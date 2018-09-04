package com.mooc.house.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.mooc.house.biz.mapper.UserMapper;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.model.House;
import com.mooc.house.common.model.User;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/6/25.
 */
@Service
@RabbitListener(queues = {"email"})
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private final Cache<String, String> registerCache = CacheBuilder.newBuilder().
            maximumSize(100).//空间
            expireAfterAccess(15, TimeUnit.MINUTES).//缓存时间15分钟
            removalListener(new RemovalListener<String, String>() {//超时就删除
        //无论是手动清除缓存，还是自动清除缓存都会执行该方法。所以要判断user的enable属性是否已经被修改，在决定是否删除user
        @Override
        public void onRemoval(RemovalNotification<String, String> removalNotification) {
            String email = removalNotification.getValue();
            int enable = userMapper.findEnableByEmail(email);
            if (enable == 0) {
                userMapper.delete(email);
            }
        }
    }).build();

    private final Cache<String, String> retrieveCache = CacheBuilder.newBuilder().
            maximumSize(100).//空间
            expireAfterAccess(15, TimeUnit.MINUTES).//缓存时间15分钟
            removalListener(new RemovalListener<String, String>() {//超时就删除
        //无论是手动清除缓存，还是自动清除缓存都会执行该方法。所以要判断user的enable属性是否已经被修改，在决定是否删除user
        @Override
        public void onRemoval(RemovalNotification<String, String> removalNotification) {

        }
    }).build();

    public void sendMail(String title, String url, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setText(url);
        message.setSubject(title);
        mailSender.send(message);
    }

    /**
     * 1.缓存key-email的关系
     * 2.借助springmail发送邮件
     * 3.借助MQ进行异步操作
     *
     *
     */
    @RabbitHandler
    public void process(User account) {
        String email = account.getEmail();
        String randomKey = RandomStringUtils.randomAlphabetic(CommonConstants.EMAIL_CODE_LENGTH);
        registerCache.put(randomKey, email);//把email存入guavacache中
        String url = "http://" + domainName + "/account/verify?key=" + randomKey;
        sendMail("平台激活邮件", url, email);
    }

    public boolean enable(String key) {
        //通过key获取email
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        int resultCount = userMapper.setEnable(1, email);
        registerCache.invalidate(key);//清掉guava缓存
        return resultCount > 0;
    }

    @Async
    public void passwordRetrieve(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(CommonConstants.EMAIL_CODE_LENGTH);
        retrieveCache.put(randomKey, email);//把email存入guavacache中
        String url = "http://" + domainName + "/account/retrieve?key=" + randomKey + "&email=" + email;
        sendMail("访问url确认重新设置密码", url, email);
    }

    public HashMap<String, Object> revise(String key, String email) {
        val returnMap = new HashMap<String, Object>();
        String guavaEmail = retrieveCache.getIfPresent(key);
        if (StringUtils.isBlank(guavaEmail)) {
            returnMap.put("status", false);
            returnMap.put("message", "无效的连接，请重新操作");
            return returnMap;
        }
        if (email.equals(guavaEmail)) {
            retrieveCache.invalidate(key);//清掉guava缓存
            returnMap.put("status", true);
            return returnMap;
        } else {
            returnMap.put("status", false);
            returnMap.put("message", "无效的连接");
            return returnMap;
        }

    }

    @Async
    public void sendMailToAgent(House house, User agent, String msg, User loginUser) {
        String sendMsg = "用户" + loginUser.getName() +
                "邮箱为" + loginUser.getEmail() +
                "在了解了您发布的房产" + house.getName() +
                "后通过本平台向您发送邮件：" + msg;
        sendMail("房产平台有用户向您发送邮件", sendMsg, agent.getEmail());
    }
}

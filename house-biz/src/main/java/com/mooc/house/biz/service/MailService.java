package com.mooc.house.biz.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.mooc.house.biz.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/6/25.
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${domain.name}")
    private String domainName;

    @Autowired
    private UserMapper userMapper;

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
     * 3.借助异步框架进行操作
     *
     * @param email
     */
    @Async
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
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
}
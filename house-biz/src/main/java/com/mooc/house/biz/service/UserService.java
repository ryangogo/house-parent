package com.mooc.house.biz.service;

import com.mooc.house.biz.mapper.UserMapper;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.model.Agency;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ServerResponse;
import com.mooc.house.common.utils.HashUtils;
import com.mooc.house.common.utils.QiNiuCDNOperator;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailService mailService;

    public List<User> selectAllUser() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    /**
     * 1校验数据是否重复 2生成key，绑定email 3发送邮件给用户
     *
     * @param account
     * @param avatarFile
     * @return
     */
    @Transactional
    public ServerResponse addCount(User account, MultipartFile avatarFile) {
        int resultCount = userMapper.CheckUserName(account.getName());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("用户名称已存在");
        }
        resultCount = userMapper.CheckEmail(account.getEmail());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("用户邮箱已存在");
        }
        if (account.getPasswd().length() < 6) {
            return ServerResponse.createByErrorMessage("密码至少需要6位");
        }
        if (!account.getPasswd().equals(account.getConfirmPasswd())) {
            return ServerResponse.createByErrorMessage("两次密码输入不一致");
        }
        if (account.getType() == 1) {
            account.setAgencyId(0);
        }
        //对密码进行加盐MD5操作
        account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
        //将头像图片上传到七牛云
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        FileInputStream inputStream = null;
        try {
            inputStream = (FileInputStream) avatarFile.getInputStream();
            DefaultPutRet db = QiNiuCDNOperator.fileUpload(inputStream, uuidStr);
            account.setAvatar(db.key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userMapper.insert(account);
        //发送邮件
        mailService.registerNotify(account.getEmail());
        return ServerResponse.createBySuccess();
    }


    public boolean enable(String key) {
        return mailService.enable(key);
    }

    public User auth(String email, String password) {
        password = HashUtils.encryPassword(password);
        User user = userMapper.selectByEmailAndPassword(email, password);
        if (user != null) {
            user.setAvatar(CommonConstants.DEFAULT_QINIU_URL + user.getAvatar());
            user.setPasswd("");
            return user;
        }
        return null;
    }

    public void retrieve(String email) {
        mailService.passwordRetrieve(email);
    }

    public HashMap<String, Object> revise(String email, String key, String password) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        if (password.length() < 6) {
            returnMap.put("status", false);
            returnMap.put("message", "密码至少需要6位");
            return returnMap;
        }
        returnMap = mailService.revise(key, email);//清除guava的缓存
        if ((boolean) returnMap.get("status")) {
            password = HashUtils.encryPassword(password);
            userMapper.modifyPasswordByEmail(email, password);
            return returnMap;
        }
        return returnMap;
    }

    public boolean checkEmail(String email) {
        int resultCount = userMapper.CheckEmail(email);
        return resultCount == 1;
    }

    public HashMap<String, Object> changePassword(User user) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        if (!user.getNewPassword().equals(user.getConfirmPasswd())) {
            returnMap.put("status", false);
            returnMap.put("message", "两次输入新密码不一致，请重新输入");
            return returnMap;
        }
        if (user.getNewPassword().length() < 6) {
            returnMap.put("status", false);
            returnMap.put("message", "密码长度需要大于6位");
            return returnMap;
        }
        String passwd = userMapper.checkByUserNameAndEmail(user.getName(), user.getEmail());
        user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
        if (!user.getPasswd().equals(passwd)) {
            returnMap.put("status", false);
            returnMap.put("message", "密码错误，请重新输入");
            return returnMap;
        }
        String encPwd = HashUtils.encryPassword(user.getNewPassword());
        userMapper.modifyPasswordByEmail(user.getEmail(), encPwd);
        returnMap.put("status", true);
        returnMap.put("message", "密码修改成功");
        return returnMap;
    }

    public HashMap<String, Object> modifyUser(HttpSession session, User user, String name, String phone, String email, String aboutme) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        int resultCount = userMapper.modifyUserByEmail(name, phone, email, aboutme);
        if (resultCount == 1) {
            returnMap.put("status", true);
            returnMap.put("message", "信息更新成功");
            user.setName(name);
            user.setPhone(phone);
            user.setAboutme(aboutme);
            session.removeAttribute(CommonConstants.USER_ATTRIBUTE);
            session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
            return returnMap;
        }
        returnMap.put("status", false);
        returnMap.put("message", "信息更新失败");
        return returnMap;
    }

    public List<Agency> getAgencies() {
        return userMapper.selectAllAgency();
    }
}

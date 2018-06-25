package com.mooc.house.biz.service;

import com.mooc.house.biz.mapper.UserMapper;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ServerResponse;
import com.mooc.house.common.utils.HashUtils;
import com.mooc.house.common.utils.QiNiuCDNOperator;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> selectAllUser() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    public ServerResponse userRegister(User account, MultipartFile avatarFile) {
        int resultCount = userMapper.CheckUserName(account.getAgencyName());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("用户已存在");
        }
        resultCount = userMapper.CheckEmail(account.getEmail());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("用户邮箱已存在");
        }
        if (account.getPasswd().length() < 6) {
            return ServerResponse.createByErrorMessage("密码需要大于6位");
        }
        if (!account.getPasswd().equals(account.getConfirmPasswd())) {
            return ServerResponse.createByErrorMessage("两次密码输入不一致");
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
        resultCount = userMapper.insert(account);
        System.out.println("------------" + resultCount + "------------");
        return ServerResponse.createBySuccess();
    }

}

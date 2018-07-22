package com.mooc.house.biz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mooc.house.biz.mapper.AgencyMapper;
import com.mooc.house.biz.mapper.UserMapper;
import com.mooc.house.common.constants.CommonConstants;
import com.mooc.house.common.model.User;
import com.mooc.house.common.result.ServerResponse;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ryan on 2018/7/10.
 */
@Service
public class AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    @Autowired
    private UserMapper userMapper;

    public ServerResponse<PageInfo> getAllAgent(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        val agentList = agencyMapper.listPageAgent();
        for (User agent : agentList) {
            String avatar = agent.getAvatar();
            agent.setAvatar(CommonConstants.DEFAULT_QINIU_URL + avatar);
        }
        PageInfo pageInfo = new PageInfo<>(agentList, pageSize);
        return ServerResponse.createBySuccess(pageInfo);
    }

    public User getAgencyById(Integer agentId) {
        User user = userMapper.selectById(agentId);
        user.setAvatar(CommonConstants.DEFAULT_QINIU_URL + user.getAvatar());
        user.setPasswd("");
        return user;
    }
}

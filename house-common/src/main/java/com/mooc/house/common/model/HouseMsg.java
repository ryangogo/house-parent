package com.mooc.house.common.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by Administrator on 2018/7/8.
 */
@Data
public class HouseMsg {

    private Integer id;

    /**
     * 留言内容
     */
    private String msg;

    /**
     * 经纪人id
     */
    private Integer agentId;

    /**
     * 房产id
     */
    private Integer houseId;

    /**
     * 留言人的id
     */
    private Long userId;

    /**
     * 留言人的姓名
     */
    private String userName;

    /**
     * 留言人头像
     */
    private String userAvatar;

    private Date createTime;

    private String createTimeStr;
}

package com.mooc.house.common.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Long id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 房屋id
     */
    private Long houseId;

    /**
     * 发布时间戳
     */
    private Date createTime;

    /**
     * 博客id
     */
    private Integer blogId;

    /**
     * 类型1-房产评论，2-博客评论
     */
    private Boolean type;

    /**
     * 用户评论
     */
    private Long userId;

}
package com.mooc.house.common.model;

import java.util.Date;

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

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取评论内容
     *
     * @return content - 评论内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评论内容
     *
     * @param content 评论内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取房屋id
     *
     * @return house_id - 房屋id
     */
    public Long getHouseId() {
        return houseId;
    }

    /**
     * 设置房屋id
     *
     * @param houseId 房屋id
     */
    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    /**
     * 获取发布时间戳
     *
     * @return create_time - 发布时间戳
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发布时间戳
     *
     * @param createTime 发布时间戳
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取博客id
     *
     * @return blog_id - 博客id
     */
    public Integer getBlogId() {
        return blogId;
    }

    /**
     * 设置博客id
     *
     * @param blogId 博客id
     */
    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    /**
     * 获取类型1-房产评论，2-博客评论
     *
     * @return type - 类型1-房产评论，2-博客评论
     */
    public Boolean getType() {
        return type;
    }

    /**
     * 设置类型1-房产评论，2-博客评论
     *
     * @param type 类型1-房产评论，2-博客评论
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * 获取用户评论
     *
     * @return user_id - 用户评论
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户评论
     *
     * @param userId 用户评论
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
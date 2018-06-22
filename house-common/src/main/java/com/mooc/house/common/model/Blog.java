package com.mooc.house.common.model;

import java.util.Date;

public class Blog {
    private Integer id;

    /**
     * 标签
     */
    private String tags;

    /**
     * 日期
     */
    private Date createTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类1-准备买房，2-看房/选房,3-签约/订房，4-全款/贷款
     * 5-缴税/过户，6-入住/交接，7-买房风险
     */
    private Integer cat;

    /**
     * 内容
     */
    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取标签
     *
     * @return tags - 标签
     */
    public String getTags() {
        return tags;
    }

    /**
     * 设置标签
     *
     * @param tags 标签
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * 获取日期
     *
     * @return create_time - 日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置日期
     *
     * @param createTime 日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取分类1-准备买房，2-看房/选房,3-签约/订房，4-全款/贷款
     * 5-缴税/过户，6-入住/交接，7-买房风险
     *
     * @return cat - 分类1-准备买房，2-看房/选房,3-签约/订房，4-全款/贷款
     * 5-缴税/过户，6-入住/交接，7-买房风险
     */
    public Integer getCat() {
        return cat;
    }

    /**
     * 设置分类1-准备买房，2-看房/选房,3-签约/订房，4-全款/贷款
     * 5-缴税/过户，6-入住/交接，7-买房风险
     *
     * @param cat 分类1-准备买房，2-看房/选房,3-签约/订房，4-全款/贷款
     *            5-缴税/过户，6-入住/交接，7-买房风险
     */
    public void setCat(Integer cat) {
        this.cat = cat;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}
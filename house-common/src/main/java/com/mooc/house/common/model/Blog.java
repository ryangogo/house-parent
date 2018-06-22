package com.mooc.house.common.model;

import lombok.Data;

import java.util.Date;

@Data
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


}
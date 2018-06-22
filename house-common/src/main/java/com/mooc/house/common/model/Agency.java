package com.mooc.house.common.model;

public class Agency {
    private Integer id;

    /**
     * 经纪机构名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 描述
     */
    private String aboutUs;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 网站
     */
    private String webSite;

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
     * 获取经纪机构名称
     *
     * @return name - 经纪机构名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置经纪机构名称
     *
     * @param name 经纪机构名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取手机
     *
     * @return phone - 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机
     *
     * @param phone 手机
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取描述
     *
     * @return about_us - 描述
     */
    public String getAboutUs() {
        return aboutUs;
    }

    /**
     * 设置描述
     *
     * @param aboutUs 描述
     */
    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    /**
     * 获取电话
     *
     * @return mobile - 电话
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置电话
     *
     * @param mobile 电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取网站
     *
     * @return web_site - 网站
     */
    public String getWebSite() {
        return webSite;
    }

    /**
     * 设置网站
     *
     * @param webSite 网站
     */
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}
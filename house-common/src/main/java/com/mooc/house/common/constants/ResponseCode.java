package com.mooc.house.common.constants;

/**
 * Created by Administrator on 2017/10/19.
 */
public enum ResponseCode {

    //判断是否为管理员
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),

    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;


    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

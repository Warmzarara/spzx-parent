package com.atguigu.spzx.model.vo.common;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200 , "操作成功") ,
    LOGIN_ERROR(201 , "用户名或者密码错误"),
    LOGIN_AUTH(208,"用户未登录"),
    USER_NOT_EXISTED_ERROR(201 , "用户不存在"),
    VALIDATECODE_EMPTY(202,"验证码为空"),
    DATA_ERROR(204,"数据异常"),
    NODE_ERROR(217,"该节点下有子节点，不可以删除"),
    USER_NAME_EXISTS(209,"该用户名已存在"),
    SYSTEM_ERROR(500,"服务器错误，网络异常"),
    FILE_UPLOAD_ERROR(501,"系统异常，文件上传失败"),
    VALIDATECODE_ERROR(202,"验证码错误");

    private Integer code ;      // 业务状态码
    private String message ;    // 响应消息

    private ResultCodeEnum(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }
}
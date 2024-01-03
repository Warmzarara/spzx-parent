package com.atguigu.spzx.model.vo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedisPrefixEnum {
    USER_LOGIN("user:login"),
    USER_LOGIN_VALIDATE("user:login:validatecode:");
    private String prefix;
}

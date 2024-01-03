package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class LoginException extends RuntimeException{
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String message;
    /**
     * 引入枚举
     */
    private ResultCodeEnum resultCodeEnum;

    public LoginException(){
        this.resultCodeEnum = ResultCodeEnum.LOGIN_ERROR;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
    
}

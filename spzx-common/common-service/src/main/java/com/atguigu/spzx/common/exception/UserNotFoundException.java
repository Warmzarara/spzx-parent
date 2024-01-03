package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException{
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

    public UserNotFoundException(){
        this.resultCodeEnum = ResultCodeEnum.USER_NOT_EXISTED_ERROR;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}

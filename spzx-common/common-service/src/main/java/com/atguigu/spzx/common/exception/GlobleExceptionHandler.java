package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobleExceptionHandler {
    //全局异常处理：Exception.class

    /**
     * 自动抛出异常，按照自定义格式返回结果
     * @return
     */
    @ExceptionHandler(Exception.class)   //异常何时执行（条件）
    public Result error(Exception e){
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    } 

    /**
     * 手动抛出异常
     * @param e
     * @return
     */
    @ExceptionHandler(GuiguException.class)
    public Result error(GuiguException e){
        return Result.build(null, e.getResultCodeEnum());
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public Result error(UserNotFoundException e){
        return Result.build(null,e.getResultCodeEnum());
    }
    
    @ExceptionHandler(LoginException.class)
    public Result error(LoginException e){
        return Result.build(null,e.getResultCodeEnum());
    }
    
}

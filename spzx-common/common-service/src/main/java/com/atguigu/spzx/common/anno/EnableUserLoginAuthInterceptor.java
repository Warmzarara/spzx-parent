package com.atguigu.spzx.common.anno;

import com.atguigu.spzx.common.config.UserWebMvcConfiguration;
import com.atguigu.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)   //使用位置 类
@Retention(value = RetentionPolicy.RUNTIME) //生命周期
@Import(value = {UserLoginAuthInterceptor.class, UserWebMvcConfiguration.class})
public @interface EnableUserLoginAuthInterceptor {
}

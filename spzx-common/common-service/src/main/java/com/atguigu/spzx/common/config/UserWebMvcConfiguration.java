package com.atguigu.spzx.common.config;

import com.atguigu.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.common.config
 * @className: UserWebMvcConfiguration
 * @author: XiaoHB
 * @date: 2024/3/7 18:52
 */
@Component
public class UserWebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private UserLoginAuthInterceptor userLoginAuthInterceptor;

    /**
     * 注册拦截器，拦截/api 开头的所有路径，执行拦截器方法
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor)
                .addPathPatterns("/api/**");
    }
}

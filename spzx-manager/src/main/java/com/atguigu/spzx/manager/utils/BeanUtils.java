package com.atguigu.spzx.manager.utils;


import org.springframework.context.ApplicationContext;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.utils
 * @className: BeanUtils
 * @author: XiaoHB
 * @date: 2024/3/1 17:30
 */
public class BeanUtils {
    private static ApplicationContext appContext;
    public static void setApplicationContext(ApplicationContext appContext){
        BeanUtils.appContext = appContext;
    }
    
    public static Object getBean(String beanName){
        return appContext.getBean(beanName);
    }
}

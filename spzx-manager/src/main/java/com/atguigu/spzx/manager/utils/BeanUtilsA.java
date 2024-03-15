package com.atguigu.spzx.manager.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.utils
 * @className: BeanUtilsA
 * @author: XiaoHB
 * @date: 2024/3/1 18:18
 */
@Component
public class BeanUtilsA implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtilsA.applicationContext = applicationContext;   
    }
    
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    
    public Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
    
    public <T>T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
    
    public <T>T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}

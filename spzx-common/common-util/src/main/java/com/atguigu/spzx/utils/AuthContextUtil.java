package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.entity.user.UserInfo;

/**
 * 操作线程对象
 */
public class AuthContextUtil {
    
    /**
     * 创建ThreadLocal对象
     */
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();
    
    /**
     * 添加数据
     */
    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }
    
    /**
     * 获取数据
     */
    public static SysUser get(){
        return threadLocal.get();
    }

    /**
     * 删除对象
     */
    public static void remove(){
        threadLocal.remove();
    }

    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>() ;


    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get() ;
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }
}

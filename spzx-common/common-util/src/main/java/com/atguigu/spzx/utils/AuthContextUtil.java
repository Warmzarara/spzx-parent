package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysUser;

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
}

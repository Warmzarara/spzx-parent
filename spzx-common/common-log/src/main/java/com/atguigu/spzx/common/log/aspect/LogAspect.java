package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.utils.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.common.log.aspect
 * @className: LogAspect
 * @author: XiaoHB
 * @date: 2024/2/6 18:40
 */
@Aspect
@Component
public class LogAspect {
    
    @Autowired
    private AsyncOperLogService asyncOperLogService;
    
    //前置
    //@Before()
    //后置
    //@After()
    //环绕通知
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(
            ProceedingJoinPoint joinPoint,  //调用业务方法
            Log sysLog  //注解
    ){
//        String title = sysLog.title();
//        int bussinessType = sysLog.businessType();
//        System.out.println("tittle:"+title+" Type:"+bussinessType);
        
        
        
        /*
        业务方法调用之前封装数据
         */
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);
        //业务方法
        Object proceed = null;
        try {
             proceed = joinPoint.proceed();
             LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,0,null);
            System.out.println("业务方法执行完了");
        } catch (Throwable e) {
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            // TODO: 2024/2/11 AOP不手动抛出异常，事务不会回滚 
            throw new RuntimeException();
        }
        
        //调用service方法
        asyncOperLogService.saveSysOperLog(sysOperLog);
        //返回执行结果
        return proceed;
    }
}

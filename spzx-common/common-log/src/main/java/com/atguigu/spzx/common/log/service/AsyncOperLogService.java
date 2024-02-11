package com.atguigu.spzx.common.log.service;

import com.atguigu.spzx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {
    public void saveSysOperLog(SysOperLog sysOperLog);
}

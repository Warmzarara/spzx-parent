package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssignMenuDto;

import java.util.Map;

public interface SysRoleMenuService {
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    void doAssign(AssignMenuDto assignMenuDto);
}

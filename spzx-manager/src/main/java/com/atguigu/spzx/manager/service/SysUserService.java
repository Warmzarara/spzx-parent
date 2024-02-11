package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    SysUser logout(String token);

    PageInfo<SysUser> findByPage( SysUserDto sysUserDto,Integer pageNum, Integer pageSize);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Integer userId);

    void doAssign(AssignRoleDto assignRoleDto);

}

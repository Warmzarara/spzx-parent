package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: SysRoleServiceImpl
 * @author: XiaoHB
 * @date: 2023/12/24 14:46
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    /**
     * 从数据库中，按照角色名称查询数据，并进行分页
     * @param sysRoleDto
     * @param current 当前页数
     * @param limit 当前页显示记录数
     * @return
     */
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        PageHelper.startPage(current,limit);
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleList);
        return pageInfo;
    }

    /**
     * 添加角色方法
     * @param sysRole
     */
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    /**
     * 修改角色方法 
     * @param sysRole
     */
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    /**
     * 删除角色方法
     * @param roleId
     */
    @Override
    public void deleteById(long roleId) {
        sysRoleMapper.delete(roleId);
    }

    /**
     * 
     * @return
     */
    @Override
    public Map<String, Object> findAll(Long userId) {
        //map用来储存角色信息
        Map<String,Object> map = new HashMap<>();
        //1.查询所有角色
        List<SysRole> roleList = sysRoleMapper.findAll();
        map.put("allRolesList",roleList);
        //2.根据userId查询分配过的角色id列表
        List<Long> roleIds = sysRoleUserMapper.selectRoleIdsByUserId(userId);
        map.put("sysUserRoles",roleIds);
        return map;
    }
}

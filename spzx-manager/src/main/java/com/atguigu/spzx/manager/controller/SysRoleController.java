package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: SysRoleController
 * @author: XiaoHB
 * @date: 2023/12/24 14:44
 */
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {
    @Autowired private SysRoleService sysRoleService;

    /**
     * 角色列表方法，pageHelper实现分页
     * @param current 当前页
     * @param limit 每页显示记录数
     * @param sysRoleDto 条件角色名称对象
     * @return
     */
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable Integer current,
                             @PathVariable Integer limit,
                             @RequestBody SysRoleDto sysRoleDto) {
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto,current,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 角色查询
     * 1.查询所有的角色
     * 2.查询当前用户分配过的角色
     * 用map结构来存储角色，为了同时存储所有角色信息和回显信息
     * @return
     */
    @GetMapping("findAllRoles/{userId}")
    public Result findAllRoles (@PathVariable("userId") Long userId){
        Map<String,Object> map = sysRoleService.findAll(userId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }
    

    /**
     * 角色添加方法
     * @param sysRole
     * @return
     */
    @Log(
            title = "角色管理添加",
            businessType = 1)
    @PostMapping("/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 角色修改方法
     * @param sysRole
     * @return
     */
    @PostMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 角色删除方法
     * @param roleId
     * @return
     */
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId") long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    
    
}

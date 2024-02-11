package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: SysUserController
 * @author: XiaoHB
 * @date: 2023/12/30 16:31
 */

@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户列表方法
     * @param current
     * @param limit
     * @param sysUserDto
     * @return
     */
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable Integer current,
                             @PathVariable Integer limit,
                             @RequestBody SysUserDto sysUserDto){
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto,current,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用户添加方法
     * @param sysUser
     * @return
     */
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用户修改方法
     * @param sysUser
     * @return
     */
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用户删除方法
     * @param userId
     * @return
     */
    @DeleteMapping("/deleteById/{userId}")  
    public Result deleteById(@PathVariable("userId") Integer userId){
        sysUserService.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 角色分配方法
     * @param assignRoleDto
     * @return
     */
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignRoleDto assignRoleDto){
        sysUserService.doAssign(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}

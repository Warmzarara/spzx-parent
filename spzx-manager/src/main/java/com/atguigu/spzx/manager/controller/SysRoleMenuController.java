package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: SysRoleMenuController
 * @author: XiaoHB
 * @date: 2024/1/11 18:48
 */
@RestController
@RequestMapping("/admin/system/sysRoleMenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 查询所有菜单和分配过菜单的id列表
     * @param roleId
     * @return
     */
    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId){
        Map<String,Object> map = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }
    
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignMenuDto assignMenuDto){
        //删除角色之前分配过的菜单数据
        sysRoleMenuService.doAssign(assignMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}

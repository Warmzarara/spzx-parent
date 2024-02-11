package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.controller
 * @className: SysMenuController
 * @author: XiaoHB
 * @date: 2024/1/9 17:35
 */ 
@Tag(name = "菜单接口")
@RestController //交给spring管理，返回json数据
@RequestMapping("/admin/system/sysMenu")  //路径
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    // TODO: 2024/1/9 菜单修改 菜单添加 菜单列表
    
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
    @PutMapping("/update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable("id") Long id){
        sysMenuService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    
    @GetMapping("/findNodes")
    public Result findNodes () {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}

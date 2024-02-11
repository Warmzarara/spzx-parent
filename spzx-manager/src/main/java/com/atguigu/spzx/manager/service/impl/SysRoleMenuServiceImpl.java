package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: SysRoleMenuServiceImpl
 * @author: XiaoHB
 * @date: 2024/1/11 18:50
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    
    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public Map<String,Object> findSysRoleMenuByRoleId(Long roleId) {
        Map<String,Object> map = new HashMap<>();
        List<SysMenu> sysMenulist = sysMenuService.findNodes();
        map.put("sysMenuList",sysMenulist);
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);
        map.put("roleMenuIds",roleMenuIds); 
        return map;
    }

    @Override
    public void doAssign(AssignMenuDto assignMenuDto) {
        sysRoleMenuMapper.deleteByRoleId(assignMenuDto.getRoleId());
        List<Map<String,Object>> menuInfo = assignMenuDto.getMenuIdList();
        if(menuInfo!=null && menuInfo.size()>0){
            sysRoleMenuMapper.doAssign(assignMenuDto);
        }else{
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}

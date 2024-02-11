package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    void deleteByRoleId(Long roleId);

    List<Long> findSysRoleMenuByRoleId(Long roleId);

    void doAssign(AssignMenuDto assignMenuDto);

    void updateSysRoleMenuIsHalf(Long menuId);
}
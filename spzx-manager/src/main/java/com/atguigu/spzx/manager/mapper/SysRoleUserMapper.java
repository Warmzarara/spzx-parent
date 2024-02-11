package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {
    void deleteByUserId(Long userId);

    void doAssign(Long userId, Long roleId);

    List<Long> selectRoleIdsByUserId(Long userId);
}

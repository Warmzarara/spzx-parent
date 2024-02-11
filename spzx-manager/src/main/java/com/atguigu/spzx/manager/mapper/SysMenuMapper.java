package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAll();
    SysMenu findById(Long id);
    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void deleteById(Long id);


    void delete(Long id);

    Integer selectCountById(Long id);

    List<SysMenu> findMenusByUserId(Long userId);

    SysMenu selectParentMenu(Long parentId);
}

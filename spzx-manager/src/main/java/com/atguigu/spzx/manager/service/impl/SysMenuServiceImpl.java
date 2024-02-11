package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.utils.MenuHelper;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.service.impl
 * @className: SysMenuServiceImpl
 * @author: XiaoHB
 * @date: 2024/1/9 17:37
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 1.查询所有菜单，若为空则返回null
     * 2.调用菜单工具类，返回List<Sysmenu>类型的集合
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        //查询所有菜单
        List<SysMenu> sysMenuList = sysMenuMapper.findAll();
        if(CollectionUtil.isEmpty(sysMenuList)){
            return null;
        }
        //调用工具类方法，返回要求的数据格式
        List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList);
        return treeList;
    }

    /**
     * 菜单添加
     * @param sysMenu
     */
    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);
        //新添加菜单，父菜单变为半开
        updateSysRoleMenu(sysMenu);
    }
    
    private void updateSysRoleMenu(SysMenu sysMenu){
        //获取当前菜单父菜单
        SysMenu parentMenu = sysMenuMapper.selectParentMenu(sysMenu.getParentId());
        if(parentMenu != null){
            //isHalf改为1
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            updateSysRoleMenu(parentMenu);
        }
    }

    /**
     * 菜单更新
     * @param sysMenu
     */
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }
    
    //判断是否存在子菜单
    //存在子菜单则删除
    @Override
    public void deleteById(Long id) {
        Integer count = sysMenuMapper.selectCountById(id);
        if(count != 0){
            if(count > 0) {
                throw new GuiguException(ResultCodeEnum.NODE_ERROR);
            }else if (count < 0){
                throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
            }
        }
        sysMenuMapper.delete(id);
    }

    @Override
    public List<SysMenuVo> findMenusByUserId() {
        //获取当前用户id
        SysUser sysUser = AuthContextUtil.get();
        //根据userId查询菜单
        Long userId = sysUser.getId();
        List<SysMenu> sysMenuList = MenuHelper.buildTree(sysMenuMapper.findMenusByUserId(userId));
        //封装成要求结构
        List<SysMenuVo> sysMenuVos = this.buildMenus(sysMenuList);
        return sysMenuVos;
    }

    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}

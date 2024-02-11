package com.atguigu.spzx.manager.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.manager.utils
 * @className: MenuHelper
 * @descritpion: 封装树形菜单数据
 * @author: XiaoHB
 * @date: 2024/1/9 17:58
 */
public class MenuHelper {
    /**
     * 递归入口
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        //最终集合数据结构
        List<SysMenu> trees = new ArrayList<>();
        //开始递归
        for(SysMenu sysMenu:sysMenuList ){
            //找到第一层菜单 条件：parentId = 0
            if(sysMenu.getParentId().longValue() == 0) {    //递归入口：顶层元素（parentId=0）
                //根据第一层找下一层
                //传递两个参数：1.当前第一层菜单 2.所有菜单集合
                trees.add(findChild(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子菜单
     * @param sysMenu 每次寻子方法的相对顶层菜单
     * @param sysMenuList 所有菜单的集合
     * @return
     */
    private static SysMenu findChild(SysMenu sysMenu,List<SysMenu> sysMenuList){
         sysMenu.setChildren(new ArrayList<>());
         for(SysMenu it:sysMenuList){   //sysMenu为当前循环的顶层菜单，it为要查找的子菜单
             if(sysMenu.getId().longValue() == it.getParentId().longValue()){   //子菜单parentId = 当前顶层菜单id
                 sysMenu.getChildren().add(findChild(it,sysMenuList));  //递归，以当前子菜单为顶层菜单，接着找下一层菜单，直到没有子菜单的最后一层，从下往上调用add方法
             }
         }
        return sysMenu; //返回最顶层菜单与其子菜单的树形结构集合
    }
    
    
//    public List<SysMenu> bt(List<SysMenu> allMenuList){
//        List<SysMenu> treeMenu = new ArrayList<>(); //处理后的树形结构
//        for(SysMenu pNode:allMenuList){
//            if(pNode.getParentId() == 0){   //方法入口：发现顶层菜单，开始递归
//                treeMenu.add(fc(pNode,allMenuList));
//            }
//        }
//        return treeMenu;
//    }
//
//    private SysMenu fc(SysMenu pNode, List<SysMenu> allMenuList) {
//        pNode.setChildren(new ArrayList<>());
//        for(SysMenu sNode:allMenuList){
//            if(sNode.getParentId() == pNode.getId()){
//                pNode.getChildren().add(fc(sNode,allMenuList));
//            }
//        }
//        return pNode;
//    }


}

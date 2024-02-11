package com.atguigu.spzx.model.vo.system;

import lombok.Data;

import java.util.List;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.vo.system
 * @className: SysMenuVo
 * @author: XiaoHB
 * @date: 2024/1/20 18:03
 */
@Data
public class SysMenuVo {
    private String title;
    private String name;
    private List<SysMenuVo> children;
}

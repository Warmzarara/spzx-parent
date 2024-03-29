package com.atguigu.spzx.model.entity.h5;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.entity.h5
 * @className: CartInfo
 * @author: XiaoHB
 * @date: 2024/3/9 19:37
 */
// com.atguigu.spzx.model.entity.h5;
@Data
@Schema(description = "购物车实体类")
public class CartInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "skuid")
    private Long skuId;

    @Schema(description = "放入购物车时价格")
    private BigDecimal cartPrice;

    @Schema(description = "数量")
    private Integer skuNum;

    @Schema(description = "图片文件")
    private String imgUrl;

    @Schema(description = "sku名称 (冗余)")
    private String skuName;

    @Schema(description = "isChecked")
    private Integer isChecked;

}
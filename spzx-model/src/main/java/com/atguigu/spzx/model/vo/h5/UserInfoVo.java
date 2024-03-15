package com.atguigu.spzx.model.vo.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @projectName: spzx-parent
 * @package: com.atguigu.spzx.model.vo.h5
 * @className: UserInfoVo
 * @author: XiaoHB
 * @date: 2024/3/7 17:01
 */
@Data
@Schema(description = "用户类")
public class UserInfoVo {

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

}
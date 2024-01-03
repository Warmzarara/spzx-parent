package com.atguigu.spzx.model.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "验证码响应实体类")
public class ValidateCodeVo {
    @Schema(description = "验证码Key")
    private String codeKey;     //验证码的key
    
    @Schema(description = "验证码Value")
    private String codeValue;       //图片验证码相应的字符串数据
}

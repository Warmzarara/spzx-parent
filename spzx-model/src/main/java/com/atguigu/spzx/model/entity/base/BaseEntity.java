package com.atguigu.spzx.model.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共属性实体类
 */
@Data
public class BaseEntity implements Serializable {
    
    @Schema(description = "唯一标识")
    private Long id;
    
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
    @Schema(description = "创建时间")
    private Date createTime;
    
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
    @Schema(description = "修改时间")
    private Date updateTime;
    
    @Schema(description = "是否删除")
    private Integer isDeleted;  
}

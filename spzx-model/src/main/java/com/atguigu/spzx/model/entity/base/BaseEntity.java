package com.atguigu.spzx.model.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共属性实体类
 */
@Data
public class BaseEntity implements Serializable {
    private Long id;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date createTime;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date updateTime;
    private Integer isDeleted;  
}

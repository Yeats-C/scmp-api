package com.aiqin.bms.scmp.api.product.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Createed by sunx on 2018/11/19.<br/>
 */
@Data
@ApiModel("分页返回实体")
public class BasePageResponse<T> {
    @ApiModelProperty("总记录")
    private Integer total;

    @ApiModelProperty("明细")
    private List<T> contents;
}

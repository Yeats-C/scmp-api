package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Createed by sunx on 2018/11/19.<br/>
 */
@Data
@ApiModel("分页查询基础实体")
public class BasePageRequest<T> {

    @ApiModelProperty("当前页码，第一页为1")
    @JsonProperty("current_page")
    private Integer currentPage;

    @ApiModelProperty("页面大小")
    @JsonProperty("page_size")
    private Integer pageSize;

    @ApiModelProperty("查询条件")
    @JsonProperty("search_params")
    private T searchParams;
}

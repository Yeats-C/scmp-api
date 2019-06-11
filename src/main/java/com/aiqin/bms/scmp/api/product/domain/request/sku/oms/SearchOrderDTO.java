package com.aiqin.bms.scmp.api.product.domain.request.sku.oms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * SearchOrderDTO
 *
 * @author zhangtao
 * @createTime 2019/1/4 下午7:17
 * @description 排序规则
 */
@Data
@ApiModel("排序规则")
public class SearchOrderDTO implements Serializable {
    private static final long serialVersionUID = 6746089946280238728L;

    @ApiModelProperty("排序规则，true-升序,false-降序")
    private Boolean ascending;

    @ApiModelProperty("排序字段，1-综合排序，2-按销量排序，3-按价格排序")
    private Integer property;
}

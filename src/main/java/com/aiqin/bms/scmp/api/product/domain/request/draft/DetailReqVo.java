package com.aiqin.bms.scmp.api.product.domain.request.draft;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className DetailReqVo
 * @date 2019/5/14 14:42
 * @description TODO
 */
@ApiModel("获取申请单详情请求Vo")
@Data
public class DetailReqVo {

    @ApiModelProperty("审批类型,1:商品,2:商品配置,3:销售区域")
    private Integer approvalType;

    @ApiModelProperty(value = "申请类别 此字段用与查看商品和SKU详情使用")
    private String applySort;

    @ApiModelProperty(value = "申请编号")
    private String code;

    @ApiModelProperty(value = "主键Id")
    private Long id;


}

package com.aiqin.bms.scmp.api.product.domain.response.sku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/13 0013 14:25
 */
@Data
@ApiModel("商品申请管理返回数据")
public class QuerySkuApplyListResp {
    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("sku数量")
    private Long skuNum;

    @ApiModelProperty("商品数")
    private Long productNum;

    @ApiModelProperty("审核状态")
    private String approvalStatus;

    @ApiModelProperty("审核人")
    private String reviewer;

    @ApiModelProperty("审核时间")
    private Date reviewTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}

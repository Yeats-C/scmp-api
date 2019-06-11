package com.aiqin.bms.scmp.api.product.domain.response.skuconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/19 0019 15:45
 */
@Data
@ApiModel("sku配置申请管理返回对象")
public class ApplySkuConfigItemResp {
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

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;
}

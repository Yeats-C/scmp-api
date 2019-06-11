package com.aiqin.bms.scmp.api.product.domain.request.sku.purchase;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("采购sku列表请求条件")
public class QueryPurchaseSkuReqVO extends PageReq {
    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @NotEmpty(message = "供货单位不能为空")
    @ApiModelProperty("供货单位code")
    private String supplyCode;

    @NotEmpty(message = "物流中心不能为空")
    @ApiModelProperty("物流中心code")
    private String transportCenterCode;

    @NotEmpty(message = "库房不能为空")
    @ApiModelProperty("库房code")
    private String warehouseCode;

    @NotEmpty(message = "采购组不能为空")
    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

}

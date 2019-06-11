package com.aiqin.bms.scmp.api.product.domain.request.sku.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("采购sku列表校验请求")
public class CheckPurchaseSkuReqVO {
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

    @ApiModelProperty("sku编码集合")
    private List<String> skuList;

    @ApiModelProperty("公司编码")
    private String companyCode;

}

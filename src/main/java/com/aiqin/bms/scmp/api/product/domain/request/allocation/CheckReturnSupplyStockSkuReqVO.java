package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author HuangLong
 * @date 2019/1/4
 */
@Data
@ApiModel("退供导入sku商品列表校验请求vo")
public class CheckReturnSupplyStockSkuReqVO {

    @ApiModelProperty("公司编码")
    private String companyCode;

    @NotEmpty(message = "供货单位不能为空")
    @ApiModelProperty("供货单位code")
    private String supplyCode;

    @NotEmpty(message = "物流中心不能为空")
    @ApiModelProperty("物流中心code")
    private String transportCenterCode;

    @NotEmpty(message = "库房不能为空")
    @ApiModelProperty("库房code")
    private String warehouseCode;


    @ApiModelProperty("采购组")
    @NotEmpty(message = "采购组不能为空")
    private String purchaseGroupCode;

    @ApiModelProperty("库存属性  1是正品，0是备品")
    private Byte stockProperty;

    @ApiModelProperty("sku编码集合")
    private List<String> skuList;
}

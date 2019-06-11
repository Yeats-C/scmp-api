package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author knight.xie
 * @version 1.0
 * @className UpdateStockReqVo
 * @date 2019/1/9 14:45
 * @description 库存更新VO
 */
@ApiModel("库存更新VO")
@Data
public class UpdateStockReqVo implements Serializable {

    @ApiModelProperty("公司编码")
    @NotEmpty(message = "公司编码不能为空")
    private String companyCode;

    @ApiModelProperty("供货单位")
    @NotEmpty(message = "供货单位不能为空")
    private String supplyCode;

    @ApiModelProperty("物流中心")
    @NotEmpty(message = "物流中心不能为空")
    private String transportCenterCode;

    @ApiModelProperty("库房")
    @NotEmpty(message = "库房不能为空")
    private String warehouseCode;

    @ApiModelProperty("采购组")
    @NotEmpty(message = "采购组不能为空")
    private String purchaseGroupCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("库存状态")
    private byte stockStatusCode;

    public UpdateStockReqVo(String companyCode,String transportCenterCode,String warehouseCode, String skuCode, Long num, byte stockStatusCode) {
        this.companyCode = companyCode;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.skuCode = skuCode;
        this.num = num;
        this.stockStatusCode = stockStatusCode;
    }
}


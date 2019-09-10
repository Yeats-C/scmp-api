package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuSupplyUnitCapacityRespVo
 * @date 2019/5/14 15:48
 */
@Data
@ApiModel("SKU供应商产能信息返回")
public class ProductSkuSupplyUnitCapacityRespVo extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商编码")
    private String supplyUnitCode;

    @ApiModelProperty("供应商名称")
    private String supplyUnitName;

    @ApiModelProperty("sku编码")
    private String productSkuCode;

    @ApiModelProperty("sku名称")
    private String productSkuName;

    @ApiModelProperty("生产量")
    private Long outPut;

    @ApiModelProperty("需要天数")
    private Long needDays;
}

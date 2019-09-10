package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("SKU库存临时信息")
@Data
public class ProductSkuStockInfoDraft extends CommonBean {


    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("sku编码")
    private String productSkuCode;

    @ApiModelProperty("sku名称")
    private String productSkuName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("条形码")
    private String barCode;

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

    @ApiModelProperty("拆零系数")
    private Long zeroRemovalCoefficient;

}
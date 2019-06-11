package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("包装信息")
@Data
public class ProductSkuBoxPackingDraft extends CommonBean {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("整箱商品包装 单位名称")
    private String largeUnit;

    @ApiModelProperty("整箱商品包装 单位编码")
    private String unitCode;

    @ApiModelProperty("包装箱子长度")
    private Long boxLength;

    @ApiModelProperty("宽度（mm）")
    private Long boxWidth;

    @ApiModelProperty("箱子高度")
    private Long boxHeight;

    @ApiModelProperty("箱子体积")
    private Long boxVolume;

    @ApiModelProperty("毛重")
    private Long boxGrossWeight;

    @ApiModelProperty("净重")
    private Long netWeight;


}
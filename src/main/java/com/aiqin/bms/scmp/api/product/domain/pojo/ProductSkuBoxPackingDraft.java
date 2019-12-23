package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.base.PropertyMsg;
import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

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
    @PropertyMsg(value = "单位", delete = true)
    private String largeUnit;

    @ApiModelProperty("整箱商品包装 单位编码")
    private String unitCode;

    @ApiModelProperty("包装箱子长度")
    // @PropertyMsg("长")
    private Long boxLength;

    @ApiModelProperty("宽度（mm）")
    // @PropertyMsg("宽")
    private Long boxWidth;

    @ApiModelProperty("箱子高度")
    // @PropertyMsg("高")
    private Long boxHeight;

    @ApiModelProperty("箱子体积")
    @PropertyMsg("体积")
    private Long boxVolume;

    @ApiModelProperty("毛重")
    @PropertyMsg("毛重")
    private BigDecimal boxGrossWeight;

    @ApiModelProperty("净重")
    @PropertyMsg("净重")
    private BigDecimal netWeight;


}
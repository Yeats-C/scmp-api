package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.base.PropertyMsg;
import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("门店销售/销售信息")
@Data
public class ProductSkuSalesInfoDraft extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品编号")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("描述")
    @PropertyMsg("描述")
    private String description;

    @ApiModelProperty("0:未用 1:在用")
    private Byte usageStatus;

    @ApiModelProperty("规格")
    @PropertyMsg("规格")
    private String spec;

    @ApiModelProperty("单位名称")
    @PropertyMsg(value = "单位", delete = true)
    private String smallUnit;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("销售码")
    @PropertyMsg("条形码")
    private String salesCode;

    @ApiModelProperty("基商品含量")
    @PropertyMsg("单位含量")
    private Integer baseProductContent;

    @ApiModelProperty("宽")
    private Long productWidth;

    @ApiModelProperty("长")
    private Long productLength;

    @ApiModelProperty("高度")
    private Long productHeight;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    @PropertyMsg(value = "默认值", replace = "0_否,1_是")
    private Byte isDefault;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("拆零系数")
    @PropertyMsg("交易倍数")
    private Long zeroRemovalCoefficient;
}
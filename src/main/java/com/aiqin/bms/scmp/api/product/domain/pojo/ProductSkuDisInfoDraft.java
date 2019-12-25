package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.base.PropertyMsg;
import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("sku销售/配送信息")
@Data
public class ProductSkuDisInfoDraft extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品编号")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("规格")
    @PropertyMsg("规格")
    private String spec;

    @ApiModelProperty("单位code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    @PropertyMsg("单位")
    private String unitName;

    @ApiModelProperty("配送码")
    @PropertyMsg("条形码")
    private String deliveryCode;

    @ApiModelProperty("基商品含量")
    @PropertyMsg("单位含量")
    private Integer baseProductContent;

    @ApiModelProperty("最大订货数量")
    @PropertyMsg("最大订购数量")
    private Integer maxOrderNum;

    @ApiModelProperty("商品sku 编码")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("拆零系数")
    @PropertyMsg("交易倍数")
    private Long zeroRemovalCoefficient;
}
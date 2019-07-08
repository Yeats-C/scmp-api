package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("组合商品-子sku信息临时表")
@Data
public class ProductSkuSubDraft extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("组合SKU编码")
    private String mainSkuCode;

    @ApiModelProperty("组合SKU名称")
    private String mainSkuName;

    @ApiModelProperty("子SKU编码")
    private String subSkuCode;

    @ApiModelProperty("子SKU名称")
    private String subSkuName;

    @ApiModelProperty("子SKU数量")
    private Integer subSkuNum;

    @ApiModelProperty("是否主商品(0:不是 1:是)")
    private Byte mainProduct;
}
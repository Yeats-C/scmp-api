package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("关联商品临时表")
@Data
public class ProductSkuAssociatedGoodsDraft extends CommonBean {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("主商品编码")
    private String mainSkuCode;

    @ApiModelProperty("主商品名称")
    private String mainSkuName;

    @ApiModelProperty("子商品编码")
    private String subSkuCode;

    @ApiModelProperty("子商品名称")
    private String subSkuName;

}
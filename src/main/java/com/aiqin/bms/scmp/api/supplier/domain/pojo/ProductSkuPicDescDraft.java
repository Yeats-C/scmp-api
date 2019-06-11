package com.aiqin.bms.scmp.api.supplier.domain.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("sku商品说明")
@Data
public class ProductSkuPicDescDraft {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("排序号")
    private Long sortingNumber;

    @ApiModelProperty("图片说明路径")
    private String picDescPath;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

}
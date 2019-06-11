package com.aiqin.bms.scmp.api.supplier.domain.pojo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("生产厂家")
@Data
public class ProductSkuManufacturerDraft {




    @ApiModelProperty("生产厂家")
    private String manufacturerName;

    @ApiModelProperty("厂方商品编号")
    private String factoryProductNumber;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Byte isDefault;

    @ApiModelProperty("保修地址")
    private String address;

}
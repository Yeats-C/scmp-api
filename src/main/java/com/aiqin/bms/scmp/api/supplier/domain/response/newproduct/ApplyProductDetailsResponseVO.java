package com.aiqin.bms.scmp.api.supplier.domain.response.newproduct;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("商品申请详细")
public class ApplyProductDetailsResponseVO {
     @ApiModelProperty("商品编码")
    private String productCode;;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("sku数量")
    private Integer skuNumber;

    @ApiModelProperty("所属品类名称")
    private String productCategoryName;
    @ApiModelProperty("所属品类编码")
    private String productCategoryCode;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;


    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("申请时间起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("申请结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date selectionEffectiveEndTime;

    private String formNo;
}

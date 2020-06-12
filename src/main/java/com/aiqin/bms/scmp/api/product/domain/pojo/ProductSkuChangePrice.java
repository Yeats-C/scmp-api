package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("变价主表")
@Data
public class ProductSkuChangePrice {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("编码")
    private String code;

//    @ApiModelProperty("变价类型编码")
//    private String changePriceType;

//    @ApiModelProperty("变价类型名称")
//    private String changePriceName;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("运费承担方编码")
    private String costBearerCode;

    @ApiModelProperty("运费承担方名称")
    private String costBearerName;

    @ApiModelProperty("预算")
    private BigDecimal budget;

    @ApiModelProperty("公司名称")
    private String companyCode;

    @ApiModelProperty("公司编码")
    private String companyName;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("与之前相同")
    private Integer applyStatus;

    @ApiModelProperty("申请的表单号")
    private String formNo;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("来源(0变价1sku新增)")
    private Integer original;

    @ApiModelProperty("调价原因编码")
    private String changePriceReasonCode;

    @ApiModelProperty("调价原因描述")
    private String changePriceReasonName;

    @ApiModelProperty("是否含有区域")
    private Integer beContainArea;

    @ApiModelProperty("毛利率增加数")
    private BigDecimal increaseCount;
    @ApiModelProperty("毛利率减少数")
    private BigDecimal decreaseCount;
    @ApiModelProperty("毛利率增加额度")
    private BigDecimal increaseGrossProfit;
    @ApiModelProperty("毛利率减少额度")
    private BigDecimal decreaseGrossProfit;
}
package com.aiqin.bms.scmp.api.product.domain.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("价格日志表")
@Data
public class ProductSkuPriceInfoLog {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("关联编码")
    private String code;

    @ApiModelProperty("含税金额")
    private Long priceTax;

    @ApiModelProperty("未税金额")
    private Long priceNoTax;

    @ApiModelProperty("税率")
    private Long tax;

    @ApiModelProperty("开始生效时间")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    private Date effectiveTimeEnd;

    @ApiModelProperty("状态0未生效1生效中2已失效")
    private Integer status;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("区域或门店信息")
    private String areaInfo;

    @ApiModelProperty("扩展字段1")
    private String extField1;

    @ApiModelProperty("扩展字段2")
    private String extField2;

    @ApiModelProperty("扩展字段3")
    private String extField3;

    @ApiModelProperty("扩展字段4")
    private Date extField4;

    @ApiModelProperty("扩展字段5")
    private Integer extField5;

    @ApiModelProperty("扩展字段6")
    private Long extField6;

    public ProductSkuPriceInfoLog(String code, Long priceTax, Long priceNoTax, Long tax, Date effectiveTimeStart, Date effectiveTimeEnd, Integer status, String createBy, Date createTime) {
        this.code = code;
        this.priceTax = priceTax;
        this.priceNoTax = priceNoTax;
        this.tax = tax;
        this.effectiveTimeStart = effectiveTimeStart;
        this.effectiveTimeEnd = effectiveTimeEnd;
        this.status = status;
        this.createBy = createBy;
        this.createTime = createTime;
    }

    public ProductSkuPriceInfoLog() {
    }
}
package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date effectiveTimeEnd;

    @ApiModelProperty("状态0未生效1生效中2已失效")
    private Integer status=1;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("区域或门店信息")
    private String areaInfo;

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
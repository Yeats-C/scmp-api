package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("区域附表")
@Data
public class ProductSkuChangePriceAreaInfo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("主表编码")
    private String mainCode;

    @ApiModelProperty("区域名称")
    private String name;

    @ApiModelProperty("区域编码")
    private String code;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("开始生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date effectiveTimeEnd;

    @ApiModelProperty("是否有效(0否1是)")
    private Integer beEffective;

    @ApiModelProperty("失效原因")
    private String unEffectiveReason;
}
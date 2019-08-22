package com.aiqin.bms.scmp.api.product.domain.request.newproduct;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("商品查询列表")
public class QueryNewProductReqVO extends PageReq {
    @ApiModelProperty("创建时间开始")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date createTimeStart;

    @ApiModelProperty("创建时间结束")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date createTimeEnd;
    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty(value = "当前登录人", hidden = true)
    private String personId;
}

package com.aiqin.bms.scmp.api.supplier.domain.response.sku;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/21 0021 10:20
 */
@Data
@ApiModel("商品申请商品列表返回")
public class ApplyDetailProductListResp {
    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("sku数量")
    private Long skuNum;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}

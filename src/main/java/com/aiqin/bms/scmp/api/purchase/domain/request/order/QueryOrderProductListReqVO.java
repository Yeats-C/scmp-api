package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-17
 * @time: 10:04
 */
@Data
@ApiModel("订单商品列表请求vo")
public class QueryOrderProductListReqVO extends PageReq {

    @ApiModelProperty(value = "创建时间从")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateStart;

    @ApiModelProperty(value = "创建时间到")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateEnd;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库编码")
    private String warehouseCode;

    @ApiModelProperty("订单编码(订单号)")
    private String orderCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("门店编码")
    private String storeCode;

    @ApiModelProperty("门店名称")
    private String storeName;

    @ApiModelProperty("公司编码")
    private String companyCode;

}

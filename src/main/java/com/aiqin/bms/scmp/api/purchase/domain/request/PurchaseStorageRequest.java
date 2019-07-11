package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReport;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SavePurchaseScoreReqVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-07-10
 **/
@Data
public class PurchaseStorageRequest {

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="质检报告数据")
    @JsonProperty("report_request")
    private ProductSkuInspReport reportRequest;

    @ApiModelProperty(value="采购供应商评分")
    @JsonProperty("score_request")
    private SavePurchaseScoreReqVo scoreRequest;

    @ApiModelProperty(value="创建者id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建者")
    @JsonProperty("create_by_name")
    private String createByName;

}

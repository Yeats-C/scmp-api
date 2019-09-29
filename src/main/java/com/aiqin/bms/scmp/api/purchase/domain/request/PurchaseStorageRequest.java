package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.PurchaseInspectionReport;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderProduct;
import com.aiqin.bms.scmp.api.supplier.domain.request.score.SavePurchaseScoreReqVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-07-10
 **/
@Data
public class PurchaseStorageRequest {

    @ApiModelProperty(value="采购单id")
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @ApiModelProperty(value="采购单code")
    @JsonProperty("purchase_order_code")
    private String purchaseOrderCode;

    @ApiModelProperty(value="质检报告数据")
    @JsonProperty("inspection_report")
    private List<PurchaseInspectionReport> inspectionReport;

    @ApiModelProperty(value="采购供应商评分")
    @JsonProperty("score_request")
    private SavePurchaseScoreReqVo scoreRequest;

    @ApiModelProperty(value="创建者id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="创建者")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="公司code")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value="公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value="采购单商品集合")
    @JsonProperty("order_list")
    private List<PurchaseOrderProduct> orderList;

    @ApiModelProperty(value="入库次数")
    @JsonProperty("purchase_num")
    private Integer purchaseNum;

    @ApiModelProperty("行号")
    @JsonProperty("lineNum")
    private Long lineNum;

    @ApiModelProperty(value="入库单code")
    @JsonProperty("inbound_oder_code")
    private String inboundOderCode;
}

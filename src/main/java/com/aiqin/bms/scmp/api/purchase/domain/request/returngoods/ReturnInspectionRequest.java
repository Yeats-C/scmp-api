package com.aiqin.bms.scmp.api.purchase.domain.request.returngoods;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-06-10
 **/
@Data
@ApiModel("退货验货保存请求类")
public class ReturnInspectionRequest {

    @ApiModelProperty("退货单号")
    @JsonProperty("return_order_code")
    private String returnOrderCode;

    @ApiModelProperty("验货备注")
    @JsonProperty("inspection_remark")
    private String inspectionRemark;

    @ApiModelProperty("验货操作人")
    private String operator;

    @ApiModelProperty("验货操作人编码")
    @JsonProperty("operator_code")
    private String operatorCode;

    @ApiModelProperty("验货商品信息")
    @JsonProperty("item_list")
    private List<ReturnOrderInfoInspectionItem> itemList;
}

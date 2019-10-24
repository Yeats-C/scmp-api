package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrder;
import com.aiqin.bms.scmp.api.purchase.domain.PurchaseOrderDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-25
 **/
@Data
public class PurchaseOrderRequest {

    @ApiModelProperty(value="采购单")
    @JsonProperty("purchase_order")
    private PurchaseOrder purchaseOrder;

    @ApiModelProperty(value="采购单详情")
    @JsonProperty("order_details")
    private PurchaseOrderDetails orderDetails;

    @ApiModelProperty(value="采购申请单ids")
    @JsonProperty("apply_ids")
    private List<String> applyIds;

    @ApiModelProperty(value="文件信息")
    @JsonProperty("file_list")
    private List<FileRecord> fileList;

    @ApiModelProperty(value="人员编码")
    @JsonProperty("person_id")
    private String personId;

    @ApiModelProperty(value="人员名称")
    @JsonProperty("person_name")
    private String personName;

    @ApiModelProperty(value="审批名称")
    @JsonProperty("checkout_name")
    private String checkoutName;
}

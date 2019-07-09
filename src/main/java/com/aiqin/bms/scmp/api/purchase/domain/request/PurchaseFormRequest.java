package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2019-06-24
 **/
@Data
public class PurchaseFormRequest extends PagesRequest {

    @ApiModelProperty(value="采购申请单ids")
    @JsonProperty("apply_ids")
    private List<String> applyIds;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value="结算方式编码")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value="采购申请id")
    @JsonProperty("purchase_apply_id")
    private String purchaseApplyId;

    @ApiModelProperty(value="修改者id")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="修改者")
    @JsonProperty("update_by_name")
    private String updateByName;

}

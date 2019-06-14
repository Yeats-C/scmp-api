package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-06-13
 **/
@Data
public class PurchaseApplyRequest {

    @ApiModelProperty(value="采购申请单")
    @JsonProperty("purchase_apply_code")
    private String purchaseApplyCode;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购申请类型   0.自动  1.手动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value="采购申请状态 0.待提交 1.已完成")
    @JsonProperty("apply_status")
    private Integer applyStatus;

    @ApiModelProperty(value="开始时间")
    @JsonProperty("begin_time")
    private String beginTime;

    @ApiModelProperty(value="结束时间")
    @JsonProperty("finish_time")
    private String finishTime;

}

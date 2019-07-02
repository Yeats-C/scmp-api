package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: zhao shuai
 * @create: 2019-06-13
 **/
@Data
public class PurchaseApplyResponse {

    @ApiModelProperty(value="采购申请id")
    @JsonProperty("purchase_apply_id")
    private String purchaseApplyId;

    @ApiModelProperty(value="采购申请单")
    @JsonProperty("purchase_apply_code")
    private String purchaseApplyCode;

    @ApiModelProperty(value="采购申请类型   0.自动  1.手动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value="采购申请状态 0.待提交 1.已完成")
    @JsonProperty("apply_status")
    private Integer applyStatus;

    @ApiModelProperty(value="采购组编码")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="采购组名称")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value="创建者")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value="sku数量")
    @JsonProperty("sku_count")
    private Integer skuCount;

    @ApiModelProperty(value="单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value="采购含税金额")
    @JsonProperty("product_total_amount")
    private Integer productTotalAmount;

    @ApiModelProperty(value="实物返金额")
    @JsonProperty("return_amount")
    private Integer returnAmount;
}

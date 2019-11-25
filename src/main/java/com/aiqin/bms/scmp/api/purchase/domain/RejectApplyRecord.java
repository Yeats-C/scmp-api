package com.aiqin.bms.scmp.api.purchase.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class RejectApplyRecord {
    @ApiModelProperty(value = "")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value = "申请单类型: 0 手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value = "采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value = "")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value = "退供申请单状态: 0  已完成 1 待提交")
    @JsonProperty("apply_record_status")
    private Integer applyRecordStatus;

    @ApiModelProperty(value = "总sku数量")
    @JsonProperty("sum_sku")
    private Integer sumSku;

    @ApiModelProperty(value = "单品金额")
    @JsonProperty("sum_count")
    private Integer sumCount;

    @ApiModelProperty(value = "商品含税金额")
    @JsonProperty("sum_amount")
    private BigDecimal sumAmount;

    @ApiModelProperty(value = "实物返商品含税金额")
    @JsonProperty("sum_return_amount")
    private BigDecimal sumReturnAmount;

    @ApiModelProperty(value = "赠品含税金额")
    @JsonProperty("sum_gift_amount")
    private BigDecimal sumGiftAmount;

    @ApiModelProperty(value="是否删除 0 否 1是")
    @JsonProperty("status")
    private String status;

    @ApiModelProperty(value = "")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value = "")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value = "")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value = "")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty("创建人公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("创建人公司名称")
    @JsonProperty("company_name")
    private String companyName;

}
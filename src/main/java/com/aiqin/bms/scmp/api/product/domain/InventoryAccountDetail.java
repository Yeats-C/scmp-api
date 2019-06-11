package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wuyongqiang
 * @description 库存出入库记录明细对象
 * @date 2018/11/22 17:52
 */
@Data
@ApiModel("库存出入库记录明细")
public class InventoryAccountDetail {
    @ApiModelProperty(value = "出/入库单号")
    @JsonProperty("master_number")
    private String masterNumber;

    @ApiModelProperty(value = "关联单号")
    @JsonProperty("relate_number")
    private String relateNumber;

    @ApiModelProperty(value = "单据类型")
    @JsonProperty("bill_type")
    private Integer billType;

    @ApiModelProperty(value = "制作人")
    @JsonProperty("creator")
    private String creator;

    @JsonProperty("create_by_name")
    @ApiModelProperty("创建人名称")
    public String createByName;

    @ApiModelProperty(value = "制单时间")
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "明细列表")
    @JsonProperty("accounts")
    private List<InventoryAccount> accounts;

    public String getMasterNumber() {
        return masterNumber;
    }

    public void setMasterNumber(String masterNumber) {
        this.masterNumber = masterNumber;
    }

    public String getRelateNumber() {
        return relateNumber;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<InventoryAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<InventoryAccount> accounts) {
        this.accounts = accounts;
    }
}

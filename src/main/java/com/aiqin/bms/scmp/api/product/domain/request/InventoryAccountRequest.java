package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyongqiang
 * @description 库存出入库记录查询对象
 * @date 2018/11/21 19:30
 */
@ApiModel("库存出入库记录查询参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAccountRequest extends PagesRequest {
    @ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;

    @ApiModelProperty(value = "记录类型",required=true)
    @JsonProperty("record_type")
    private Integer recordType;

    @ApiModelProperty(value = "单据类型")
    @JsonProperty("bill_type")
    private Integer billType;

    @ApiModelProperty(value = "单号/关联单号")
    @JsonProperty("record_number")
    private String recordNumber;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }
}

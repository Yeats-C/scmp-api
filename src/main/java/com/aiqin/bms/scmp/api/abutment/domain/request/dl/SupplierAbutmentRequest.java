package com.aiqin.bms.scmp.api.abutment.domain.request.dl;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.DeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyPurchaseGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SupplierAbutmentRequest extends ApplySupplyCompany {

    @ApiModelProperty(value="供应商的发货信息")
    @JsonProperty("delivery_list")
    private List<DeliveryInformation> deliveryList;

    @ApiModelProperty(value="采购组信息")
    @JsonProperty("group_list")
    private List<SupplyCompanyPurchaseGroup> groupList;
}

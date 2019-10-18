package com.aiqin.bms.scmp.api.purchase.domain.pdf;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhao shuai
 * @create: 2019-10-16
 **/
@Data
public class SupplyPdfResponse {

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supply_code")
    private String supplyCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supply_name")
    private String supplyName;

    @ApiModelProperty(value="手机号")
    @JsonProperty("mobile_phone")
    private String mobilePhone;

    @ApiModelProperty(value="传真")
    @JsonProperty("fax")
    private String fax;

    @ApiModelProperty(value="联系人名称")
    @JsonProperty("contact_name")
    private String contactName;

    @ApiModelProperty(value="详细地址")
    @JsonProperty("address")
    private String address;
}

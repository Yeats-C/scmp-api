package com.aiqin.fms.wechat.api.domain.sap.supplier;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author sunx
 * @description
 * @date 2019-08-05
 */
@Data
@ApiModel("scmp供应商银行信息")
public class SupplierBank {

    @ApiModelProperty("更改标识银行必输：创建-I；修改-U；删除-D；创建/更改-M")
    @JsonProperty("TASK")
    @NotBlank(message = "更改标识银行必输")
    private String flag;

    @ApiModelProperty("银行名称")
    @JsonProperty("BANKA")
    private String bankName;

    @ApiModelProperty("银行国家代号")
    @JsonProperty("BANKS")
    private String bankCountryNo="CN";


    @ApiModelProperty("银行编号")
    @JsonProperty("BANKL")
    private String bankIdxNo;

    @ApiModelProperty("银行帐户号码")
    @JsonProperty("BANKN")
    private String bankNo;

    @ApiModelProperty("银行帐户的参考规定")
    @JsonProperty("BKREF")
    private String bankNote;


    @ApiModelProperty("帐户持有人姓名")
    @JsonProperty("KOINH")
    private String bankUserName;
}

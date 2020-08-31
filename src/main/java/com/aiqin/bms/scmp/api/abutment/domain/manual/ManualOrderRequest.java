package com.aiqin.bms.scmp.api.abutment.domain.manual;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@Data
@Api("DL推送单据参数")
public class ManualOrderRequest {

    @NotEmpty(message = "单号集合不能为空")
    @ApiModelProperty(value="单号")
    @JsonProperty("order_codes")
    private List<String> orderCodes;

    @NotNull(message = "业务类型不能为空")
    @ApiModelProperty(value="业务类型  1：销售 2：退货  3：采购  4：退供")
    @JsonProperty("type")
    private Integer type;
}

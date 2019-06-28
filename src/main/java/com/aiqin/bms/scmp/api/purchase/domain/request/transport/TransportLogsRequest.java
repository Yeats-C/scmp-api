package com.aiqin.bms.scmp.api.purchase.domain.request.transport;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransportLogsRequest extends PageReq {

    @ApiModelProperty("运输单号")
    @JsonProperty("transport_code")
    private String transportCode;
}

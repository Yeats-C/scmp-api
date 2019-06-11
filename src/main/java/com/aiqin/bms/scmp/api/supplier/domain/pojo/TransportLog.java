package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransportLog extends CommonBean {
    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("类型")
    @JsonProperty("type")
    private String type;

    @ApiModelProperty("运输单号")
    @JsonProperty("transport_code")
    private String transportCode;

    @ApiModelProperty("内容")
    @JsonProperty("content")
    private String content;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

}

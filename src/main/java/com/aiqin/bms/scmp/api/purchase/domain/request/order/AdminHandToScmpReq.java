package com.aiqin.bms.scmp.api.purchase.domain.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class AdminHandToScmpReq implements Serializable {
    /***
     * 要重新回传的单号
     */
    @NotEmpty(message = "单号集合不能为空")
    @ApiModelProperty(value="要推送的单号")
    @JsonProperty("order_codes")
    private List<String> orderCodes;
    /**
     * 业务类型  销售：7，  退货2, 采购：4，退供5
     */
    @NotNull(message = "业务类型不能为空")
    @ApiModelProperty(value="业务类型 销售：7，  退货2, 采购：4，退供5")
    @JsonProperty("type")
    private Integer type;
}

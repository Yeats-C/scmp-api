package com.aiqin.bms.scmp.api.purchase.domain.response.returngoods;

import com.aiqin.bms.scmp.api.product.domain.pojo.Inbound;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfo;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoLog;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-06-11 14:26
 **/
@Data
@ApiModel("退货详情和日志")
public class ReturnOrderDetailResponse extends ReturnOrderInfo {

    @ApiModelProperty("日志信息")
    @JsonProperty("log_list")
    private List<ReturnOrderInfoLog> logList;

    @ApiModelProperty("入库单基本信息")
    @JsonProperty("inbound_list")
    private List<Inbound> inboundList;
}

package com.aiqin.bms.scmp.api.purchase.domain.response.reject;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordTransportCenter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-05-12
 **/
@ApiModel("退供申请单信息与分仓信息")
@Data
public class RejectApplyAndTransportResponse extends RejectApplyRecord {

    @ApiModelProperty(value = "退供分仓列表")
    @JsonProperty("reject_transport_list")
    private List<RejectApplyRecordTransportCenter> rejectTransportList;
}

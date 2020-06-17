package com.aiqin.bms.scmp.api.purchase.domain.response.returngoods;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.order.OrderInfoItemProductBatch;
import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-06-15 10:52
 **/
@Data
public class ReturnOrderInspectionResponse extends ReturnOrderInfoItem {

    @ApiModelProperty("验货的批次信息")
    @JsonProperty("batch_list")
    private List<OrderInfoItemProductBatch> batchList;
}

package com.aiqin.bms.scmp.api.product.domain.request.returnsupply;

import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordBatch;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecordDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 * 通过退供生成出库单vo
 * @author: zth
 * @date: 2019-03-01
 * @time: 16:58
 */
@Data
@ApiModel("通过退供生成出库单vo")
public class ReturnSupplyToOutBoundReqVo {

    @ApiModelProperty("主表数据")
    @JsonProperty("reject_record")
    RejectRecord rejectRecord;

    @ApiModelProperty("商品数据")
    @JsonProperty("detail_list")
    List<RejectRecordDetail> detailList;

    @ApiModelProperty("批次数据")
    @JsonProperty("batch_list")
    List<RejectRecordBatch> batchList;

}

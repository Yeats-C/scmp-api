package com.aiqin.bms.scmp.api.purchase.domain.response.reject;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyBatch;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecord;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordTransportCenter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: zhao shuai
 * @create: 2020-05-13
 **/
@ApiModel("退供申请单分组后的信息")
@Data
public class RejectApplyGroupResponse extends RejectApplyRecord {

    @ApiModelProperty(value = "仓库信息")
    @JsonProperty("center_list")
    private List<RejectApplyRecordTransportCenter> centerList;

    @ApiModelProperty(value = "商品信息")
    @JsonProperty("detail_list")
    private List<RejectApplyRecordDetail> detailList;

    @ApiModelProperty(value = "批次信息")
    @JsonProperty("batch_list")
    private List<RejectApplyBatch> batchList;
}

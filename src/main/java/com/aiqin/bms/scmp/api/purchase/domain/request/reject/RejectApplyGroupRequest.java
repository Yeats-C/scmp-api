package com.aiqin.bms.scmp.api.purchase.domain.request.reject;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
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
 * @create: 2020-05-14 17:12
 **/
@Data
@ApiModel("退供申请单提交请求类")
public class RejectApplyGroupRequest extends RejectApplyRecord {

    @ApiModelProperty(value = "提交类型 0.保存 1.提交审核")
    @JsonProperty("submit_type")
    private Integer submitType;

    @ApiModelProperty(value = "仓库信息")
    @JsonProperty("center_list")
    private List<RejectApplyRecordTransportCenter> centerList;

    @ApiModelProperty(value = "商品信息")
    @JsonProperty("detail_list")
    private List<RejectApplyRecordDetail> detailList;

    @ApiModelProperty(value = "文件信息")
    @JsonProperty("file_list")
    private List<FileRecord> fileList;
}

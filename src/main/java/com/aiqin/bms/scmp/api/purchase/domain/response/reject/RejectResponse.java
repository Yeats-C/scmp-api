package com.aiqin.bms.scmp.api.purchase.domain.response.reject;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
import com.aiqin.bms.scmp.api.purchase.domain.OperationLog;
import com.aiqin.bms.scmp.api.purchase.domain.RejectRecord;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RejectResponse extends RejectRecord {

    @ApiModelProperty("出库单编号")
    @JsonProperty("outbound_oder_id")
    private Long outboundOderId;

    @ApiModelProperty("出库单编号")
    @JsonProperty("outbound_oder_code")
    private String outboundOderCode;

    @ApiModelProperty("文件列表")
    @JsonProperty("file_list")
    private List<FileRecord> fileList;

    @ApiModelProperty("日志记录")
    @JsonProperty("log_list")
    private List<OperationLog> logList;

}

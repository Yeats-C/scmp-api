package com.aiqin.bms.scmp.api.product.domain.request.newproduct;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("增加申请")
@Data
public class ApplyDraftReqVO {

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("申请时间起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("申请结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    private Date selectionEffectiveEndTime;

    private List<DraftSaveListReqVO>draftSaveListReqVO;

}

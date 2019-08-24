package com.aiqin.bms.scmp.api.supplier.domain.request.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @date 2019/4/30 14:46

 */
@ApiModel("保存标签信息")
@Data
public class SaveUseTagRecordItemReqVo {

    @ApiModelProperty("标签编码")
    private String tagCode;

    @ApiModelProperty("标签名称")
    private String tagName;
}

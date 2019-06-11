package com.aiqin.bms.scmp.api.supplier.domain.request.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className UpdateUseNumReqVo
 * @date 2019/4/29 15:47
 * @description TODO
 */

@ApiModel("更新使用数量")
@Data
public class UpdateUseNumReqVo {

    @ApiModelProperty("标签编码")
    private String tagCode;

    @ApiModelProperty("变化数量")
    private Integer changeNum;
}

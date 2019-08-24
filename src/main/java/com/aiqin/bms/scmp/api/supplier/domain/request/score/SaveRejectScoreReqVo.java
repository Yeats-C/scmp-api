package com.aiqin.bms.scmp.api.supplier.domain.request.score;

import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordItemReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveRejectScoreReqVo
 * @date 2019/5/23 15:51

 */
@ApiModel("保存退供评分Vo")
@Data
public class SaveRejectScoreReqVo {

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("退换货及时性")
    private BigDecimal returnTimely;

    @ApiModelProperty("标签信息")
    private List<SaveUseTagRecordItemReqVo> tagInfoList;
}

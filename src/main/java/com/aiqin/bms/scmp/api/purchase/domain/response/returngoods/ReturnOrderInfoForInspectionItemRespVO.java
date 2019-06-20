package com.aiqin.bms.scmp.api.purchase.domain.response.returngoods;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-20
 * @time: 16:45
 */
@Data
public class ReturnOrderInfoForInspectionItemRespVO extends ReturnOrderInfoItemRespVO{

    @ApiModelProperty("是否管理保质期")
    private Byte qualityAssuranceManagement;

    @ApiModelProperty("保质期基数")
    private String qualityNumber;

    @ApiModelProperty("保质(1:天 2:月 3:年)")
    private String qualityDate;

}

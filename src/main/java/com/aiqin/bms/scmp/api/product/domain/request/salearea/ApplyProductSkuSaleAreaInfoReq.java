package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-13
 * @time: 14:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyProductSkuSaleAreaInfoReq {

    @ApiModelProperty("与之前相同")
    private Integer applyStatus;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请表单号")
    private String formNo;

    @ApiModelProperty("是否生效(0未生效1已生效）")
    private Integer beEffective;
}

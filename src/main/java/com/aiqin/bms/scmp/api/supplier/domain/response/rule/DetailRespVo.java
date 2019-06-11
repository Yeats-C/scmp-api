package com.aiqin.bms.scmp.api.supplier.domain.response.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className DetailRespVo
 * @date 2019/5/23 10:59
 * @description TODO
 */
@ApiModel("规则响应Vo")
@Data
public class DetailRespVo {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("自动退货天数")
    private Integer autoReturnGoodsDay;
}

package com.aiqin.bms.scmp.api.supplier.domain.request.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className SaveReqVo
 * @date 2019/5/23 10:57
 * @description TODO
 */
@ApiModel("保存规则Vo")
@Data
public class SaveReqVo {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("自动退货天数")
    private Integer autoReturnGoodsDay;

}

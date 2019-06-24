package com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 描述: 采购组查看详情返回实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("采购组查看详情返回实体")
public class PurchaseGroupResVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("启用禁用状态")
    private Byte enable;

    @ApiModelProperty("采购组专员")
    private List<PurchaseGroupBuyerResVo> groupBuyerResVos;

    @ApiModelProperty("负责人编号")
    private String responsiblePersonCode;

    @ApiModelProperty("负责人名称")
    private String responsiblePersonName;
}

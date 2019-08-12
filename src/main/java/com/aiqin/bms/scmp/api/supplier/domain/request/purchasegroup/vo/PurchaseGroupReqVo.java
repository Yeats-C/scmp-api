package com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 描述: 采购管理组请求保存实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("采购管理组请求保存实体")
public class PurchaseGroupReqVo {

    @ApiModelProperty("采购组名称")
    @NotEmpty(message = "采购组名称不能为空")
    private String purchaseGroupName;

    @ApiModelProperty("负责人编号")
    private String responsiblePersonCode;

    @ApiModelProperty("负责人名称")
    private String responsiblePersonName;

    @ApiModelProperty("排序")
    private Integer purchaseGroupOrder;

    @ApiModelProperty("采购组人员")
    @Valid
    List<PurchaseGroupBuyerReqVo> groupBuyerReqVoList;
}

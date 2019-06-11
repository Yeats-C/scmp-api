package com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 描述: 采购组更新请求实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("采购组更新请求实体")
public class UpdatePurchaseGroupReqVo {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @NotEmpty(message = "采购管理组名称不能为空")
    private String purchaseGroupName;

    @ApiModelProperty("启用禁用状态")
    private Byte enable;

    @ApiModelProperty("采购管理组人员")
    private List<UpdatePurchaseGroupBuyerReqVo> groupBuyerReqVoList;
}

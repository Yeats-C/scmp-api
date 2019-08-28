package com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.PurchaseGroup;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.PurchaseGroupBuyerReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("批量修改采购组")
public class BatchOperatePurchaseGroupReqVO {

    @ApiModelProperty("操作类型 1修改，2删除")
    @NotNull(message = "操作类型必填1修改2删除")
    private Integer operationType;

    @ApiModelProperty("采购组编码集合")
    @NotEmpty(message = "采购组信息不能为空")
    private List<PurchaseGroup> purchaseGroups;

    @ApiModelProperty("采购组人员")
    @Valid
    @NotEmpty(message = "人员信息不能为空")
    List<PurchaseGroupBuyerReqVo> groupBuyerReqVoList;

}

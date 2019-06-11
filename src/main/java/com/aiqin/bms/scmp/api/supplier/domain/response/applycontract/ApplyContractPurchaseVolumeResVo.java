package com.aiqin.bms.scmp.api.supplier.domain.response.applycontract;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:申请制造商关联进货额返回实体详情
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("申请制造商关联进货额返回实体详情")
public class ApplyContractPurchaseVolumeResVo  extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("所属申请合同code")
    private String applyContractCode;

    @ApiModelProperty("0:无税1:有税")
    private Byte tax;

    @ApiModelProperty("金额(以分为单位)")
    private Long amountMoney;

    @ApiModelProperty("按比例")
    private Long proportion;

    @ApiModelProperty("或者金额(以分为单位)")
    private Long orAmountMoney;
}

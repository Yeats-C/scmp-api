package com.aiqin.bms.scmp.api.supplier.domain.request.contract.dto;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 描述: 合同关联进货额数据库交互实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("合同关联进货额数据库交互实体")
public class ContractPurchaseVolumeDTO extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("所属合同code")
    private String contractCode;

    @ApiModelProperty("0:无税1:有税")
    private Byte tax;

    @ApiModelProperty("金额(以分为单位)")
    private Long amountMoney;

    @ApiModelProperty("按比例")
    private Long proportion;

    @ApiModelProperty("或者金额(以分为单位)")
    private Long orAmountMoney;

    @ApiModelProperty("类型")
    private Byte planType;
}

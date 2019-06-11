package com.aiqin.bms.scmp.api.supplier.domain.response.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述:进货额返回实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("进货额返回实体")
public class ContractPurchaseVolumeResVo {
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

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;
}

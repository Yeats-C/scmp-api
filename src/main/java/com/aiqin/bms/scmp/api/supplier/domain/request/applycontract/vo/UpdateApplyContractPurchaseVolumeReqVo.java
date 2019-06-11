package com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 描述:修改申请合同接受进货额实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/13
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("修改申请合同接受进货额实体")
public class UpdateApplyContractPurchaseVolumeReqVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("0:无税1:有税")
    @NotNull(message = "请选择有税无税")
    private Byte tax;

    @ApiModelProperty("金额(以分为单位)")
    @NotNull(message = "金额不能为空")
    private Long amountMoney;

    @ApiModelProperty("按比例")
    private Long proportion;

    @ApiModelProperty("或者金额(以分为单位)")
    private Long orAmountMoney;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

}

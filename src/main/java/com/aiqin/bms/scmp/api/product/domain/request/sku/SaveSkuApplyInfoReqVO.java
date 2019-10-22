package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/14 0014 14:50
 */
@Data
@ApiModel("sku新增申请请求")
public class SaveSkuApplyInfoReqVO {

    @ApiModelProperty("sku编码集合")
    private List<String> skuCodes;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

    @ApiModelProperty("附件信息")
    private List<ApprovalFileInfoReqVo> approvalFileInfos;
}

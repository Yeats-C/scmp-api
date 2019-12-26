package com.aiqin.bms.scmp.api.product.domain.request.sku.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className DeleteSkuSupplyUnitReqVo
 * @date 2019/11/30 11:29
 */
@ApiModel("批量删除vo")
@Data
public class DeleteSkuSupplyUnitReqVo {

    @ApiModelProperty("需要提交申请的编码集合")
    @NotEmpty(message = "编码不能为空！")
    List<Long> supplierId;
}

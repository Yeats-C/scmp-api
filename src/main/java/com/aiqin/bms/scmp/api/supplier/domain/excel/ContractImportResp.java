package com.aiqin.bms.scmp.api.supplier.domain.excel;

import com.aiqin.bms.scmp.api.supplier.domain.request.applycontract.vo.ApplyContractReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("合同导入返回vo")
public class ContractImportResp {

    @ApiModelProperty("需要传入的vo")
    private List<ApplyContractReqVo> applyList;

    @ApiModelProperty("错误信息")
    private List<String> errors;
}

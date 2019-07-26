package com.aiqin.bms.scmp.api.supplier.domain.excel;

import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("供应商导入返回vo")
public class SupplierImportResp {
    @ApiModelProperty("需要传入的vo")
    private List<ApplySupplyCompanyReqVO> applyList;
    @ApiModelProperty("需要展示的vo")
    private List<SupplierImport> importVos;
}

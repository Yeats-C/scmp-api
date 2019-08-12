package com.aiqin.bms.scmp.api.supplier.domain.excel;

import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyReqVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupplierImportReq
 * @date 2019/7/26 14:37
 */
@ApiModel("供应商导入请求vo")
@Data
public class SupplierImportReq {
    private List<ApplySupplyCompanyReqVO> applyList;
    private String purchaseGroupCode;
    private String purchaseGroupName;
}

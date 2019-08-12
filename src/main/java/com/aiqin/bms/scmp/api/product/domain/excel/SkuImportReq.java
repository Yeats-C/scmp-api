package com.aiqin.bms.scmp.api.product.domain.excel;

import com.aiqin.bms.scmp.api.product.domain.request.sku.AddSkuInfoReqVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("sku导入请求vo")
public class SkuImportReq {
    private List<AddSkuInfoReqVO> addSkuList;
    private String purchaseGroupCode;
    private String purchaseGroupName;
}

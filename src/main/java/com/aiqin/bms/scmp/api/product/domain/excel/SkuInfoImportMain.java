package com.aiqin.bms.scmp.api.product.domain.excel;

import com.aiqin.bms.scmp.api.product.domain.request.sku.AddSkuInfoReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel("skuInfo导入后返回vo")
@NoArgsConstructor
@AllArgsConstructor
public class SkuInfoImportMain {
    @ApiModelProperty("保存vo")
    private List<AddSkuInfoReqVO> addSkuList;
    @ApiModelProperty("列表展示vo")
    private List<SkuInfoImportReally> importList;
}

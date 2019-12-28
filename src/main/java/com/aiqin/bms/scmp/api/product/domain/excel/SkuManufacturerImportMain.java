package com.aiqin.bms.scmp.api.product.domain.excel;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuManufacturer;
import com.aiqin.bms.scmp.api.product.domain.request.sku.AddSkuInfoReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel("sku导入后返回vo")
public class SkuManufacturerImportMain {
    @ApiModelProperty("保存vo")
    private List<ProductSkuManufacturer> addList;

    @ApiModelProperty("列表展示vo")
    private List<SkuManufactureImport> skuManufactureImports;

    @ApiModelProperty("错误信息")
    private List<String> error;

}

package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by 爱亲 on 2019/7/19.
 */
@ApiModel("采购变价批量导入实体")
@Data
public class ProductSkuChangePriceImportRequest {

    @ApiModelProperty("skucode")
    private List<String> skuCodeList;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

}
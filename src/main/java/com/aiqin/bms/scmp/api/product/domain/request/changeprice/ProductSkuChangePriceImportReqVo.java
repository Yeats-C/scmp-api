package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 爱亲 on 2019/7/19.
 */
@ApiModel("采购变价批量导入Vo")
@Data
public class ProductSkuChangePriceImportReqVo {

    @ApiModelProperty("需要导入的表格文件")
    private MultipartFile file;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("变价类型")
    private Integer changePriceType;


    public ProductSkuChangePriceImportReqVo(MultipartFile file, String purchaseGroupCode, String companyCode, Integer changePriceType) {
        this.file = file;
        this.purchaseGroupCode = purchaseGroupCode;
        this.companyCode = companyCode;
        this.changePriceType = changePriceType;
    }

    public ProductSkuChangePriceImportReqVo() {
    }
}
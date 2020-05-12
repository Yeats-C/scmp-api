package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 描述:调拨单导入sku请求实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/11
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("调拨单导入sku请求实体")
public class AllocationImportSkuReqVo {
    @ApiModelProperty("需要导入的表格文件")
    private MultipartFile file;

    @ApiModelProperty(value = "供应单位编码",hidden = true)
    private String supplyCode;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    public AllocationImportSkuReqVo(MultipartFile file, String transportCenterCode, String warehouseCode) {
        this.file = file;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
    }

    public AllocationImportSkuReqVo(MultipartFile file, String supplyCode, String transportCenterCode, String warehouseCode, String purchaseGroupCode, String companyCode) {
        this.file = file;
        this.supplyCode = supplyCode;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.purchaseGroupCode = purchaseGroupCode;
        this.companyCode = companyCode;
    }

    public AllocationImportSkuReqVo(MultipartFile file,String transportCenterCode, String warehouseCode, String purchaseGroupCode) {
        this.file = file;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.purchaseGroupCode = purchaseGroupCode;
    }
}

package com.aiqin.bms.scmp.api.product.domain.dto.salearea;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-10
 * @time: 15:13
 */
@Data
public class ProductSkuSaleAreaDraftDTO {

    @ApiModelProperty("申请编码")
    private String saleAreaCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("供货渠道类别编码")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("直送供应商编码")
    private String directDeliverySupplierCode;

    @ApiModelProperty("直送供应商名称")
    private String directDeliverySupplierName;

    @ApiModelProperty("1新增2修改")
    private Integer applyType;

    @ApiModelProperty("正式编码")
    private String officialCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    private List<ProductSkuSaleAreaInfoDraftDTO> infos;
}

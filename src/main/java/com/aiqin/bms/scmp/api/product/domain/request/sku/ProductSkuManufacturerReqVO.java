package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @className ProductSkuManufacturerReqVO
 * @date 2019/11/19
 */
@ApiModel("sku商品制造商管理列表查询VO")
@Data
public class ProductSkuManufacturerReqVO extends PageReq {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("制造商名称")
    private String manufacturerName;

    @ApiModelProperty("制造商编号")
    private String manufacturerCode;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("属性编码")
    private String productPropertyCode;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("品类编码")
    private String productCategoryCode;

    @ApiModelProperty("品类集合编码 1级编码 2级编码 3级编码 4级编码")
    private List<String> productCategoryCodes;

    @ApiModelProperty(value = "1级品类编码",hidden = true)
    private String productCategoryLv1Code;

    @ApiModelProperty(value ="2级品类编码",hidden = true)
    private String productCategoryLv2Code;

    @ApiModelProperty(value ="3级品类编码",hidden = true)
    private String productCategoryLv3Code;

    @ApiModelProperty(value ="4级品类编码",hidden = true)
    private String productCategoryLv4Code;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("品牌编码")
    private String productBrandCode;

    @ApiModelProperty("状态(0:再用 1:停止进货 2:停止配送 3:停止销售)")
    private Byte skuStatus;

    @ApiModelProperty("状态(0禁止 1可用)")
    private Byte usageStatus;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Byte isDefault;

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private String personId;


}

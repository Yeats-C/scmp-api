package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("sku列表请求条件")
public class QuerySkuListReqVO extends PageReq {

    @ApiModelProperty("商品(SPU)名称")
    private String productName;

    @ApiModelProperty("商品(SPU编码")
    private String productCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("属性编码")
    private String productPropertyCode;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("品类编码")
    private String productCategoryCode;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("品牌编码")
    private String productBrandCode;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("状态(0:再用 1:停止进货 2:停止配送 3:停止销售)")
    private Byte skuStatus;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;


}

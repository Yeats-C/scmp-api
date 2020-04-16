package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("商品批次管理入参")
public class QueryProductSkuBatchReqVO extends PageReq {
    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("属性编码")
    private String productPropertyCode;


    @ApiModelProperty("品类code")
    private String productCategoryCode;


    @ApiModelProperty("品牌编码")
    private String productBrandCode;


    @ApiModelProperty("物流中心code")
    private String transportCenterCode;

    @ApiModelProperty("库房code")
    private String warehouseCode;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

}

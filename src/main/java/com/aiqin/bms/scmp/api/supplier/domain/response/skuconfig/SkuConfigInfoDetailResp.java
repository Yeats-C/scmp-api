package com.aiqin.bms.scmp.api.supplier.domain.response.skuconfig;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/11 0011 15:28
 */
@Data
@ApiModel("sku配置详情返回对象")
public class SkuConfigInfoDetailResp {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品类别名称")
    private String productSortName;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("sku配置项数据")
    private List<SkuConfigDetailRespVO> skuConfigDetailRespVOS;
}

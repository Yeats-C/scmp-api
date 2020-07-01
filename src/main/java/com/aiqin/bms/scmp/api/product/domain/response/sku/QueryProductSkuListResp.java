package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/12 0012 17:26
 */
@Data
@ApiModel("sku管理返回对象")
public class QueryProductSkuListResp {
    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("所属商品编码")
    private String productCode;

    @ApiModelProperty("商品/赠品(0:商品，1:赠品，2:组合商品)")
    private Byte goodsGifts;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("品类编码")
    private String productCategoryCode;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("商品上下架")
    private String onSale;

    @JsonProperty("sku_price")
    @ApiModelProperty("动销价")
    private BigDecimal skuPrice;

    @ApiModelProperty("类别")
    private String productSort;

    @ApiModelProperty("颜色")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("货号")
    private String itemNumber;

    @ApiModelProperty("销售条形码")
    private String barCode;

    @ApiModelProperty(value = "sku渠道信息")
    private List<ProductSkuChannelRespVo> productSkuChannels;

}

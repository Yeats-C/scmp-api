package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author knight.xie
 * @version 1.0
 * @className PurchaseSaleStockRespVo
 * @date 2019/5/14 15:10
 */
@ApiModel("SKU进销存消息返回")
@Data
public class PurchaseSaleStockRespVo extends CommonBean {

    @ApiModelProperty("主键ID")
    private Long Id;

    @ApiModelProperty("商品sku 编码")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("商品编号")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("类型(0:库存,1:采购, 2:销售, 3:门店销售)")
    private Byte type;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("单位code")
    private String unitCode;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty("条形码")
    private String barCode;

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

    @ApiModelProperty("最大订购数-销售信息独有")
    private Integer maxOrderNum;

    @ApiModelProperty("描述-门店销售独有")
    private String description;

    @ApiModelProperty("是否缺省(0:否,1：是)-门店销售独有")
    private Byte isDefault;

    @ApiModelProperty("拆零系数")
    private Long zeroRemovalCoefficient;

    @ApiModelProperty("库存单位code")
    private String stockUnitCode;

    @ApiModelProperty(value = "库存单位名称")
    private String stockUnitName;
}

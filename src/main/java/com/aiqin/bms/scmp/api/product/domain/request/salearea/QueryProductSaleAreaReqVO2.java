package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-10
 * @time: 14:00
 */
@Data
@ApiModel("销售区域查询请求vo")
public class QueryProductSaleAreaReqVO2 extends PageReq {


    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;


    @ApiModelProperty("商品属性code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    private String productPropertyName;

    @ApiModelProperty("供货渠道类别code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("是否上架，0为未上架，1为上架")
    private Byte onSale;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("条码")
    private String barCode;


    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;


    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    private String procurementSectionName;
    @ApiModelProperty("销售区域编码")
    private String code;

    @ApiModelProperty("销售区域名称")
    private String name;

    @ApiModelProperty("销售区域 是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty(value = "公司编码",hidden = true)
    private String companyCode;



    private String personId;

}

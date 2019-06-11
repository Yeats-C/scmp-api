package com.aiqin.bms.scmp.api.product.domain.response.sku.purchase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author HuangLong
 * @date 2018/12/25
 */
@ApiModel("采购单商品对象返回vo")
@Data
public class PurchaseItemRespVo {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("采购主表编码")
    private String purchaseCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("商品code")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("商品类型名称")
    private String productTypeName;

    @ApiModelProperty("商品类型编码")
    private String productTypeCode;

    @ApiModelProperty("商品品牌编码")
    private String productBrandCode;

    @ApiModelProperty("商品品牌名称")
    private String productBrandName;

    @ApiModelProperty("单位编码")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("规格编码")
    private String specCode;

    @ApiModelProperty("规格名称")
    private String specName;

    @ApiModelProperty("颜色编码")
    private String colorCode;

    @ApiModelProperty("颜色名称")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("商品类别编码")
    private String productSortCode;

    @ApiModelProperty("商品类别名字")
    private String productSortName;

    @ApiModelProperty("税率")
    private Long taxRate;

    @ApiModelProperty("含税单价")
    private Long price;

    @ApiModelProperty("采购数量")
    private Long num;


    @ApiModelProperty("采购含税总价")
    private Long totalPrice;
    @ApiModelProperty("实际到货数量")
    private Long actualNum;

    @ApiModelProperty("实际到货含税总价")
    private Long actualTotalPrice;

    @ApiModelProperty("主单位和采购单位间的转换系数(第一个对应基础信息的，第二个对应进货信息的）")
    private String convertNum;

    @ApiModelProperty("sku图片地址")
    private String picUrl;

    @ApiModelProperty("sku状态，0为启用，1为禁用")
    private Byte skuStatus;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;
}

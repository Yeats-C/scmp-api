package com.aiqin.bms.scmp.api.product.domain.response.sku.file;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuFile;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuFileRespVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Auther: mamingze
 * @Date: 2019-10-28 10:58
 * @Description:
 */
@Data
@ApiModel("商品文件管理")
public class ProductSkuFileRespVo {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("所属商品编码")
    private String productCode;

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
    private Long skuPrice;

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

    @ApiModelProperty("商品类别(部门)code")
    private String productSortCode;

    @ApiModelProperty("商品类别(部门)名称")
    private String productSortName;

    @ApiModelProperty("所属文件列表")
    private List<ProductSkuFileRespVO> productSkuFileList;

}

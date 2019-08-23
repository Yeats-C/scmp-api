package com.aiqin.bms.scmp.api.product.domain.response.sku.config;

import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuSupplyUnitRespVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author knight.xie
 * @version 1.0
 * @className SkuConfigDetailRepsVo
 * @date 2019/5/25 18:39
 */
@Data
@ApiModel("SKU配置信息详情返回")
public class SkuConfigDetailRepsVo {

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品名称")
    private String productCode;

    @ApiModelProperty("商品编码")
    private String productName;

    @ApiModelProperty("商品类别code")
    private String productSortCode;

    @ApiModelProperty("商品类别名称")
    private String productSortName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("供货渠道类别code")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("商品类型(0:商品，1:赠品)")
    private Byte skuType;

    @ApiModelProperty("商品类型")
    private String skuTypeName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("SKU配置列表信息")
    private List<SkuConfigsRepsVo> configs;

    @ApiModelProperty("供应商列表")
    private List<ProductSkuSupplyUnitRespVo> supplierList;

    public String getSkuTypeName() {
       if (Objects.equals(StatusTypeCode.GOOD.getStatus(),skuType)) {
            return StatusTypeCode.GOOD.getName();
       } else {
           return StatusTypeCode.Gift.getName();
       }
    }
}

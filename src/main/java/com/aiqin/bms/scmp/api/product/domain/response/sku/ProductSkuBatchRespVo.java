package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author HuangLong
 * @date 2018/12/25
 */
@ApiModel("商品批次列表数据")
@Data
public class ProductSkuBatchRespVo {
    @ApiModelProperty("主键id")
    private Long id;


    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;


    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("商品类型名称")
    private String productTypeName;


    @ApiModelProperty("商品/赠品(0:商品，1:赠品 2:组合商品)")
    private Byte goodsGifts;

    @ApiModelProperty("商品品牌编码")
    private String productBrandCode;

    @ApiModelProperty("商品品牌名称")
    private String productBrandName;

    @ApiModelProperty("商品属性code")
    private String productPropertyCode;

    @ApiModelProperty("商品属性名称")
    private String productPropertyName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;
    @ApiModelProperty("物流中心code")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("库房code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("状态u：0为启用，1为禁用")
    private Byte useStatus;

    @ApiModelProperty("仓库类型'库房类型(1.销售库 2.特卖库 3.残品库 4.监管库）")
    private Integer warehouseType;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人名称")
    private String createByName;

    @ApiModelProperty("创建人id")
    private String createById;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人id")
    private String updateById;

    @ApiModelProperty("修改人名称")
    private String updateByName;


}

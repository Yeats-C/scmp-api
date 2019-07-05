package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-28
 * @time: 16:18
 */
@ApiModel("变价选sku信息返回vo")
@Data
public class QuerySkuInfoRespVO {
    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku编码")
    private String skuName;

    @ApiModelProperty("品类")
    private String productCategoryName;

    @ApiModelProperty("品牌")
    private String productBrandName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("颜色")
    private String colorName;

    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty("进项单位")
    private String unitName;

    @ApiModelProperty("类别")
    private String productSortName;

    @ApiModelProperty("是否赠品0商品1赠品")
    private Byte goodsGifts;

    @ApiModelProperty("商品属性")
    private String productPropertyName;

    @ApiModelProperty("spu编码")
    private String spuName;

    @ApiModelProperty("spu编码")
    private String spuCode;

    @ApiModelProperty("仓库批次号编码")
    private String warehouseBatchNumber;

    @ApiModelProperty("仓库批次号名称")
    private String warehouseBatchName;

    @ApiModelProperty("仓库编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date productTime;

    @ApiModelProperty("批次信息")
    private List<BatchInfo> batchList;

    @ApiModelProperty("价格项目集合")
    private List<PriceChannelForChangePrice> priceChannelList;

    @ApiModelProperty("供应商集合")
    private List<supplierInfoVO> supplierInfoVOS;

    public String getWarehouseBatchName() {
        return Optional.ofNullable(this.transportCenterName).orElse("")+
                Optional.ofNullable(this.warehouseName).orElse("")+
                Optional.ofNullable(this.warehouseBatchNumber).orElse("")+(
                Objects.isNull(productTime)?"":this.productTime.toString());
    }
}

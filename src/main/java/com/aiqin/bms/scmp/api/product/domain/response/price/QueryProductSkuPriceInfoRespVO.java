package com.aiqin.bms.scmp.api.product.domain.response.price;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceAreaInfo;
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
 * @date: 2019-05-30
 * @time: 10:39
 */
@ApiModel("价格列表返回vo")
@Data
public class QueryProductSkuPriceInfoRespVO {

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("价格项目")
    private String priceItemName;

    @ApiModelProperty("价格类型")
    private String priceTypeName;

    @ApiModelProperty("含税金额")
    private Long priceTax;

    @ApiModelProperty("仓库批次号编码")
    private String warehouseBatchNumber;

    @ApiModelProperty("仓库批次号名称")
    private String warehouseBatchName;

    @ApiModelProperty("仓库名称")
    private String transportCenterName;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("开始生效时间")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    private Date effectiveTimeEnd;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("是否默认(0否1是)")
    private Integer beDefault;

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productTime;

    @ApiModelProperty("品类")
    private String productCategoryName;

    @ApiModelProperty("品牌")
    private String productBrandName;

    @ApiModelProperty("商品属性")
    private String productPropertyName;

    @ApiModelProperty("区域信息")
    List<ProductSkuPriceAreaInfo> areaInfos;

    public void setWarehouseBatchName() {
        if(Objects.isNull(this.transportCenterName)){
            return;
        }
        this.warehouseBatchName = Optional.ofNullable(this.transportCenterName).orElse("")+"-"
                +Optional.ofNullable(this.transportCenterName).orElse("")+"-"
                +Optional.ofNullable(this.warehouseBatchName).orElse("")+"-"
                +Optional.ofNullable(this.warehouseBatchNumber).orElse("")+"-"
                + (Objects.nonNull(this.productTime)?this.productTime.toString():"");
    }
}

package com.aiqin.bms.scmp.api.product.domain.response.price;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceAreaInfo;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuPriceInfoLog;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-30
 * @time: 14:47
 */
@ApiModel("详情返回vo")
@Data
public class ProductSkuPriceInfoRespVO {

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("价格项目名称")
    private String priceItemName;

    @ApiModelProperty("品类")
    private String productCategoryName;

    @ApiModelProperty("品牌")
    private String productBrandName;

    @ApiModelProperty("商品属性")
    private String productPropertyName;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格数据名称")
    private String priceAttributeName;

    @ApiModelProperty("含税金额")
    private BigDecimal priceTax;

    @ApiModelProperty("未税金额")
    private BigDecimal priceNoTax;

    @ApiModelProperty("税率")
    private BigDecimal tax;

    @ApiModelProperty("开始生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date effectiveTimeStart;

    @ApiModelProperty("结束生效时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date effectiveTimeEnd;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("是否默认(0否1是)")
    private Integer beDefault;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

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

    @ApiModelProperty("生产日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date productTime;

    @ApiModelProperty("商品状态")
    private Integer productStatus;

    @ApiModelProperty("区域信息")
    List<ProductSkuPriceAreaInfo> areaInfos;

    @ApiModelProperty("日志信息")
    List<ProductSkuPriceInfoLog> logs;

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

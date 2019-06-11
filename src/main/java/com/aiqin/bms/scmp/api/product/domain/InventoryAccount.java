package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wuyongqiang
 * @description 库存出入库记录对象
 * @date 2018/11/21 19:40
 */
@Data
@ApiModel("库存出入库记录")
public class InventoryAccount {
    @ApiModelProperty(value = "出/入库单号")
    @JsonProperty("master_number")
    private String masterNumber;

    @ApiModelProperty(value = "关联单号")
    @JsonProperty("relate_number")
    private String relateNumber;

    @ApiModelProperty(value = "单据类型",example = "1初始化入库,2门店销售退货,3网店销售退货,4门店销售,5网店销售")
    @JsonProperty("bill_type")
    private Integer billType;

    @ApiModelProperty(value = "库存类别",example = "1代表门店自有仓，2代表配送中心大仓")
    @JsonProperty("storage_type")
    private Integer storageType;

    @ApiModelProperty(value = "仓位",example = "1代表陈列仓位，2代表退货仓位,3代表存储仓位")
    @JsonProperty("storage_position")
    private Integer storagePosition;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "商品条码")
    @JsonProperty("product_barcode")
    private String productBarcode;

    @ApiModelProperty(value = "商品单价")
    @JsonProperty("product_price")
    private Long productPrice;

    @ApiModelProperty(value = "出/入库数量")
    @JsonProperty("update_volume")
    private Integer updateVolume;

    @ApiModelProperty(value = "可售库存")
    @JsonProperty("sellable_storage")
    private Integer sellableStorage;

    @ApiModelProperty(value = "制作人")
    @JsonProperty("creator")
    private String creator;

    @JsonProperty("create_by_name")
    @ApiModelProperty("创建人名称")
    public String createByName;

    @ApiModelProperty(value = "制单时间")
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public String getMasterNumber() {
        return masterNumber;
    }

    public void setMasterNumber(String masterNumber) {
        this.masterNumber = masterNumber;
    }

    public String getRelateNumber() {
        return relateNumber;
    }

    public void setRelateNumber(String relateNumber) {
        this.relateNumber = relateNumber;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public Integer getStorageType() {
        return storageType;
    }

    public void setStorageType(Integer storageType) {
        this.storageType = storageType;
    }

    public Integer getStoragePosition() {
        return storagePosition;
    }

    public void setStoragePosition(Integer storagePosition) {
        this.storagePosition = storagePosition;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Long productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getUpdateVolume() {
        return updateVolume;
    }

    public void setUpdateVolume(Integer updateVolume) {
        this.updateVolume = updateVolume;
    }

    public Integer getSellableStorage() {
        return sellableStorage;
    }

    public void setSellableStorage(Integer sellableStorage) {
        this.sellableStorage = sellableStorage;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

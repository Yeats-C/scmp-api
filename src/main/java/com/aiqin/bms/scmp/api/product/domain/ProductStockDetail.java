package com.aiqin.bms.scmp.api.product.domain;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class ProductStockDetail extends CommonBean {
    @ApiModelProperty(value = "id", hidden = true)
    private Long id;

    @ApiModelProperty("skuCode")
    @JsonProperty(value = "sku_code")
    private String skuCode;

    @ApiModelProperty("门店主键")
    @JsonProperty(value = "distributor_id")
    private String distributorId;

    @ApiModelProperty("门店编号")
    @JsonProperty(value = "distributor_code")
    private String distributorCode;

    @ApiModelProperty("变更类型，0为入库，1为出库")
    @JsonProperty(value = "stock_change_type")
    private Integer stockChangeType;

    @ApiModelProperty("变更数量")
    @JsonProperty(value = "change_count")
    private Integer changeCount;

    @ApiModelProperty("当时可售库存")
    @JsonProperty(value = "sellable_storage")
    private Integer sellableStorage;

    @ApiModelProperty("入库、出库业务类型/单据类型：1初始化入库、2门店销售退货、3网店销售退货，4门店销售，5网店销售")
    @JsonProperty(value = "stock_change_reason")
    private Integer stockChangeReason;

    @ApiModelProperty("是否释放，0为未释放，1为释放")
    @JsonProperty(value = "release_status")
    private Integer releaseStatus;

    @ApiModelProperty("入库单号")
    @JsonProperty(value = "warehousing_id")
    private String warehousingId;

    @ApiModelProperty("关联单号")
    @JsonProperty(value = "relation_id")
    private String relationId;

    @ApiModelProperty("仓库类型，1代表门店自有仓，2代表配送中心大仓")
    @JsonProperty(value = "warehouse_type")
    private Integer warehouseType;

    @ApiModelProperty("仓位，1代表陈列仓位，2代表退货仓位")
    @JsonProperty(value = "position")
    private Integer position;

    @ApiModelProperty("create_time")
    @JsonProperty(value = "create_time")
    private Date createTime;

    @ApiModelProperty("update_time")
    @JsonProperty(value = "update_time")
    private Date updateTime;

    @ApiModelProperty("create_by")
    @JsonProperty(value = "create_by")
    private String createBy;

    @ApiModelProperty("update_by")
    @JsonProperty(value = "update_by")
    private String updateBy;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    @JsonProperty(value = "del_flag")
    private Byte delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public Integer getStockChangeType() {
        return stockChangeType;
    }

    public void setStockChangeType(Integer stockChangeType) {
        this.stockChangeType = stockChangeType;
    }

    public Integer getChangeCount() {
        return changeCount;
    }

    public void setChangeCount(Integer changeCount) {
        this.changeCount = changeCount;
    }

    public Integer getSellableStorage() {
        return sellableStorage;
    }

    public void setSellableStorage(Integer sellableStorage) {
        this.sellableStorage = sellableStorage;
    }

    public Integer getStockChangeReason() {
        return stockChangeReason;
    }

    public void setStockChangeReason(Integer stockChangeReason) {
        this.stockChangeReason = stockChangeReason;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getWarehousingId() {
        return warehousingId;
    }

    public void setWarehousingId(String warehousingId) {
        this.warehousingId = warehousingId;
    }

    public String getRelationId() {
        return relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Integer getWarehouseType() {
        return warehouseType;
    }

    public void setWarehouseType(Integer warehouseType) {
        this.warehouseType = warehouseType;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }
}
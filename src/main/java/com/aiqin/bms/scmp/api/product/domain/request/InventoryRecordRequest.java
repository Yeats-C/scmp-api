package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wuyongqiang
 * @description 更新库存记录请求对象
 * @date 2018/11/29 11:27
 */
@ApiModel("更新库存记录请求参数")
@Data
@AllArgsConstructor
public class InventoryRecordRequest {
    @ApiModelProperty(value = "门店ID")
    @JsonProperty("store_id")
    private String storeId;

    @ApiModelProperty(value = "门店编号")
    @JsonProperty("store_code")
    private String storeCode;

    @ApiModelProperty(value = "商品SKU码")
    @JsonProperty("product_sku")
    private String productSku;

    @ApiModelProperty(value = "记录类型")
    @JsonProperty("record_type")
    private Integer recordType;

    @ApiModelProperty(value = "记录数量")
    @JsonProperty("record_number")
    private Integer recordNumber;

    @ApiModelProperty(value = "单据类型")
    @JsonProperty("bill_type")
    private Integer billType;

    @ApiModelProperty(value = "出/入库单号")
    @JsonProperty("master_number")
    private String masterNumber;

    @ApiModelProperty(value = "关联单号")
    @JsonProperty("relate_number")
    private String relateNumber;

    @ApiModelProperty(value = "库存类别")
    @JsonProperty("storage_type")
    private Integer storageType;

    @ApiModelProperty(value = "可售库存")
    @JsonProperty("sellable_storage")
    private Integer sellableStorage;

    @ApiModelProperty(value = "仓位")
    @JsonProperty("storage_position")
    private Integer storagePosition;

    @ApiModelProperty(value = "释放状态")
    @JsonProperty("release_status")
    private Integer releaseStatus;

    @ApiModelProperty(value = "操作人")
    @JsonProperty("operator")
    private String operator;

    @JsonProperty("create_by_name")
    @ApiModelProperty("创建人名称")
    public String createByName;


    public InventoryRecordRequest() {

    }

    public InventoryRecordRequest(String storeId, String productSku, Integer storageType) {
        this.storeId = storeId;
        this.productSku = productSku;
        this.storageType = storageType;
    }
}

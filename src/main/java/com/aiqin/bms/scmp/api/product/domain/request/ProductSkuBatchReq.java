package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 
 * ProductSkuBatch
 * 数据库表：product_sku_batch
 */
@Data
public class ProductSkuBatchReq {

    /**
     * 
     * 表字段 : product_sku_batch.id
     */
    @ApiModelProperty("主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * sku编码
     * 表字段 : product_sku_batch.sku_code
     */
    @ApiModelProperty("sku编码")
    private String skuCode;

    /**
     * SKU名称
     * 表字段 : product_sku_batch.sku_name
     */
    @ApiModelProperty("sku名称")
    private String skuName;

    /**
     * 删除标记(0:正常 1:禁用)
     * 表字段 : product_sku_batch.use_status
     */
    @ApiModelProperty("删除标记(0:正常 1:禁用)")
    private Integer useStatus=0;

    /**
     * 仓编码(物流中心编码)
     * 表字段 : product_sku_batch.transport_center_code
     */
    @ApiModelProperty("仓编码(物流中心编码)")
    private String transportCenterCode;

    /**
     * 仓名称(物流中心名称)
     * 表字段 : product_sku_batch.transport_center_name
     */
    @ApiModelProperty("仓名称(物流中心名称)")
    private String transportCenterName;

    /**
     * 库房编码
     * 表字段 : product_sku_batch.warehouse_code
     */
    @ApiModelProperty("库房编码")
    private String warehouseCode;

    /**
     * 库房名称
     * 表字段 : product_sku_batch.warehouse_name
     */
    @ApiModelProperty("库房名称")
    private String warehouseName;

    /**
     * 库房类型(1.销售库 2.特卖库 3.残品库 4.监管库)
     * 表字段 : product_sku_batch.warehouse_type
     */
    @ApiModelProperty("库房类型(1.销售库 2.特卖库 3.残品库 4.监管库)")
    private Boolean warehouseType;

    /**
     * 创建人id
     * 表字段 : product_sku_batch.create_by_id
     */
    @ApiModelProperty("创建人id")
    private String createById;

    /**
     * 创建人名称
     * 表字段 : product_sku_batch.create_by_name
     */
    @ApiModelProperty("创建人名称")
    private String createByName;

    /**
     * 创建时间
     * 表字段 : product_sku_batch.create_time
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新人名称
     * 表字段 : product_sku_batch.update_by_name
     */
    @ApiModelProperty("更新人名称")
    private String updateByName;

    /**
     * 更新时间
     * 表字段 : product_sku_batch.update_time
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 更新人id
     * 表字段 : product_sku_batch.update_by_id
     */
    @ApiModelProperty("更新人id")
    private String updateById;


}
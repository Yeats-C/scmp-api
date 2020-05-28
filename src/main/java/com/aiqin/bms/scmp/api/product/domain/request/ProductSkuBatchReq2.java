package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.Warehouse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 
 * ProductSkuBatch
 * 数据库表：product_sku_batch
 */
@Data
public class ProductSkuBatchReq2 {



    /**
     * 库房名称
     * 表字段 : product_sku_batch.warehouse_name
     */
    @ApiModelProperty("库房集合")
    private List<Warehouse> warehouses;



    /**
     * 库房名称
     * 表字段 : product_sku_batch.warehouse_name
     */
    @ApiModelProperty("商品集合")
    private List<ProductSkuBatchReq> productSkuBatchReqList;
}
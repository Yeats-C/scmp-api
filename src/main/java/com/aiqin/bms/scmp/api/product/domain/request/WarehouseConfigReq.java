package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Auther: mamingze
 * @Date: 2020-04-09 17:37
 * @Description:
 */
@Data
public class WarehouseConfigReq  extends PageReq {

    /**
     *
     * 表字段 : 主键id
     */
    @ApiModelProperty("主键id")
    @JsonProperty("id")
    private Long id;

    /**
     * 库房名称\
     * 表字段 : warhouse_config.warehouse_name
     */
    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    /**
     * 库房编码
     * 表字段 : warhouse_config.warehouse_code
     */
    @ApiModelProperty("库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    /**
     * 库房类型
     * 表字段 : warhouse_config.warehouse_type
     */
    @ApiModelProperty("库房类型（1.销售库 2.特卖库 3.残品库 4.监管库）")
    @JsonProperty("warehouse_type")
    private String warehouseType;

    /**
     *
     * 表字段 : warhouse_config.stock_code
     */

    /**
     * 库房类型
     * 表字段 : warhouse_config.warehouse_type
     */
    @ApiModelProperty("库房编码")
    @JsonProperty("stock_code")
    private String stockCode;

    /**
     *
     * 表字段 : warhouse_config.stock_name
     */
    @ApiModelProperty("库房名称")
    @JsonProperty("stock_name")
    private String stockName;

    /**
     * 对接类型
     * 表字段 : warhouse_config.docking_type
     */
    @ApiModelProperty("对接类型(0:上游订单 1：下游wms)")
    @JsonProperty("docking_type")
    private Integer dockingType;

    /**
     * 对接系统
     * 表字段 : warhouse_config.docking_system
     */
    @ApiModelProperty("对接系统(0：DL系统 1：巨沃系统 2:富勒系统 3：德邦系统)")
    @JsonProperty("docking_system")
    private Integer dockingSystem;

    /**
     * 对接库房名称
     * 表字段 : warhouse_config.counterpart_warehouse_name
     */
    @ApiModelProperty("对接库房名称")
    @JsonProperty("counterpart_warehouse_name")
    private String counterpartWarehouseName;

    /**
     * 对接库房编码
     * 表字段 : warhouse_config.counterpart_warehouse_code
     */
    @ApiModelProperty("对接库房编码")
    @JsonProperty("counterpart_warehouse_code")
    private String counterpartWarehouseCode;

    /**
     * 对接仓库编码
     * 表字段 : warhouse_config.counterpart_stock_code
     */
    @ApiModelProperty("对接仓库编码")
    @JsonProperty("counterpart_stock_code")
    private String counterpartStockCode;

    /**
     * 对接仓库名称
     * 表字段 : warhouse_config.counterpart_stock_name
     */
    @ApiModelProperty("对接仓库名称")
    @JsonProperty("counterpart_stock_name")
    private String counterpartStockName;

    @ApiModelProperty("移库类型（0.wms发起移库 1.分别发起移库 2.同时发起移库）")
    @JsonProperty("movement_type")
    private Integer movementType;

    /**
     * 启用禁用状态
     * 表字段 : warhouse_config.enable
     */
    @ApiModelProperty("禁用启用状态，0是禁用，1是未禁用")
    @JsonProperty("enable")
    private Integer enable;

    /**
     * 创建人
     * 表字段 : warhouse_config.create_by
     */
    @ApiModelProperty("创建人")
    @JsonProperty("create_by")
    private String createBy;

    /**
     * 创建时间
     * 表字段 : warhouse_config.create_time
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     * 表字段 : warhouse_config.update_by
     */
    @ApiModelProperty("创建人")
    @JsonProperty("update_by")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}

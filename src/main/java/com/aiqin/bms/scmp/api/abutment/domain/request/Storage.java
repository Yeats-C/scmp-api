package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author sunx
 * @description scmp出入主信息
 * @date 2019-08-02
 */
@Data
@ApiModel("出入主信息")
public class Storage {
    /**
     * 出入库单id
     */
    @JsonProperty("order_id")
    @ApiModelProperty("出入库单id")
    private String orderId;
    /**
     * 出入库单号
     */
    @JsonProperty("order_code")
    @ApiModelProperty("出入库单号")
    private String orderCode;
    /**
     * 出入库标识
     */
    @JsonProperty("in_out_flag")
    @ApiModelProperty("出入库标识")
    private Integer inOutFlag;
    /**
     * 来源单号id
     */
    @JsonProperty("source_order_id")
    @ApiModelProperty("来源单号id")
    private String sourceOrderId;
    /**
     * 来源单编码
     */
    @JsonProperty("source_order_code")
    @ApiModelProperty("来源单编码")
    private String sourceOrderCode;
    /**
     * sap物料单编码
     */
    @JsonProperty("sap_order_id")
    @ApiModelProperty(value = "sap物料单编码", hidden = true)
    private String sapOrderId;
    /**
     * 来源业务单类型0 采购单 5 退供单 10 销售订单 15 配送退货
     */
    @JsonProperty("source_order_type")
    @ApiModelProperty("来源业务单类型0 采购 5 退供 10 配送订单 15 直送订单 20 辅采订单  25 售后退货 30 出入库")
    private String sourceOrderType;
    /**
     * 类型描述
     */
    @JsonProperty("source_order_type_name")
    @ApiModelProperty("类型描述")
    private String sourceOrderTypeName;

    @JsonProperty("sub_order_type")
    @ApiModelProperty("出入库单据类型 0 采购入库 5 退供出库 10 配送订单出库 15 直送订单出库 " +
            "20 辅采订单出库  25 售后退货入库 30 移库 35 调拨入库 40 调拨出库 45 报损 50 报溢")
    private String subOrderType;
    /**
     * 类型描述
     */
    @JsonProperty("sub_order_type_name")
    @ApiModelProperty("出入库单据类型描述，采购入库 退供出库 等")
    private String subOrderTypeName;

    /**
     * 出库仓库编码
     */
    @JsonProperty("transport_code")
    @ApiModelProperty("出库仓库编码(只是出入库单据时,都传出库中)")
    private String transportCode;
    /**
     * 出库仓库名称
     */
    @JsonProperty("transport_name")
    @ApiModelProperty("出库仓库名称(只是出入库单据时,都传出库中")
    private String transportName;
    /**
     * sap出库仓库编码
     */
    @JsonProperty("sap_transport_code")
    @ApiModelProperty(value = "sap出库仓库编码", hidden = true)
    private String sapTransportCode;
    /**
     * 出库库房编码
     */
    @JsonProperty("storage_code")
    @ApiModelProperty("出库库房编码(只是出入库单据时,都传出库中")
    private String storageCode;
    /**
     * 出库库房名称
     */
    @JsonProperty("storage_name")
    @ApiModelProperty("出库库房名称(只是出入库单据时,都传出库中")
    private String storageName;
    /**
     * sap出库库房编码
     */
    @JsonProperty("sap_storage_code")
    @ApiModelProperty(value = "sap出库库房编码", hidden = true)
    private String sapStorageCode;
    /**
     * 入仓库编码
     */
    @JsonProperty("transport_code1")
    @ApiModelProperty("入仓库编码")
    private String transportCode1;
    /**
     * 入仓库名称
     */
    @JsonProperty("transport_name1")
    @ApiModelProperty("入仓库名称")
    private String transportName1;
    /**
     * sap入仓库编码
     */
    @JsonProperty("sap_transport_code1")
    @ApiModelProperty(value = "sap入仓库编码", hidden = true)
    private String sapTransportCode1;
    /**
     * 入库房编码
     */
    @JsonProperty("storage_code1")
    @ApiModelProperty("入库房编码")
    private String storageCode1;
    /**
     * 入库房名称
     */
    @JsonProperty("storage_name1")
    @ApiModelProperty("入库房名称")
    private String storageName1;
    /**
     * sap入库房编码
     */
    @JsonProperty("sap_storage_code1")
    @ApiModelProperty(value = "sap入库房编码", hidden = true)
    private String sapStorageCode1;

    /**
     * sap供应商物料编码
     */
    @JsonProperty("sap_supplier_code")
    @ApiModelProperty(value = "sap供应商物料编码")
    private String sapSupplierCode;
    /**
     * 总数量
     */
    @JsonProperty("order_count")
    @ApiModelProperty("总数量")
    private Integer orderCount;
    /**
     * 总金额
     */
    @ApiModelProperty("总金额")
    private String amount;
    /**
     * 折扣金额
     */
    @JsonProperty("discount_price")
    @ApiModelProperty("折扣金额")
    private String discountPrice;

    /**
     * 实际出入库时间
     */
    @JsonProperty("opt_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("实际出入库时间")
    private Date optTime;

    /**
     * 同步时间
     */
    @JsonProperty("sync_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("同步时间")
    private Date createTime;

    /**
     * 最终操作人id
     */
    @JsonProperty("user_id")
    @ApiModelProperty("最终操作人id")
    private String createById;

    /**
     * 最终操作人
     */
    @JsonProperty("user_name")
    @ApiModelProperty("最终操作人")
    private String createByName;

    /**
     * 出入库单据明细信息
     */
    @JsonProperty("details")
    @ApiModelProperty("出入库单据明细信息")
    private List<StorageDetail> details;
}

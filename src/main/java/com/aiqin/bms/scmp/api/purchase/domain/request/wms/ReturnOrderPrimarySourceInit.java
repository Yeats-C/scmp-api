package com.aiqin.bms.scmp.api.purchase.domain.request.wms;

import com.aiqin.bms.scmp.api.product.domain.response.wms.BatchInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author:zfy
 * @Date:2020/3/6
 * @Content:
 */
@Data
@ApiModel("退货入库单推送主表")
public class ReturnOrderPrimarySourceInit {

    @ApiModelProperty(value="入库单号")
    @JsonProperty("inbound_order_code")
    private String inboundOrderCode;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;


    @ApiModelProperty(value="退货单创建时间")
    @JsonProperty("create_time")
    private String createTime;

    @ApiModelProperty(value="退货单创建人编码")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="退货单创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="物流公司编号")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty(value="物流公司名称")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty(value="物流公司单号")
    @JsonProperty("transport_number")
    private String transportNumber;

    @ApiModelProperty(value="客户编码")
    @JsonProperty("customer_code")
    private String customerCode;

    @ApiModelProperty(value="客户名称")
    @JsonProperty("customer_name")
    private String customerName;

    @ApiModelProperty(value="发货人")
    @JsonProperty("shipper")
    private String shipper;

    @ApiModelProperty(value="发货人手机号")
    @JsonProperty("shipper_number")
    private String shipperNumber;

    @ApiModelProperty(value="备注")
    @JsonProperty("remake")
    private String remake;

    @ApiModelProperty(value="退货单号")
    @JsonProperty("return_order_code")
    @NotEmpty(message = "退货单号不能为空")
    private String returnOrderCode;

    @ApiModelProperty(value="销售单号")
    @JsonProperty("order_code")
    private String orderCode;

    /*******德邦新增*******/
    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="国家")
    @JsonProperty("country")
    private String country;

    @ApiModelProperty(value="发货省")
    @JsonProperty("province")
    private String province;

    @ApiModelProperty(value="发货市   ")
    @JsonProperty("city")
    private String city;

    @ApiModelProperty(value="发货区/县")
    @JsonProperty("county")
    private String county;

    @ApiModelProperty(value="详细地址")
    @JsonProperty("street")
    private String street;
    /*******德邦新增*******/

    @ApiModelProperty(value="商品详情")
    @JsonProperty("details")
    private List<ReturnOrderChildSourceInit> details;

    @ApiModelProperty(value="商品批次")
    @JsonProperty("batchInfo_list")
    private List<BatchInfo> batchInfoList;

}

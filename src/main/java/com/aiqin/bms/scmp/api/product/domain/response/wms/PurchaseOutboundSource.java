package com.aiqin.bms.scmp.api.product.domain.response.wms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "退供出库推送源数据")
@Data
public class PurchaseOutboundSource {

    @NotBlank(message = "退供单号不能为空")
    @ApiModelProperty(value="退供单号")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @NotBlank(message = "库房编码不能为空")
    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @NotBlank(message = "供应商编码不能为空")
    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="退供单创建人编号")
    @JsonProperty("operuser_code")
    private String operuserCode;

    @ApiModelProperty(value="退供单创建人姓名")
    @JsonProperty("operuser_name")
    private String operuserName;

    @NotNull(message = "退供单创建日期不能为空")
    @ApiModelProperty(value="退供单创建日期")
    @JsonProperty("create_date")
    private Date createDate;

    @ApiModelProperty(value="收货人")
    @JsonProperty("contacts_person")
    private String contactsPerson;

    @ApiModelProperty(value="收货人电话")
    @JsonProperty("contacts_person_phone")
    private String contactsPersonPhone;

    @ApiModelProperty(value="收货区域-省编号")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value="收货区域-省名称")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value="收货区域-市编号")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value="收货区域-市名称")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value="收货区域-县编号")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value="收货区域-县名称")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value="收货详细地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value="出库单号")
    @JsonProperty("outbound_oder_code")
    private String outboundOderCode;

    @Valid
    @ApiModelProperty(value = "退供入库推送源数据明细")
    @JsonProperty("detail_list")
    private List<PurchaseOutboundDetailSource> detailList;

    @ApiModelProperty(value="批次信息明细")
    @JsonProperty("batch_info")
    private List<BatchInfo> batchInfo;
}

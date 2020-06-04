package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "调拨入库推送源数据")
@Data
public class AllocationInboundSource implements Serializable {

    @ApiModelProperty(value="调拨计划号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value="来源单号")
    @JsonProperty("source_order_no")
    private String sourceOrderNo;

    @ApiModelProperty(value="来源类型 默认为:01  01：普通调拨(出、入仓库必需传)02:调拨入库03:调拨出库04:唯品调拨")
    @JsonProperty("source_type")
    private String sourceType;

    @ApiModelProperty(value="是否唯品(1是0否,默认0)")
    @JsonProperty("is_vip54")
    private String isVip54;

    @ApiModelProperty(value="第三方入库单号(如唯品的出仓单号)")
    @JsonProperty("out_order_no")
    private String outOrderNo;

    @ApiModelProperty(value="调出仓库编码")
    @JsonProperty("from_whcode")
    private String fromWhcode;

    @ApiModelProperty(value="调入仓库编码")
    @JsonProperty("target_whcode")
    private String targetWhcode;

    @ApiModelProperty(value="创建时间(yyyy-MM-dd HH:mm:ss)", example = "2001-01-01 01:01:01")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("create_date")
    private Date createDate;

    @ApiModelProperty(value="创建人编码")
    @JsonProperty("create_user_code")
    private String createUserCode;

    @ApiModelProperty(value="创建人名称")
    @JsonProperty("create_user_name")
    private String createUserName;

    @ApiModelProperty(value="物流商编码")
    @JsonProperty("logistics_company_code")
    private String logisticsCompanyCode;

    @ApiModelProperty(value="物流单号")
    @JsonProperty("express_no")
    private String expressNo;

    @ApiModelProperty(value="货主")
    @JsonProperty("goods_owner")
    private String goodsOwner;

    @ApiModelProperty(value="调出货主")
    @JsonProperty("from_goods_owner")
    private String fromGoodsOwner;

    @ApiModelProperty(value="调入货主")
    @JsonProperty("target_goods_owner")
    private String targetGoodsOwner;

    @ApiModelProperty(value="备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "备用字段1")
    @JsonProperty("gwf1")
    private String gwf1;

    @ApiModelProperty(value = "备用字段2")
    @JsonProperty("gwf2")
    private String gwf2;

    @ApiModelProperty(value = "备用字段3")
    @JsonProperty("gwf3")
    private String gwf3;

    @ApiModelProperty(value = "备用字段4")
    @JsonProperty("gwf4")
    private String gwf4;

    @ApiModelProperty(value = "备用字段5")
    @JsonProperty("gwf5")
    private String gwf5;

    @ApiModelProperty(value="调拨入库推送源数据明细")
    @JsonProperty("allocation_inbound_detailed_source")
    private List<AllocationInboundDetailedSource> allocationInboundDetailedSource;
}

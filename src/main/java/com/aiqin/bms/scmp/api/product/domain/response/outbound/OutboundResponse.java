package com.aiqin.bms.scmp.api.product.domain.response.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by 爱亲 on 2019/1/15.
 */
@ApiModel("出库信息返回实体")
public class OutboundResponse {

    @ApiModelProperty("出库状态编码")
    @JsonProperty("outbound_status_code")
    private Byte outboundStatusCode;

    @ApiModelProperty("出库状态名称")
    @JsonProperty("outbound_status_name")
    private String outboundStatusName;

    @ApiModelProperty("出库类型编码")
    @JsonProperty("outbound_type_code")
    private Byte outboundTypeCode;

    @ApiModelProperty("出库类型名称")
    @JsonProperty("outbound_type_name")
    private String outboundTypeName;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("实际出库数量")
    @JsonProperty("pra_outbound_num")
    private Long praOutboundNum;

    @ApiModelProperty("实际出库主数量")
    @JsonProperty("pra_outbound_main_num")
    private Long praOutboundMainNum;

    @ApiModelProperty("出库单号")
    @JsonProperty("outbound_oder_code")
    private String outboundOderCode;

    @ApiModelProperty("创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty("更新人")
    @JsonProperty("update_by")
    private String updateBy;

    @ApiModelProperty("更新时间")
    @JsonProperty("update_time")
    private Date updateTime;

    public Byte getOutboundStatusCode() {
        return outboundStatusCode;
    }

    public void setOutboundStatusCode(Byte outboundStatusCode) {
        this.outboundStatusCode = outboundStatusCode;
    }

    public String getOutboundStatusName() {
        return outboundStatusName;
    }

    public void setOutboundStatusName(String outboundStatusName) {
        this.outboundStatusName = outboundStatusName;
    }

    public Byte getOutboundTypeCode() {
        return outboundTypeCode;
    }

    public void setOutboundTypeCode(Byte outboundTypeCode) {
        this.outboundTypeCode = outboundTypeCode;
    }

    public String getOutboundTypeName() {
        return outboundTypeName;
    }

    public void setOutboundTypeName(String outboundTypeName) {
        this.outboundTypeName = outboundTypeName;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getPraOutboundNum() {
        return praOutboundNum;
    }

    public void setPraOutboundNum(Long praOutboundNum) {
        this.praOutboundNum = praOutboundNum;
    }

    public Long getPraOutboundMainNum() {
        return praOutboundMainNum;
    }

    public void setPraOutboundMainNum(Long praOutboundMainNum) {
        this.praOutboundMainNum = praOutboundMainNum;
    }

    public String getOutboundOderCode() {
        return outboundOderCode;
    }

    public void setOutboundOderCode(String outboundOderCode) {
        this.outboundOderCode = outboundOderCode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
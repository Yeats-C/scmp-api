package com.aiqin.bms.scmp.api.supplier.domain.response.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by 爱亲 on 2019/1/15.
 */
@ApiModel("入库信息返回实体")
public class InboundResponse {

    @ApiModelProperty("入库状态编码")
    @JsonProperty("inbound_status_code")
    private Integer inboundStatusCode;

    @ApiModelProperty("入库状态名称")
    @JsonProperty("inbound_status_name")
    private String inboundStatusName;

    @ApiModelProperty("入库类型编码")
    @JsonProperty("inbound_type_code")
    private Integer inboundTypeCode;

    @ApiModelProperty("入库类型名称")
    @JsonProperty("inbound_type_name")
    private String inboundTypeName;

    @ApiModelProperty("sku编码")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("实际入库数量")
    @JsonProperty("pra_inbound_num")
    private Long praInboundNum;

    @ApiModelProperty("实际入库主数量")
    @JsonProperty("pra_inbound_main_num")
    private Long praInboundMainNum;

    @ApiModelProperty("入库单号")
    @JsonProperty("inbound_oder_code")
    private String inboundOderCode;

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

    public Integer getInboundStatusCode() {
        return inboundStatusCode;
    }

    public void setInboundStatusCode(Integer inboundStatusCode) {
        this.inboundStatusCode = inboundStatusCode;
    }

    public String getInboundStatusName() {
        return inboundStatusName;
    }

    public void setInboundStatusName(String inboundStatusName) {
        this.inboundStatusName = inboundStatusName;
    }

    public Integer getInboundTypeCode() {
        return inboundTypeCode;
    }

    public void setInboundTypeCode(Integer inboundTypeCode) {
        this.inboundTypeCode = inboundTypeCode;
    }

    public String getInboundTypeName() {
        return inboundTypeName;
    }

    public void setInboundTypeName(String inboundTypeName) {
        this.inboundTypeName = inboundTypeName;
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

    public Long getPraInboundNum() {
        return praInboundNum;
    }

    public void setPraInboundNum(Long praInboundNum) {
        this.praInboundNum = praInboundNum;
    }

    public Long getPraInboundMainNum() {
        return praInboundMainNum;
    }

    public void setPraInboundMainNum(Long praInboundMainNum) {
        this.praInboundMainNum = praInboundMainNum;
    }

    public String getInboundOderCode() {
        return inboundOderCode;
    }

    public void setInboundOderCode(String inboundOderCode) {
        this.inboundOderCode = inboundOderCode;
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
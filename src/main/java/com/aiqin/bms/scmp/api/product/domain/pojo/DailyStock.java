package com.aiqin.bms.scmp.api.product.domain.pojo;


import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("")
public class DailyStock extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("sku号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("昨日库存数")
    private Long yesterdayStockNum;

    @ApiModelProperty("昨日库存单价")
    private Long yesterdayStockPrice;

    @ApiModelProperty("昨日入库量")
    private Long yesterdayInboundNum;

    @ApiModelProperty("昨日出库量")
    private Long yesterdayOutboundNum;

    @ApiModelProperty("删除标记，0未删除 1已删除")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public Long getYesterdayStockNum() {
        return yesterdayStockNum;
    }

    public void setYesterdayStockNum(Long yesterdayStockNum) {
        this.yesterdayStockNum = yesterdayStockNum;
    }

    public Long getYesterdayStockPrice() {
        return yesterdayStockPrice;
    }

    public void setYesterdayStockPrice(Long yesterdayStockPrice) {
        this.yesterdayStockPrice = yesterdayStockPrice;
    }

    public Long getYesterdayInboundNum() {
        return yesterdayInboundNum;
    }

    public void setYesterdayInboundNum(Long yesterdayInboundNum) {
        this.yesterdayInboundNum = yesterdayInboundNum;
    }

    public Long getYesterdayOutboundNum() {
        return yesterdayOutboundNum;
    }

    public void setYesterdayOutboundNum(Long yesterdayOutboundNum) {
        this.yesterdayOutboundNum = yesterdayOutboundNum;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
}
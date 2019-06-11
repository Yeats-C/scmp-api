package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("整箱商品包装")
public class ProductSkuBoxPacking extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("整箱商品包装 单位")
    private String largeUnit;

    @ApiModelProperty("包装箱子长度")
    private Long boxLength;

    @ApiModelProperty("宽度（mm）")
    private Long boxWidth;

    @ApiModelProperty("箱子高度")
    private Long boxHeight;

    @ApiModelProperty("箱子体积")
    private Long boxVolume;

    @ApiModelProperty("毛重")
    private Long boxGrossWeight;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("申请编码")
    private String applyCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLargeUnit() {
        return largeUnit;
    }

    public void setLargeUnit(String largeUnit) {
        this.largeUnit = largeUnit == null ? null : largeUnit.trim();
    }

    public Long getBoxLength() {
        return boxLength;
    }

    public void setBoxLength(Long boxLength) {
        this.boxLength = boxLength;
    }

    public Long getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(Long boxWidth) {
        this.boxWidth = boxWidth;
    }

    public Long getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(Long boxHeight) {
        this.boxHeight = boxHeight;
    }

    public Long getBoxVolume() {
        return boxVolume;
    }

    public void setBoxVolume(Long boxVolume) {
        this.boxVolume = boxVolume;
    }

    public Long getBoxGrossWeight() {
        return boxGrossWeight;
    }

    public void setBoxGrossWeight(Long boxGrossWeight) {
        this.boxGrossWeight = boxGrossWeight;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode == null ? null : productSkuCode.trim();
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName == null ? null : productSkuName.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }
}
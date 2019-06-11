package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Createed by sunx on 2018/11/19.<br/>
 */
@Data
@ApiModel("商品查询实体")
public class ProductSearchRequest extends PagesRequest {
    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "sku_codes")
    @JsonProperty("sku_codes")
    private List<String> skuCodes;

    @ApiModelProperty(value = "条形码")
    @JsonProperty("bar_code")
    private String barCode;

    @ApiModelProperty(value = "销售渠道，1为网店，2为门店，3为门店和网店类型集合")
    @JsonProperty("distribution_channels")
    private List<Integer> distributionChannels;

    @ApiModelProperty(value = "品牌集合")
    @JsonProperty("brand_ids")
    private List<String> brandIds;

    @ApiModelProperty(value = "分类集合")
    @JsonProperty("category_ids")
    private List<String> categoryIds;

    @ApiModelProperty(value = "是否上架，0为未上架，1为上架")
    @JsonProperty("on_sale")
    private Integer onSale;

    @ApiModelProperty(value = "库存区间")
    @JsonProperty("range_stock_from")
    private Integer rangeStockFrom;

    @ApiModelProperty(value = "库存区间")
    @JsonProperty("range_stock_to")
    private Integer rangeStockTo;

    @ApiModelProperty(value = "预警状态，0为正常，1为预警")
    @JsonProperty("warning_status")
    private Integer warningStatus;

    @ApiModelProperty(value = "是否仅显示有库存的，是传1，否传0")
    @JsonProperty("show_stock")
    private Integer showStock;

    @ApiModelProperty(value = "是否仅显示有活动的，是传1，否传0")
    @JsonProperty("show_activity")
    private Integer showActivity;

    @ApiModelProperty(value = "排序字段，价格传price，销量传sale")
    @JsonProperty("order_by")
    private String orderBy;

    @ApiModelProperty(value = "升降序标识，asc  升序  desc 降序")
    @JsonProperty("asc_or_desc")
    private String ascOrDesc;

    @ApiModelProperty(value = "关键字，用英文逗号隔开")
    @JsonProperty("keyword")
    private String keyword;

    @ApiModelProperty(value = "搜索参数(销售码或者skucode)")
    @JsonProperty("text")
    private String text;

    @ApiModelProperty(value = "排除skuCode集合，取滞销商品数据时使用",hidden = true)
    @JsonProperty("sku_not_in")
    private List<String> skuCodesNotIn;

    public ProductSearchRequest(String distributorId, String productName, List<String> skuCodes, String barCode,
            List<Integer> distributionChannels, List<String> brandIds, List<String> categoryIds, Integer onSale,
            Integer rangeStockFrom, Integer rangeStockTo, Integer warningStatus, Integer showStock,
            Integer showActivity, String orderBy, String ascOrDesc, String keyword, Integer pageSize, Integer pageNo) {
        this.distributorId = distributorId;
        this.productName = productName;
        this.skuCodes = skuCodes;
        this.barCode = barCode;
        this.distributionChannels = distributionChannels;
        this.brandIds = brandIds;
        this.categoryIds = categoryIds;
        this.onSale = onSale;
        this.rangeStockFrom = rangeStockFrom;
        this.rangeStockTo = rangeStockTo;
        this.warningStatus = warningStatus;
        this.showStock = showStock;
        this.showActivity = showActivity;
        this.orderBy = orderBy;
        this.ascOrDesc = ascOrDesc;
        this.keyword = keyword;
        this.setPageNo(pageNo);
        this.setPageSize(pageSize);
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getSkuCodes() {
        return skuCodes;
    }

    public void setSkuCodes(List<String> skuCodes) {
        this.skuCodes = skuCodes;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public List<Integer> getDistributionChannels() {
        return distributionChannels;
    }

    public void setDistributionChannels(List<Integer> distributionChannels) {
        this.distributionChannels = distributionChannels;
    }

    public List<String> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(List<String> brandIds) {
        this.brandIds = brandIds;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public Integer getOnSale() {
        return onSale;
    }

    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    public Integer getRangeStockFrom() {
        return rangeStockFrom;
    }

    public void setRangeStockFrom(Integer rangeStockFrom) {
        this.rangeStockFrom = rangeStockFrom;
    }

    public Integer getRangeStockTo() {
        return rangeStockTo;
    }

    public void setRangeStockTo(Integer rangeStockTo) {
        this.rangeStockTo = rangeStockTo;
    }

    public Integer getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(Integer warningStatus) {
        this.warningStatus = warningStatus;
    }

    public Integer getShowStock() {
        return showStock;
    }

    public void setShowStock(Integer showStock) {
        this.showStock = showStock;
    }

    public Integer getShowActivity() {
        return showActivity;
    }

    public void setShowActivity(Integer showActivity) {
        this.showActivity = showActivity;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getAscOrDesc() {
        return ascOrDesc;
    }

    public void setAscOrDesc(String ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ProductSearchRequest() {
        super();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

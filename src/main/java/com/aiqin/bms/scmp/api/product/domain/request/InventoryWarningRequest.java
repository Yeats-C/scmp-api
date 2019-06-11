package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wuyongqiang
 * @description 设置库存预警请求对象
 * @date 2018/11/22 15:10
 */
@ApiModel("库存预警请求参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryWarningRequest {
    @ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;

    @ApiModelProperty(value = "商品SKU码列表")
    @JsonProperty("skus")
    private List<String> skus;

    @ApiModelProperty(value = "是否预警（0：取消预警、1：启动预警）")
    @JsonProperty("warnable")
    private Integer warnable;

    @ApiModelProperty(value = "预警阀值(自定义)")
    @JsonProperty("threshold")
    private Integer threshold;

    @ApiModelProperty(value = "默认库存预警值(默认值为3)")
    @JsonProperty("default_warning_stock")
    private Integer defaultWarningStock;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<String> getSkus() {
        return skus;
    }

    public void setSkus(List<String> skus) {
        this.skus = skus;
    }

    public Integer getWarnable() {
        return warnable;
    }

    public void setWarnable(Integer warnable) {
        this.warnable = warnable;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}

package com.aiqin.bms.scmp.api.purchase.domain.bi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel
@Data
public class BiClassification {
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(value="奶粉数量")
    @JsonProperty("milk_num")
    private Long milkNum;

    @ApiModelProperty(value="辅食数量")
    @JsonProperty("side_dish_num")
    private Long sideDishNum;

    @ApiModelProperty(value="保健品数量")
    @JsonProperty("health_care_products_num")
    private Long healthCareProductsNum;

    @ApiModelProperty(value="纸尿片数量")
    @JsonProperty("diaper_num")
    private Long diaperNum;

    @ApiModelProperty(value="洗护用品数量")
    @JsonProperty("products_num")
    private Long productsNum;

    @ApiModelProperty(value="居家用品数量")
    @JsonProperty("occupy_home_num")
    private Long occupyHomeNum;

    @ApiModelProperty(value="喂养数量")
    @JsonProperty("feed_products_num")
    private Long feedProductsNum;

    @ApiModelProperty(value="车床椅数量")
    @JsonProperty("lathe_chair_num")
    private Long latheChairNum;

    @ApiModelProperty(value="玩具数量")
    @JsonProperty("toy_num")
    private Long toyNum;

    @ApiModelProperty(value="图书音像纪念品数量")
    @JsonProperty("books_video_souvenirs_num")
    private Long booksVideoSouvenirsNum;

    @ApiModelProperty(value="棉品数量")
    @JsonProperty("cotton_goods_num")
    private Long cottonGoodsNum;

    @ApiModelProperty(value="赠品数量")
    @JsonProperty("gifts_num")
    private Long giftsNum;

    @ApiModelProperty(value="物料数量")
    @JsonProperty("material_num")
    private Long materialNum;

    @ApiModelProperty(value="德名居数量")
    @JsonProperty("de_ming_ju_num")
    private Long deMingJuNum;

    @ApiModelProperty(value="其他数量")
    @JsonProperty("other_num")
    private Long otherNum;

    @ApiModelProperty(value="总的可用数量")
    @JsonProperty("total_available_num")
    private Long totalAvailableNum;

    @ApiModelProperty(value="奶粉占比")
    @JsonProperty("milk_rate")
    private BigDecimal milkRate;

    @ApiModelProperty(value="辅食占比")
    @JsonProperty("side_dish_rate")
    private BigDecimal sideDishRate;

    @ApiModelProperty(value="保健品占比")
    @JsonProperty("health_care_products_rate")
    private BigDecimal healthCareProductsRate;

    @ApiModelProperty(value="纸尿片占比")
    @JsonProperty("diaper_rate")
    private BigDecimal diaperRate;

    @ApiModelProperty(value="洗护用品占比")
    @JsonProperty("products_rate")
    private BigDecimal productsRate;

    @ApiModelProperty(value="居家用品占比")
    @JsonProperty("occupy_home_rate")
    private BigDecimal occupyHomeRate;

    @ApiModelProperty(value="喂养用品占比")
    @JsonProperty("feed_products_rate")
    private BigDecimal feedProductsRate;

    @ApiModelProperty(value="车床椅占比")
    @JsonProperty("lathe_chair_rate")
    private BigDecimal latheChairRate;

    @ApiModelProperty(value="玩具占比")
    @JsonProperty("toy_rate")
    private BigDecimal toyRate;

    @ApiModelProperty(value="图书音像纪念品占比")
    @JsonProperty("books_video_souvenirs_rate")
    private BigDecimal booksVideoSouvenirsRate;

    @ApiModelProperty(value="棉品占比")
    @JsonProperty("cotton_goods_rate")
    private BigDecimal cottonGoodsRate;

    @ApiModelProperty(value="赠品占比")
    @JsonProperty("gifts_rate")
    private BigDecimal giftsRate;

    @ApiModelProperty(value="物料占比")
    @JsonProperty("material_rate")
    private BigDecimal materialRate;

    @ApiModelProperty(value="德名居占比")
    @JsonProperty("de_ming_ju_rate")
    private BigDecimal deMingJuRate;

    @ApiModelProperty(value="其他占比")
    @JsonProperty("other_rate")
    private BigDecimal otherRate;

    @ApiModelProperty(value="计算时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="更新时间")
    @JsonProperty("update_time")
    private Date updateTime;

}
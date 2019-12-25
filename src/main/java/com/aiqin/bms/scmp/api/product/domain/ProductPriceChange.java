package com.aiqin.bms.scmp.api.product.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Joker
 * @date 2019/2/26 下午5:24
 */
@Data
@ApiModel("商品价格修改 请求VO")
public class ProductPriceChange implements Serializable {

    private static final long serialVersionUID = -7194060198971025483L;

    @ApiModelProperty(value = "自增主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "分销机构名称")
    @JsonProperty("distributor_name")
    private String distributorName;

    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_code")
    private String distributorCode;

    @ApiModelProperty(value = "商品id")
    @JsonProperty("product_id")
    private String productId;

    @ApiModelProperty(value = "商品编码")
    @JsonProperty("product_code")
    private String productCode;

    @ApiModelProperty(value = "商品名称")
    @JsonProperty("product_name")
    private String productName;

    @ApiModelProperty(value = "sku_code")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty(value = "型号")
    @JsonProperty("model")
    private String model;

    @ApiModelProperty(value = "颜色")
    @JsonProperty("color")
    private String color;

    @ApiModelProperty(value = "变动前销售价格")
    @JsonProperty("before_change_price")
    private BigDecimal beforeChangePrice;

    @ApiModelProperty(value = "变动后销售价格")
    @JsonProperty("after_change_price")
    private BigDecimal afterChangePrice;

    @ApiModelProperty(value = "变动前会员价格")
    @JsonProperty("before_change_member_price")
    private BigDecimal beforeChangeMemberPrice;

    @ApiModelProperty(value = "变动后会员价格")
    @JsonProperty("after_change_member_price")
    private BigDecimal afterChangeMemberPrice;

    @ApiModelProperty(value = "列表图")
    @JsonProperty("logo")
    private String logo;

    @ApiModelProperty(value = "封面图")
    @JsonProperty("images")
    private String images;

    @ApiModelProperty(value = "create_time")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "create_by")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value = "update_by")
    @JsonProperty("update_by")
    private String updateBy;

    @JsonProperty("create_by_name")
    @ApiModelProperty("创建人名称")
    public String createByName;

    @JsonProperty("update_by_name")
    @ApiModelProperty("更新人名称")
    public String updateByName;

    @ApiModelProperty(value = "是否被删除，1为被删除")
    @JsonProperty("del_flag")
    private Integer delFlag;


    @ApiModelProperty(value = "进货价")
    @JsonProperty("purchase_price")
    private BigDecimal purchasePrice;

}

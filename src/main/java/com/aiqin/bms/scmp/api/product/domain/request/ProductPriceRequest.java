package com.aiqin.bms.scmp.api.product.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joker
 * @date 2019/2/26 下午5:48
 */
@Data
@ApiModel("商品价格修改 请求VO")
public class ProductPriceRequest implements Serializable {

    private static final long serialVersionUID = -7194060198971025483L;

    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "分销机构名称")
    @JsonProperty("distributor_name")
    private String distributorName;

    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_code")
    private String distributorCode;

    @ApiModelProperty(value = "零售价")
    @JsonProperty("price")
    private Long price;

    @ApiModelProperty(value = "会员价格")
    @JsonProperty("member_price")
    private Long memberPrice;

    @ApiModelProperty(value = "进货价")
    @JsonProperty("purchase_price")
    private Long purchasePrice;

    @ApiModelProperty(value = "sku_code")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty(value = "create_by")
    @JsonProperty("create_by")
    private String createBy;

    @JsonProperty("create_by_name")
    @ApiModelProperty("创建人名称")
    public String createByName;


    @JsonProperty("update_by_name")
    @ApiModelProperty("更新人名称")
    public String updateByName;

    @ApiModelProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:ss:mm")
    @JsonProperty("create_time")
    private Date createTime;
}

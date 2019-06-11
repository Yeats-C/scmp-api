package com.aiqin.bms.scmp.api.product.domain.request.sku.oms;

import com.aiqin.bms.scmp.api.product.domain.response.sku.store.LogisticsCenterApiResVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @功能说明:
 * @author wangxu
 * @date 2019/1/5 0005 10:22
 */
@Data
@ApiModel("oms查询订单请求条件")
public class SearchOrderReqVO{

    @JsonProperty("category_ids")
    @ApiModelProperty("品类ids")
    private List<String> categoryIds;

    @JsonProperty("brand_ids")
    @ApiModelProperty("品牌id")
    private List<String> brandIds;

    @JsonProperty("attribute_id")
    @ApiModelProperty("商品属性ID")
    private String attributeId;

    @ApiModelProperty("商品名称或编码")
    private String product;

    @JsonProperty("tag_type")
    @ApiModelProperty("商品标签类型，0-首单必备,1-主推商品,2-热卖商品,3-新品")
    private Integer tagType;

    @JsonProperty("tag_code")
    @ApiModelProperty("商品标签编码")
    private String tagCode;

    @JsonProperty("include_sku_codes")
    @ApiModelProperty("商品sku")
    private List<String> includeSkuCodes;

    @JsonProperty("exclude_sku_codes")
    @ApiModelProperty("商品sku(不包含)")
    private List<String> excludeSkuCodes;

    @JsonProperty("product_properties")
    @ApiModelProperty("品类ID+品牌ID")
    private List<ProductPropertyDTO> productProperties;

    @JsonProperty("order_by")
    @ApiModelProperty("排序规则")
    private SearchOrderDTO orderBy;

    @JsonProperty("request_time")
    @ApiModelProperty("请求时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;

    @JsonProperty("page_size")
    @ApiModelProperty("每页个数")
    private Integer pageSize;

    @JsonProperty("page_no")
    @ApiModelProperty("当前访问页")
    private Integer pageNo;

    @ApiModelProperty(value = "查询仓库")
    @JsonProperty("ware_houses")
    private List<LogisticsCenterApiResVo> wareHouses;

    @ApiModelProperty("请忽略")
    private List<String> skuCodes;
}

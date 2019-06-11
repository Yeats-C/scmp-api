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
 * @author: wangxu
 * @date: 2019/3/6 0006 19:39
 */
@Data
@ApiModel("分页其他条件请求sku列表")
public class QuerySkuListPageReq {
    @ApiModelProperty(value = "每页条数")
    @JsonProperty("page_size")
    private Integer pageSize;
    @ApiModelProperty(value = "当前页")
    @JsonProperty("page_no")
    private Integer pageNo;

    @ApiModelProperty(value = "查询仓库")
    @JsonProperty("ware_houses")
    private List<LogisticsCenterApiResVo> wareHouses;

    @JsonProperty("include_sku_codes")
    @ApiModelProperty("商品sku")
    private List<String> includeSkuCodes;

    @JsonProperty("exclude_sku_codes")
    @ApiModelProperty("商品sku(不包含)")
    private List<String> excludeSkuCodes;

    @JsonProperty("product_properties")
    @ApiModelProperty("品类ID+品牌ID")
    private List<ProductPropertyDTO> productProperties;

    @JsonProperty("request_time")
    @ApiModelProperty("请求时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;

    @ApiModelProperty("请忽略")
    private List<String> skuCodes;

}

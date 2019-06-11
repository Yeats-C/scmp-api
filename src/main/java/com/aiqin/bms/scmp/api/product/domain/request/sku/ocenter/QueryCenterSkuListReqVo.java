package com.aiqin.bms.scmp.api.product.domain.request.sku.ocenter;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.aiqin.bms.scmp.api.product.domain.request.sku.oms.ProductPropertyDTO;
import com.aiqin.bms.scmp.api.product.domain.response.sku.store.LogisticsCenterApiResVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @date 2019/4/16 14:29
 * @description TODO
 */

@Data
@ApiModel("运营中心查询sku列表请求")
public class QueryCenterSkuListReqVo extends PageReq {

    @ApiModelProperty(value = "查询仓库")
    @JsonProperty("ware_houses")
    private List<LogisticsCenterApiResVo> wareHouses;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @JsonProperty("include_sku_codes")
    @ApiModelProperty("商品sku(包含)")
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
}

package com.aiqin.bms.scmp.api.product.domain.request.sku.store;

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
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("门店商品下sku列表请求条件")
public class QueryStoreSkuListReqVO {

    @ApiModelProperty(value = "商品spu")
    @JsonProperty("spu_code")
    private String spuCode;

    @JsonProperty("color_name")
    @ApiModelProperty("颜色")
    private String colorName;

    @JsonProperty("model_number")
    @ApiModelProperty("型号")
    private String modelNumber;

    @ApiModelProperty(value = "sku编码集合")
    @JsonProperty("sku_codes")
    private List<String> skuCodes;

    @ApiModelProperty("规格")
    @JsonProperty("spec")
    private String spec;

    @ApiModelProperty(value = "门店所属城市id")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value = "门店所属省id")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty("公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty(value = "门店所属区id")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value = "查询仓库")
    @JsonProperty("ware_houses")
    private List<LogisticsCenterApiResVo> wareHouses;

    @JsonProperty("request_time")
    @ApiModelProperty("请求时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;

}

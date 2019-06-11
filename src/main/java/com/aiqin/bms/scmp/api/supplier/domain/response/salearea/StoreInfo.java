package com.aiqin.bms.scmp.api.supplier.domain.response.salearea;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-16
 * @time: 15:22
 */
@ApiModel("列表数据")
@Data
public class StoreInfo {

    @ApiModelProperty("门店编码")
    @JsonProperty(value = "store_code")
    private String storeCode;

    @ApiModelProperty("门店名称")
    @JsonProperty(value = "store_name")
    private String storeName;

    @ApiModelProperty("省名称")
    @JsonProperty(value = "province_name")
    private String provinceName;

    @ApiModelProperty("省id")
    @JsonProperty(value = "province_id")
    private String provinceId;

    @ApiModelProperty("市名称")
    @JsonProperty(value = "city_name")
    private String cityName;

    @ApiModelProperty("县名称")
    @JsonProperty(value = "district_name")
    private String districtName;
}

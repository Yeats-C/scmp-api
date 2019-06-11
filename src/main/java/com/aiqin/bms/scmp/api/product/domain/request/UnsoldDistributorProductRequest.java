package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Createed by sunx on 2019/4/9.<br/>
 */
@Data
@ApiModel("滞销商品请求实体")
@ToString(callSuper = true)
public class UnsoldDistributorProductRequest extends PagesRequest {
    @ApiModelProperty(value = "分销机构编码")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "非滞销商品sku集合",hidden = true)
    @JsonProperty("sold_sku_codes")
    private List<String> soldSkuCodes;
}

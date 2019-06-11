package com.aiqin.bms.scmp.api.product.domain.request.brand;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Createed by sunx on 2018/12/5.<br/>
 */
@Data
@ApiModel("分销机构上架微商城品牌分页查询请求实体")
public class ProductBrandDistributionPageRequest extends PagesRequest {
    @ApiModelProperty("品牌名称")
    @JsonProperty("brand_name")
    private String brandName;

    @ApiModelProperty("分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;
}

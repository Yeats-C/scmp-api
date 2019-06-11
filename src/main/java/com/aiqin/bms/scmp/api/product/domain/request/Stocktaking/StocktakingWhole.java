package com.aiqin.bms.scmp.api.product.domain.request.Stocktaking;

import com.aiqin.bms.scmp.api.product.domain.Stocktaking.StocktakingInfo;
import com.aiqin.bms.scmp.api.product.domain.Stocktaking.StocktakingProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("添加盘点数据")
public class StocktakingWhole {
    
	@ApiModelProperty(value = "盘点信息")
    @JsonProperty("stocktaking_info")
    private StocktakingInfo stocktakingInfo;
	
	@ApiModelProperty(value = "不传参-总数据条数")
    @JsonProperty("total_count")
    private Integer totalCount;
	
	@ApiModelProperty(value = "商品信息")
    @JsonProperty("product_list")
    private List<StocktakingProductInfo> productList;

}

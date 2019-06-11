package com.aiqin.bms.scmp.api.product.domain.request.Stocktaking;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("查询盘点记录参数")
@Data
public class SelectStocktakingRequest extends PagesRequest{
   
	@ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;
	
	@ApiModelProperty(value = "盘点id")
    @JsonProperty("stocktaking_id")
    private String stocktakingId;
	
	@ApiModelProperty(value = "盘点id集合")
    @JsonProperty("stocktaking_id_List")
    private List<String> stocktakingIdList;
	
	@ApiModelProperty(value = "仓库类型1为门店自有仓，2为爱亲大仓")
    @JsonProperty("storage_type")
    private Integer storageType;
	
	@ApiModelProperty(value = "仓位类型1:陈列仓位 2:退货仓位 3:存储仓位")
    @JsonProperty("stock_type")
    private Integer stockType;
	
	@ApiModelProperty(value = "盘点日期")
    @JsonProperty("stocktaking_date")
    private String stocktakingDate;
	
	
	@ApiModelProperty(value = "备注")
    @JsonProperty("memo")
    private String memo;
}

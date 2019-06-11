package com.aiqin.bms.scmp.api.product.domain.Stocktaking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @author hzy
 * 盘点
 */
@Data
@ApiModel("盘点信息")
public class StocktakingInfo {

	@ApiModelProperty(value = "ID")
    @JsonProperty("stocktaking_id")
    private String stocktakingId;
	
	@ApiModelProperty(value = "所属门店")
    @JsonProperty("store_id")
    private String storeId;
    
    @ApiModelProperty(value = "门店名称")
    @JsonProperty("store_name")
    private String storeName;
	
	@ApiModelProperty(value = "盘点单号")
    @JsonProperty("stocktaking_code")
    private String stocktakingCode;
	
	@ApiModelProperty(value = "仓库类型，1为门店自有仓，2为爱亲大仓")
    @JsonProperty("storage_type")
    private Integer storageType;
    
    @ApiModelProperty(value = "仓位类型，1:陈列仓位 2:退货仓位 3:存储仓位")
    @JsonProperty("stock_type")
    private Integer stockType;
	
	@ApiModelProperty(value = "盘点状态 1:未完成  2：已完成 ")
    @JsonProperty("stocktaking_status")
    private Integer stocktakingStatus;
	
	@ApiModelProperty(value = "盘点日期 YYYY-MM-DD")
    @JsonProperty("stocktaking_date")
    private String stocktakingDate;
	
	@ApiModelProperty(value = "操作人")
    @JsonProperty("create_by")
    private String createBy;
	
	@ApiModelProperty(value = "修改人")
    @JsonProperty("update_by")
    private String updateBy;
	
	@ApiModelProperty(value = "盘点完成时间",example = "2001-01-01 01:01:01")
    @JsonProperty("create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
	
	@ApiModelProperty(value = "修改日期",example = "2001-01-01 01:01:01")
    @JsonProperty("update_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
	
	@ApiModelProperty(value = "备注")
    @JsonProperty("memo")
    private String memo;
}

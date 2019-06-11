package com.aiqin.bms.scmp.api.product.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 *
 * @author zhujunchao
 * @create 2018-12-10 17:39
 */
@ApiModel("仓位查询参数")
@Data
public class InventoryWarehouse extends PagesRequest {
    @ApiModelProperty(value = "分销机构id")
    @JsonProperty("distributor_id")
    private String distributorId;

    @ApiModelProperty(value = "分销机构名称")
    @JsonProperty("distributor_name")
    private String distributorName;


    @JsonProperty("storage_type")
    @ApiModelProperty(value = "仓库类型，1为门店自有仓，2为爱亲大仓")
    private Integer storageType;

//    @ApiModelProperty(value = "仓库类型(1:自有仓 2:爱亲仓)")
//    @JsonProperty("distributor_type")
//    private String distributorType;

    @ApiModelProperty(value = "陈列仓位")
    @JsonProperty("display_stock")
    private String displayStock;

    @ApiModelProperty(value = "退货仓位")
    @JsonProperty("return_stock")
    private String returnStock;

    @ApiModelProperty(value = "存储仓位")
    @JsonProperty("storage_stock")
    private String storageStock;
}

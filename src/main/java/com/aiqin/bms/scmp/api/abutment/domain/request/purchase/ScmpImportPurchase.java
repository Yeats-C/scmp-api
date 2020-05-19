package com.aiqin.bms.scmp.api.abutment.domain.request.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunx
 * @description scmp采购单
 * @date 2019-08-03
 */
@Data
@ApiModel("熙耘采购&退供单据-->结算系统     接口文件")
public class ScmpImportPurchase {

    @JsonProperty("purchase")
    @ApiModelProperty("采购&退供单据")
    private Purchase purchase;

    @JsonProperty("storage")
    @ApiModelProperty("出入库信息")
    private Storage storage;
    
    
}

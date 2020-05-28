package com.aiqin.bms.scmp.api.abutment.domain.request.sale;

import com.aiqin.bms.scmp.api.abutment.domain.request.purchase.PurchaseStorage;
import com.aiqin.bms.scmp.api.abutment.domain.request.purchase.Storage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author sunx
 * @description 熙耘销售订单&配送退货单据
 * @date 2019-08-03
 */
@Data
@ApiModel("熙耘销售订单&配送退货单据-->结算系统     接口文件")
public class ScmpImportOrder {

    /**销售订单&配送退货单据**/
    @JsonProperty("order")
    @ApiModelProperty("销售订单&配送退货单据")
    private Order order;

//    /**出入库信息**/
//    @JsonProperty("storage")
//    @ApiModelProperty("出入库信息")
//    private Storage storage;
    /**出入库信息**/
    @JsonProperty("storage")
    @ApiModelProperty("出入库信息")
    private PurchaseStorage storage;
    
    
}

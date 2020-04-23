package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuSaleArea;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-10
 * @time: 14:00
 */
@Data
@ApiModel("sku销售詳情vo")
public class QueryProductDetailReqVO extends PageReq {

    @ApiModelProperty("销售区域编码编码")
    private String  code;

    private String personId;

    private List<ProductSkuSaleArea> skuList;
}



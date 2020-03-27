package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-03
 * @time: 15:50
 */
@ApiModel("销售区域主表信息")
@Data
public class ProductSkuSaleAreaMainReqVO2    {


    @ApiModelProperty("限制区域名称")
    private String code;


    @ApiModelProperty("1显示下属sku列表2不显示")
    private Integer type;


}

package com.aiqin.bms.scmp.api.product.domain.response.salearea;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaMainRespVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-06
 * @time: 10:11
 */
@Data
@ApiModel("销售区域sku列表")
public class QueryProductSaleAreaSkuRespVO2 {

    @ApiModelProperty("sku编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  id;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("供货渠道类别code")
    private String categoriesSupplyChannelsCode;


    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;


    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("销售区域信息")
    private List<QueryProductSaleAreaMainRespVO> queryProductSaleAreaMainRespVOS;
}

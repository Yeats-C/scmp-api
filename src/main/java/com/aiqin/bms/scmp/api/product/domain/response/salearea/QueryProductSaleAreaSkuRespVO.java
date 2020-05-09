package com.aiqin.bms.scmp.api.product.domain.response.salearea;

import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInfo;
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
 * @date: 2019-06-06
 * @time: 10:11
 */
@Data
@ApiModel("销售区域sku列表")
public class QueryProductSaleAreaSkuRespVO {

    @ApiModelProperty("sku编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  id;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    @ApiModelProperty("限制区域名称")
    private String code;

    @ApiModelProperty("限制区域名称")
    private String name;


    @ApiModelProperty("总sku数量")
    private String skuCount;


    @ApiModelProperty("采购组sku数量")
    private String skuProcurementCount;

    @ApiModelProperty("销售sku信息")
    private List<ProductSkuInfo> skuList;
}

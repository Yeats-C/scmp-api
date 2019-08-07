package com.aiqin.bms.scmp.api.product.domain.response.salearea;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-04
 * @time: 10:56
 */
@ApiModel("销售区域临时表列表vo")
@Data
public class QueryProductSaleAreaMainRespVO {

    @ApiModelProperty("限制区域名称")
    private String code;

    @ApiModelProperty("限制区域名称")
    private String name;

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

    @ApiModelProperty("1新增2修改")
    private Integer applyType;

    @ApiModelProperty("sku信息")
    private List<QueryProductSaleAreaRespVO> skuList;

    @ApiModelProperty("区域信息")
    private List<ProductSaleAreaInfoRespVO> areaList;

}

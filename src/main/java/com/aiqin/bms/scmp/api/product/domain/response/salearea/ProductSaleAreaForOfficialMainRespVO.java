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
 * @date: 2019-06-05
 * @time: 17:00
 */
@Data
@ApiModel("正式表详情")
public class ProductSaleAreaForOfficialMainRespVO {

    @ApiModelProperty("限制区域名称")
    private String code;

    @ApiModelProperty("限制区域名称")
    private String name;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("sku信息")
    private List<QueryProductSaleAreaRespVO> skuList;

    @ApiModelProperty("区域信息")
    private List<ProductSaleAreaInfoRespVO> areaList;

    @ApiModelProperty("渠道信息")
    private List<ProductSaleAreaChannelRespVO> channelList;
}

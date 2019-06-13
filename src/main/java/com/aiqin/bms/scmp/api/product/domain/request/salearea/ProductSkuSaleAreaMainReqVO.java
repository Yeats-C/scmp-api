package com.aiqin.bms.scmp.api.product.domain.request.salearea;

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
 * @date: 2019-06-03
 * @time: 15:50
 */
@ApiModel("销售区域主表信息")
@Data
public class ProductSkuSaleAreaMainReqVO {

    @ApiModelProperty("限制区域名称")
    private String code;

    @ApiModelProperty("限制区域名称")
    private String name;

    @ApiModelProperty("正式限制区域编码")
    private String officialCode;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8" ,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8" ,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("1新增2修改")
    private Integer applyType;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("区域信息")
    private List<ProductSkuSaleAreaChannelReqVO> channelList;

    @ApiModelProperty("区域信息")
    private List<ProductSkuSaleAreaInfoReqVO> areaList;

    @ApiModelProperty("sku信息")
    private List<ProductSaleAreaReqVO> skuList;
}

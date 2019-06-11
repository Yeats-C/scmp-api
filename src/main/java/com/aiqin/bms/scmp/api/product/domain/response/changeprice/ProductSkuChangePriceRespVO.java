package com.aiqin.bms.scmp.api.product.domain.response.changeprice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-23
 * @time: 9:40
 */
@Data
@ApiModel("变价详情展示vo")
public class ProductSkuChangePriceRespVO {

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("变价类型名称")
    private String changePriceName;

    @ApiModelProperty("变价类型编码")
    private String changePriceType;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("运费承担方编码")
    private String costBearerCode;

    @ApiModelProperty("运费承担方名称")
    private String costBearerName;

    @ApiModelProperty("预算")
    private Long budget;

    @ApiModelProperty("申请的表单号")
    private String formNo;

    @ApiModelProperty("状态")
    private Integer applyStatus;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("列表数据")
    List<ProductSkuChangePriceInfoRespVO> infoList;

    @ApiModelProperty("图片数据")
    List<ProductSkuChangePriceImageRespVO> images;

    @ApiModelProperty("区域详情")
    List<ProductSkuChangePriceAreaInfoRespVO> areaList;
}

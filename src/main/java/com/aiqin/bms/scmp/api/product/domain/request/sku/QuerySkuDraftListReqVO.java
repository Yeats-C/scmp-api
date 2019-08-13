package com.aiqin.bms.scmp.api.product.domain.request.sku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("sku待申请列表请求条件")
public class QuerySkuDraftListReqVO  {

    @ApiModelProperty("商品(SPU)名称")
    private String productName;

    @ApiModelProperty("商品(SPU编码")
    private String productCode;

    @ApiModelProperty("品类集合编码 1级编码 2级编码 3级编码 4级编码")
    private List<String> productCategoryCodes;

    @ApiModelProperty(value = "1级品类编码",hidden = true)
    private String productCategoryLv1Code;

    @ApiModelProperty(value ="2级品类编码",hidden = true)
    private String productCategoryLv2Code;

    @ApiModelProperty(value ="3级品类编码",hidden = true)
    private String productCategoryLv3Code;

    @ApiModelProperty(value ="4级品类编码",hidden = true)
    private String productCategoryLv4Code;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("品牌编码")
    private String productBrandCode;

    @ApiModelProperty("申请人")
    private String applyBy;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private String personId;




}

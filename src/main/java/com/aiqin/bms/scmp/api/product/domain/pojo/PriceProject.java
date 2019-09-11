package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("价格项目PO")
@Data
public class PriceProject extends CommonBean {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("编码")
    private String priceProjectCode;

    @ApiModelProperty("名称")
    private String priceProjectName;

    @ApiModelProperty("价格类型编码")
    private String priceTypeCode;

    @ApiModelProperty("价格类型名称")
    private String priceTypeName;

    @ApiModelProperty("价格大类编码")
    private String priceCategoryCode;

    @ApiModelProperty("价格大类名称")
    private String priceCategoryName;

    @ApiModelProperty("排序")
    private Integer priceProjectOrder;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("是否禁用(0:启用 1:禁用)")
    private Byte priceProjectEnable;


}
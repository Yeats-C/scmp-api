package com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 描述:采购组人员外部提供接口实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("采购组人员外部提供接口实体")
public class PurchaseGroupBuyerVo extends CommonBean {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购人员名称")
    private String buyerName;

    @ApiModelProperty("采购人员编码")
    private String buyerCode;

    @ApiModelProperty("职位编码")
    private String positionCode;

    @ApiModelProperty("职位名称")
    private String positionName;

    @ApiModelProperty("职位级别编码")
    private String positionLevelCode;

    @ApiModelProperty("职位级别名称")
    private String positionLevelName;

}
package com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述: 采购组数据库交互实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("采购组数据库交互实体")
public class PurchaseGroupDTO extends CommonBean {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("禁用启用状态，0是禁用，1是未禁用")
    private Byte enable;

    @ApiModelProperty("排序")
    private Integer purchaseGroupOrder;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("负责人编号")
    private String responsiblePersonCode;

    @ApiModelProperty("负责人名称")
    private String responsiblePersonName;


}

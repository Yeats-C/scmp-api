package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请合同品牌")
@Data
public class ApplyContractBrand extends CommonBean {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("申请合同编号")
    private String applyContractCode;

    @ApiModelProperty("品牌编码")
    private String brandCode;

    @ApiModelProperty("品牌名称")
    private String brandName;

}
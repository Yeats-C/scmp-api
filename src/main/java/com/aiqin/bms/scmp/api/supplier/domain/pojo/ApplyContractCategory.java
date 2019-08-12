package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请合同品类")
@Data
public class ApplyContractCategory extends CommonBean {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("申请合同编号")
    private String applyContractCode;

    @ApiModelProperty("品类编码")
    private String categoryCode;

    @ApiModelProperty("品类名称")
    private String categoryName;

}
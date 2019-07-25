package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("合同品牌")
@Data
public class ContractBrand extends CommonBean {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("合同编号")
    private String contractCode;

    @ApiModelProperty("品牌编码")
    private String brandCode;

    @ApiModelProperty("品牌名称")
    private String brandName;


}
package com.aiqin.bms.scmp.api.bireport.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("低库存respVo")
@Data
public class LowInventoryRespVo {

    @ApiModelProperty("主键")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty("所属部门编码")
    @JsonProperty("product_sort_code")
    private String productSortCode;

    @ApiModelProperty("所属部门")
    @JsonProperty("product_sort_name")
    private String productSortName;

    @ApiModelProperty("采购组编码")
    @JsonProperty("procurement_section_code")
    private String procurementSectionCode;

    @ApiModelProperty("采购组名称")
    @JsonProperty("procurement_section_name")
    private String procurementSectionName;

    @ApiModelProperty("负责人编码")
    @JsonProperty("responsible_person_code")
    private String responsiblePersonCode;

    @ApiModelProperty("负责人名称")
    @JsonProperty("responsible_person_name")
    private String responsiblePersonName;

    @ApiModelProperty("仓位类型名称编码")
    @JsonProperty("warehouse_type_code")
    private String warehouseTypeCode;

    @ApiModelProperty("仓位类型名称(库房)")
    @JsonProperty("warehouse_type_name")
    private String warehouseTypeName;

}

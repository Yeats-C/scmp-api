package com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述: 外部接口根据地区调用合同list查询
 *
 * @Author: Kt.w
 * @Date: 2019/1/15
 * @Version 1.0
 * @since 1.0
 */
@ApiModel("外部接口根据地区调用合同list查询")
@Data
public class WarehouseListReqVo {

    @ApiModelProperty("省编码")
    @JsonProperty("province_code")
    private String provinceCode;

    @ApiModelProperty("市编码")
    @JsonProperty("city_code")
    private String cityCode;

    @ApiModelProperty("仓库类型编码 1销售库 2特卖库 3残品库 4监管库  null查询全部")
    @JsonProperty("warehouse_type_code")
    private Byte warehouseTypeCode;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填")
    private String companyCode;
}

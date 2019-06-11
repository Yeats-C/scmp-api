package com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 描述:库房新增实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("库房新增请求实体")
public class WarehouseReqVo {

    @ApiModelProperty("库房名称")
    @NotEmpty(message = "库房名称不能为空")
    private String warehouseName;

    @ApiModelProperty("物流中心编码")
    @NotEmpty(message = "物流中心编码不能为空")
    private String logisticsCenterCode;

    @ApiModelProperty("物流中心名称")
    @NotEmpty(message = "物流中心名称不能为空")
    private String logisticsCenterName;

    @ApiModelProperty("仓库类型名称")
    @NotEmpty(message = "仓库类型名称不能为空")
    private String warehouseTypeName;

    @ApiModelProperty("仓库类型编码")
    @NotNull(message = "仓库类型编码不能为空")
    private Byte warehouseTypeCode;

    @ApiModelProperty("省编码")
    @NotEmpty(message = "省编码不能为空")
    private String provinceCode;

    @ApiModelProperty("省名称")
    @NotEmpty(message = "省名称不能为空")
    private String provinceName;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("市名称")
    private String cityName;

    @ApiModelProperty("区编码")
    private String countyCode;

    @ApiModelProperty("区名称")
    private String countyName;

    @ApiModelProperty("详细地址")
    @NotEmpty(message = "详细地址不能为空")
    private String detailedAddress;

    @ApiModelProperty("联系人")
    @NotEmpty(message = "联系人不能为空")
    private String contact;

    @ApiModelProperty("联系电话")
    @NotEmpty(message = "联系电话不能为空")
    private String phone;

}

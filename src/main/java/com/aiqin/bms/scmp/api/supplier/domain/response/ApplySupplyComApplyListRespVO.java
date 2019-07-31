package com.aiqin.bms.scmp.api.supplier.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("供应商申请信息列表")
public class ApplySupplyComApplyListRespVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("申请编号")
    private String applyCode;

    @ApiModelProperty("申请供应单位名称")
    private String applySupplyName;

    @ApiModelProperty("供货单位编码")
    private String supplyCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("联系姓名")
    private String contactName;

    @ApiModelProperty("手机号码")
    private String mobilePhone;

    @ApiModelProperty("采购组")
    private String purchaseGroupName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("省")
    private String provinceName;

    @ApiModelProperty("市")
    private String cityName;

    @ApiModelProperty("区县名称")
    private String districtName;

}

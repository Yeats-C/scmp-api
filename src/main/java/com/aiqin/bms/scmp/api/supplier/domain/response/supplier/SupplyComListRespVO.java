package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:供货单位管理列表返回
 * @author: wangxu
 * @date: 2018/12/7 0007 17:45
 */
@ApiModel("供应商管理列表返回")
@Data
public class SupplyComListRespVO {

    @ApiModelProperty("供应商主键ID")
    private Long id;

    @ApiModelProperty("供应商名称")
    private String supplyComName;

    @ApiModelProperty("供应商编码")
    private String supplyComCode;

    @ApiModelProperty("供应商类型")
    private String supplyComType;

    @ApiModelProperty("所属供应商编码")
    private String supplierCode;

    @ApiModelProperty("所属供应商名称")
    private String supplierName;

    @ApiModelProperty("简称")
    private String abbreviation;

    @ApiModelProperty("联系姓名")
    private String contactName;

    @ApiModelProperty("手机号码")
    private String mobilePhone;

    @ApiModelProperty("逻辑删除")
    private String logicDelete;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("是否禁用")
    private String enable;

    @ApiModelProperty("申请状态")
    private Byte applyStatus;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

    @ApiModelProperty("省id")
    private String provinceId;

    @ApiModelProperty("省")
    private String provinceName;

    @ApiModelProperty("市id")
    private String cityId;

    @ApiModelProperty("市")
    private String cityName;

    @ApiModelProperty("区县id")
    private String districtId;

    @ApiModelProperty("区县名称")
    private String districtName;


}

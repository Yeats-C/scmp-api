package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplyDeliveryInfoReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySettlementInfoReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyAcctReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("申请供货单位")
@Data
public class ApplySupplyComDTO {
    @ApiModelProperty("申请供应单位名称")
    private String applySupplyName;

    @ApiModelProperty("申请供应单位类型")
    private String applySupplyType;

    @ApiModelProperty("简称")
    private String applyAbbreviation;

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

    @ApiModelProperty("区县")
    private String districtName;

    @ApiModelProperty("地区")
    private String area;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("联系姓名")
    private String contactName;

    @ApiModelProperty("手机号码")
    private String mobilePhone;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("传真")
    private String fax;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("公司网站")
    private String companyWebsite;

    @ApiModelProperty("申请供应商code")
    private String applySupplierCode;

    @ApiModelProperty("申请编号")
    private String applyCode;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("采购组")
    private String purchasingGroupCode;

    @ApiModelProperty("结算信息")
    private ApplySettlementInfoReqVO applySettlementInfoReqVO;

    @ApiModelProperty("发货信息")
    private ApplyDeliveryInfoReqVO applyDeliveryInfoReq;

    @ApiModelProperty("供货单位账户")
    private ApplySupplyCompanyAcctReqVO applySupplyCompanyAccountReq;

}
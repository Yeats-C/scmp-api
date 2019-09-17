package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("申请供货单位")
@Data
public class ApplySupplyCompany extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

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

    @ApiModelProperty("公司网站")
    private String companyWebsite;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("删除标记(0:正常 1:删除")
    private Byte delFlag;

    @ApiModelProperty("申请供应商code")
    private String applySupplierCode;

    @ApiModelProperty("申请供货单位编号")
    private String applySupplyCompanyCode;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请供应商名称")
    private String applySupplierName;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("供应商code")
    private String supplierCode;

    @ApiModelProperty("所属供应商名称")
    private String supplierName;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("表单号")
    private String formNo;

    @ApiModelProperty("供应商编码")
    private String supplyCompanyCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

    @ApiModelProperty("税号")
    private String taxId;

    @ApiModelProperty("法人代表")
    private String corporateRepresentative;

    @ApiModelProperty("注册资金")
    private BigDecimal registeredCapital;

    @ApiModelProperty("是否禁用")
    private Byte enable;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("最低订货金额")
    private Long minOrderAmount;

    @ApiModelProperty("最高订货金额")
    private Long maxOrderAmount;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty("评分")
    private BigDecimal starScore;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("结款方式")
    private String paymentMethod;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("供货区域")
    private String deliveryArea;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审批名称")
    private String approvalName;
}
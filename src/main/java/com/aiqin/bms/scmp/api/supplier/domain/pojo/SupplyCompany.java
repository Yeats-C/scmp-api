package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("供货单位")
@Data
public class SupplyCompany extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("供应单位名称")
    private String supplyName;

    @ApiModelProperty("供应单位类型")
    private String supplyType;

    @ApiModelProperty("简称")
    private String supplyAbbreviation;

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

    @ApiModelProperty("供应商编号")
    private String supplierCode;

    @ApiModelProperty("供货单位编号")
    private String supplyCode;

    @ApiModelProperty("申请供货单位code")
    private String applySupplyCompanyCode;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请状态(0 ：待审 1，审批中 2 审批通过 ，3 审批失败 4，已撤销 )")
    private Byte applyStatus;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

    @ApiModelProperty("邮编")
    private String zipCode;

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

    @ApiModelProperty("省id")
    private String sendProvinceId;

    @ApiModelProperty("省")
    private String sendProvinceName;

    @ApiModelProperty("市id")
    private String sendCityId;

    @ApiModelProperty("市")
    private String sendCityName;

    @ApiModelProperty("区县id")
    private String sendDistrictId;

    @ApiModelProperty("区县")
    private String sendDistrictName;

    @ApiModelProperty("发送地址")
    private String sendingAddress;

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
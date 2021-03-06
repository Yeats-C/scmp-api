package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo;

import com.aiqin.bms.scmp.api.supplier.domain.request.approvalfile.ApprovalFileInfoReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordReqVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@ApiModel("申请供应商信息")
@Data
public class ApplySupplyCompanyReqVO {

    @ApiModelProperty(value = "供应商code",notes = "进行数据修改时必须传的参数")
    private String applySupplyCode;

    @NotEmpty(message = "申请供应商名称不能为空")
    @ApiModelProperty("申请供应商名称")
    private String applySupplyName;

    @ApiModelProperty("供应商集团名称")
    private String supplierName;

    @ApiModelProperty("供应商集团编码")
    private String supplierCode;

    @ApiModelProperty("供应商集团简称")
    private String supplierAbbreviation;

    @ApiModelProperty("供应商属性 0:自营 1:平台")
    private String property;

    @ApiModelProperty("申请供应商类型")
    private String applySupplyType;

    @ApiModelProperty("申请供应商类型名称（导入后返给前端，只做展示）")
    private String applySupplyTypeName;

//    @NotEmpty(message = "简称不能为空")
    @ApiModelProperty("简称")
    private String applyAbbreviation;

    @NotEmpty(message = "省id不能为空")
    @ApiModelProperty("省id")
    private String provinceId;

    @NotEmpty(message = "省不能为空")
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

    @NotEmpty(message = "地址不能为空")
    @ApiModelProperty("地址")
    private String address;

    @NotEmpty(message = "联系姓名不能为空")
    @ApiModelProperty("联系姓名")
    private String contactName;

    @NotEmpty(message = "手机号码不能为空")
    @ApiModelProperty("手机号码")
    private String mobilePhone;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("传真")
    private String fax;

    @NotEmpty(message = "邮箱不能为空")
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty(value = "采购组编号", hidden = true)
    private String purchasingGroupCode;

    @ApiModelProperty(value = "采购组名称", hidden = true)
    private String purchasingGroupName;

    @ApiModelProperty("税号")
    private String taxId;

    @ApiModelProperty("法人代表")
    private String corporateRepresentative;

    @ApiModelProperty("注册资金")
    private BigDecimal registeredCapital;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("公司网址")
    private String companyWebsite;

    @ApiModelProperty("最低订货金额")
    private BigDecimal minOrderAmount;

    @ApiModelProperty("最高订货金额")
    private BigDecimal maxOrderAmount;
    
    @ApiModelProperty("发货信息")
    private List<ApplyDeliveryInfoReqVO> deliveryInfoList;

    @ApiModelProperty("文件信息")
    private List<SupplierFileReqVO> fileReqVOList;

    @ApiModelProperty("是否增加账户信息  0:增加 1:不增加")
    private Byte addAccount;

    @ApiModelProperty("供货单位账户")
    private ApplySupplyCompanyAcctReqVO applySupplyCompanyAccountReq;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "评分",hidden = true)
    private BigDecimal starScore = BigDecimal.ZERO;

    @ApiModelProperty("标签信息")
    private  SaveUseTagRecordReqVo tagInfo;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

    @ApiModelProperty("错误原因")
    private String error;

    @ApiModelProperty("是否禁用")
    private Byte enable;

    @ApiModelProperty("判断来源 0:非导入 1:导入, 如果从导入新增/修改 不进入审批流,审批状态待提交")
    private Byte source = 0;

    @ApiModelProperty("采购组")
    private List<ApplySupplyCompanyPurchaseGroupReqVo> purchaseGroupVos;

    @ApiModelProperty("结款方式")
    private String paymentMethod;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("供货区域")
    private String deliveryArea;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审批名称")
    @NotNull(message = "审批名称不能为空")
    private String approvalName;

    @ApiModelProperty("审批备注")
    @NotNull(message = "审批备注不能为空")
    private String approvalRemark;

    @ApiModelProperty("审批附件")
    private List<ApprovalFileInfoReqVo> approvalFileInfos;

    @ApiModelProperty("职位编码")
    private String positionCode;

    @ApiModelProperty("职位名称")
    private String positionName;

    @ApiModelProperty("付款方式编号")
    private String payTypeCode;
    @ApiModelProperty("付款方式名称")
    private String payTypeName;

}
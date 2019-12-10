package com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto;

import com.aiqin.bms.scmp.api.common.CommonBean;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplyDeliveryInfoReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyAcctReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyPurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierFileReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.SaveUseTagRecordReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @功能说明:申请供货单位映射实体
 * @author: wangxu
 * @date: 2018/12/7 0007 14:28
 */
@Data
public class ApplySupplyCompanyReqDTO extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("申请供应商名称")
    private String applySupplyName;

    @ApiModelProperty("供应商编码")
    private String supplyCompanyCode;

    @ApiModelProperty("申请供应商类型")
    private String applySupplyType;

    @ApiModelProperty("供应商集团名称")
    private String supplierName;

    @ApiModelProperty("供应商集团编码")
    private String supplierCode;

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

    @ApiModelProperty("申请供应商集团Code")
    private String applySupplierCode;

    @ApiModelProperty("申请编号")
    private String applyCode;

    @ApiModelProperty("申请状态(0:等待审核中 1:审核中)")
    private Byte applyStatus;

    @ApiModelProperty("申请类型")
    private Byte applyType;

    @ApiModelProperty("采购组编号")
    private String purchasingGroupCode;

    @NotEmpty(message = "采购组名称不能为空")
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

    @NotNull(message = "最低订货金额不能为空")
    @ApiModelProperty("最低订货金额")
    private Long minOrderAmount;

    @NotNull(message = "最高订货金额不能为空")
    @ApiModelProperty("最高订货金额")
    private Long maxOrderAmount;

    @ApiModelProperty("发货信息")
    private List<ApplyDeliveryInfoReqVO> deliveryInfoList;

    @ApiModelProperty("文件信息")
    private List<SupplierFileReqVO> fileReqVOList;

    @ApiModelProperty("采购组")
    private List<ApplySupplyCompanyPurchaseGroupReqVo> purchaseGroupVos;

    @ApiModelProperty("供货单位账户")
    private ApplySupplyCompanyAcctReqVO applySupplyCompanyAccountReq;

    private String formNo;

    @ApiModelProperty("是否增加账户信息  0:增加 1:不增加")
    private Byte addAccount;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("营业执照")
    private String businessLicense;

    @ApiModelProperty("评分")
    private BigDecimal starScore;

    @ApiModelProperty(value = "标签信息",hidden = true)
    private  SaveUseTagRecordReqVo tagInfo;

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

    @ApiModelProperty("审批备注")
    private String approvalRemark;

    @ApiModelProperty("职位编码")
    private String positionCode;

    @ApiModelProperty("职位名称")
    private String positionName;
}
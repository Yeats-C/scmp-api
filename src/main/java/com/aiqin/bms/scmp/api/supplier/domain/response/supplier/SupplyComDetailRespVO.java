package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.aiqin.bms.scmp.api.product.domain.response.sku.QueryProductSkuListResp;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierFileReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @功能说明:供货单位详情返回
 * @author: wangxu
 * @date: 2018/12/12 0012 15:26
 */
@ApiModel("供应商详情返回")
@Data
public class SupplyComDetailRespVO {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("供应商编号")
    private String applySupplyCode;

    @ApiModelProperty("供应商名称")
    private String applySupplyName;

    @ApiModelProperty("供应商类型")
    private String applySupplyType;

    @ApiModelProperty("供应商类型")
    private String applySupplyTypeName;

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

    @ApiModelProperty("所属集团名称")
    private String supplierName;

    @ApiModelProperty("所属集团编码")
    private String supplierCode;

    @ApiModelProperty("采购组编码")
    private String purchasingGroupCode;

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

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("收货信息")
    private List<DeliveryInfoRespVO> deliveryInfoRespVOS;

    @ApiModelProperty("标签信息")
    private List<DetailTagUseRespVo> useTagRecordReqVos;

    @ApiModelProperty("日志列表")
    private List<LogData> logDataList;

    @ApiModelProperty("文件信息")
    private List<SupplierFileReqVO> fileReqVOList;

    @ApiModelProperty("账户信息")
    private List<QuerySupplierComAcctRespVo> supplierComAcctRespVos;

    @ApiModelProperty("sku信息")
    private List<QueryProductSkuListResp> skuListRespVos;

}

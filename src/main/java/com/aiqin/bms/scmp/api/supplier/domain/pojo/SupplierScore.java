package com.aiqin.bms.scmp.api.supplier.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("供应商评分")
@Data
public class SupplierScore extends CommonBean {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("评分编号")
    private String scoreCode;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("送货及时性")
    private BigDecimal deliveryTimely = BigDecimal.ZERO;

    @ApiModelProperty("退换货及时性")
    private BigDecimal returnTimely = BigDecimal.ZERO;

    @ApiModelProperty("订单满足率")
    private BigDecimal orderFillRate = BigDecimal.ZERO;

    @ApiModelProperty("残损率")
    private BigDecimal damageRate = BigDecimal.ZERO;

    @ApiModelProperty("费用支持")
    private BigDecimal costSupport = BigDecimal.ZERO;

    @ApiModelProperty("活动支持")
    private BigDecimal activitySupport = BigDecimal.ZERO;

    @ApiModelProperty("发票返回及时性")
    private BigDecimal invoiceReturnTimely = BigDecimal.ZERO;

    @ApiModelProperty("部门编码")
    private String departCode;

    @ApiModelProperty("部门名称")
    private String departName;

    @ApiModelProperty("评分姓名")
    private String scorerName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;


}
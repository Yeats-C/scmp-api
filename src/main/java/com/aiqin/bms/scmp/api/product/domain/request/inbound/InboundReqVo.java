package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author HuangLong
 * @date 2018/12/24
 */
@ApiModel("新增采购请求vo")
@Data
public class InboundReqVo {

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("采购单号")
    private String code;

    @NotNull(message = "供货单位编码不能为空")
    @ApiModelProperty("供应单位编码")
    private String supplyCode;

    @NotNull(message = "供货单位名称不能为空")
    @ApiModelProperty("供应单位名称")
    private String supplyName;

    @ApiModelProperty("供应商编码")
    @NotNull(message = "供应商编码不能为空")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    @NotNull(message = "供应商名称不能为空")
    private String supplierName;

    @ApiModelProperty("供应单位账户名称")
    @NotNull(message = "供应单位账户名称不能为空")
    private String accountName;

    @ApiModelProperty("供应单位账户")
    @NotNull(message = "供应单位账户不能为空")
    private String account;

    @ApiModelProperty("物流中心编码")
    @NotNull(message = "物流中心编码不能为空")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    @NotNull(message = "物流中心名称不能为空")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @NotNull(message = "库房编码不能为空")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @NotNull(message = "库房名称不能为空")
    private String warehouseName;

    @ApiModelProperty("采购组编码")
    @NotNull(message = "采购组编码不能为空")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @NotNull(message = "采购组名称不能为空")
    private String purchaseGroupName;

    @ApiModelProperty("合同编号")
    @NotNull(message = "合同编号不能为空")
    private String contractCode;

    @ApiModelProperty("负责人名字")
    @NotNull(message = "负责人名字不能为空")
    private String chargeMan;

    @ApiModelProperty("联系人名称")
    @NotNull(message = "联系人名称不能为空")
    private String linkMan;

    @ApiModelProperty("联系人电话")
    @NotNull(message = "联系人电话不能为空")
    private String linkPhone;

    @ApiModelProperty("预计到货时间")
    @NotNull(message = "预计到货时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expectedTime;

    @ApiModelProperty("有效期")
    @NotNull(message = "有效期时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date effectiveTime;

    @ApiModelProperty("创建人公司编码")
    private String companyCode;

    @ApiModelProperty("创建人公司名称")
    private String companyName;

    @ApiModelProperty("采购总数量")
    @Min(message = "采购总数量不能小于1",value = 1)
    private Long totalNum;

    @ApiModelProperty("含税采购总金额")
    @Min(message = "含税采购总金额不能小于1",value = 1)
    private BigDecimal totalAmount;

    @ApiModelProperty("采购单类型(0,人工创建,1自动创建的直供单,2,自动创建的配送单)")
    @NotNull(message = "采购单类型不能为空")
    private Byte type;

    @ApiModelProperty("采购单来源(0,采购模块新建,1,订单模块自动创建)")
    private Byte origin;

    @ApiModelProperty("采购单状态(0,待提交,1待审核,2,审核中,3,待供应商确认,4,待入库,5,已入库(目前不用这个状态),6,完成,7,取消,8,审核不通过)")
    private Byte purchaseStatus;

    @ApiModelProperty("销售单位采购总数量")
    private Long saleUnitTotalNum;

    @ApiModelProperty("不含税采购总金额")
    private BigDecimal noTaxTotalAmount;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("实际到货数量")
    private Long actualNum;

    @ApiModelProperty("实际含税采购总金额")
    private BigDecimal actualAmount;

    @ApiModelProperty("实际到货销售单位数量")
    private Long saleUnitActualNum;

    @ApiModelProperty("实际不含税采购总金额")
    private BigDecimal noTaxActualAmount;

    @ApiModelProperty("采购单商品信息集合")
    @NotNull(message = "采购单商品信息集合不能为空")
    private List<InboundItemReqVo> purchaseItemVos;
}

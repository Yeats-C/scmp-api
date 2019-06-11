package com.aiqin.bms.scmp.api.product.domain.request.returnsupply;

import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.common.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-02-27
 * @time: 17:05
 */
@Data
public class ReturnSupplyReqVo {
    @ApiModelProperty("来源单号")
    @NotEmpty(message = "来源单号不能为空")
    private String code;

    @ApiModelProperty("出库类型")
    @NotEmpty(message = "出库类型不能为空")
    private OutboundTypeEnum outboundTypeCode;

    @ApiModelProperty("商品库存状态")
    @NotEmpty(message = "商品库存状态不能为空")
    private StockStatusEnum stockStatusCode;

    @ApiModelProperty("公司编码")
    @NotEmpty(message = "公司编码不能为空")
    private String companyCode;

    @ApiModelProperty("公司名称")
    @NotEmpty(message = "公司名称不能为空")
    private String companyName;

    @ApiModelProperty("供货单位编码")
    @NotEmpty(message = "供货单位编码不能为空")
    private String supplyCode;

    @ApiModelProperty("供货单位名称")
    @NotEmpty(message = "供货单位不能为空")
    private String supplyName;

    @ApiModelProperty("物流中心编码")
    @NotEmpty(message = "物流中心编码不能为空")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    @NotEmpty(message = "物流中心名称不能为空")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    @NotEmpty(message = "库房编码不能为空")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @NotEmpty(message = "库房名称不能为空")
    private String warehouseName;

    @ApiModelProperty("采购组编码")
    @NotEmpty(message = "采购组编码不能为空")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    @NotEmpty(message = "采购组名称不能为空")
    private String purchaseGroupName;

    @ApiModelProperty("出库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outboundTime;

    @ApiModelProperty("预计到货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date preArrivalTime;

    @ApiModelProperty("预计出库数量")
    private Long preOutboundNum;

    @ApiModelProperty("预计主单位数量")
    private Long preMainUnitNum;

    @ApiModelProperty("预计含税总金额")
    private Long preTaxAmount;

    @ApiModelProperty("预计无税总金额")
    private Long preAmount;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("商品详情vo")
    private List<ReturnSupplyItemReqVo> itemReqVos;
}

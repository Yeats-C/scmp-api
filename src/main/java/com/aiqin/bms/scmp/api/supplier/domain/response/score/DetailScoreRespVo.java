package com.aiqin.bms.scmp.api.supplier.domain.response.score;

import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className DetailScoreRespVo
 * @date 2019/5/23 15:54
 * @description TODO
 */
@ApiModel("详情返回Vo")
@Data
public class DetailScoreRespVo {

    @ApiModelProperty("评分编号")
    private String scoreCode;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("送货及时性")
    private BigDecimal deliveryTimely;

    @ApiModelProperty("退换货及时性")
    private BigDecimal returnTimely;

    @ApiModelProperty("订单满足率")
    private BigDecimal orderFillRate;

    @ApiModelProperty("残损率")
    private BigDecimal damageRate;

    @ApiModelProperty("费用支持")
    private BigDecimal costSupport;

    @ApiModelProperty("活动支持")
    private BigDecimal activitySupport;

    @ApiModelProperty("发票返回及时性")
    private BigDecimal invoiceReturnTimely;

    @ApiModelProperty("部门编码")
    private String departCode;

    @ApiModelProperty("部门名称")
    private String departName;

    @ApiModelProperty("评分姓名")
    private String scorerName;

    @ApiModelProperty("标签使用记录")
    List<DetailTagUseRespVo> tagUseRespVos;
}

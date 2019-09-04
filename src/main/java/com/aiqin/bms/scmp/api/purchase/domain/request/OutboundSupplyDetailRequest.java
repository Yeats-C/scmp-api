package com.aiqin.bms.scmp.api.purchase.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 * 带供应商的详情
 */
@Data
public class OutboundSupplyDetailRequest {

    @ApiModelProperty("商品行号")
    private Long productLineNum;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("渠道单价")
    private Long channelUnitPrice;

    @ApiModelProperty("数量")
    private Long num;

    @ApiModelProperty("实发数量")
    private Long actualDeliverNum;

    @ApiModelProperty(value="0不是，1是赠品")
    private Integer giftType;

    @ApiModelProperty(value="供货单位code")
    private String supplyCode;

    @ApiModelProperty(value="供货单位DL的id")
    private String supplyId;
}

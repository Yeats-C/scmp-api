package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

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
 */
@Data
public class RejectApplyListResponse {

    @ApiModelProperty(value = "退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value = "申请单类型: 0 手动 1自动 ")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value = "选中sku数量")
    @JsonProperty("sum_sku")
    private Integer sumSku;

    @ApiModelProperty(value = "单品数量")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value = "商品含税金额")
    @JsonProperty("sum_amount")
    private BigDecimal sumAmount;

    @ApiModelProperty(value = "实物返商品含税金额")
    @JsonProperty("return_amount")
    private BigDecimal returnAmount;

}

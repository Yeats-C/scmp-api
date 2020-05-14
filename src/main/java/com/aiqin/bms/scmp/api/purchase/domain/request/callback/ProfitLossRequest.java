package com.aiqin.bms.scmp.api.purchase.domain.request.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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
public class ProfitLossRequest {
    @ApiModelProperty("订单号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("订单类型(0:报损 1:报益)")
    @JsonProperty("order_type")
    private Integer orderType;

    @ApiModelProperty("仓库编号")
    @JsonProperty("logistics_center_code")
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    @JsonProperty("logistics_center_name")
    private String logisticsCenterName;

    @ApiModelProperty("库房编号")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty("数量")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty("备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty("审核确认时间")
    @JsonProperty("approve_time")
    private String approveTime;

    @ApiModelProperty("创建时间")
    @JsonProperty("create_time")
    private String createTime ;

    @ApiModelProperty("创建人")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty("修改人")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty("创建人")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty("修改人")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty("损溢类别:指定损溢、盘点损溢")
    @JsonProperty("prof_loss_type")
    private String profLossType;

    @ApiModelProperty("损溢商品信息")
    @JsonProperty("detail_list")
    private List<ProfitLossDetailRequest> detailList;

}

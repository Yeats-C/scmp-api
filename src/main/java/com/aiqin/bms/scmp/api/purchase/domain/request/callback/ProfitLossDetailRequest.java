package com.aiqin.bms.scmp.api.purchase.domain.request.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
public class ProfitLossDetailRequest {
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

    @ApiModelProperty("订单编号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty("sku编号")
    @JsonProperty("sku_code")
    private String skuCode;

    @ApiModelProperty("数量")
    @JsonProperty("quantity")
    private Long quantity;

    @ApiModelProperty("行号")
    @JsonProperty("line_num")
    private Long lineNum;

    @ApiModelProperty("原因")
    @JsonProperty("reason")
    private String reason;

    @ApiModelProperty("供应商code")
    @JsonProperty("supply_code")
    private String supplyCode;

    @ApiModelProperty("批次号")
    @JsonProperty("batch_code")
    private String batchCode;

    @ApiModelProperty("损溢类型编号（1 增加库存，2 减少库存）")
    @JsonProperty("loss_order_code")
    private String lossOrderCode;

    @ApiModelProperty("损溢类型名称（1-报溢、2-报损）")
    @JsonProperty("loss_order_name")
    private String lossOrderName;

    /**                        */
    @ApiModelProperty("sku名称")
    @JsonProperty("sku_name")
    private String skuName;

    @ApiModelProperty("品类")
    @JsonProperty("category")
    private String category;

    @ApiModelProperty("品牌")
    @JsonProperty("brand")
    private String brand;

    @ApiModelProperty("颜色")
    @JsonProperty("color")
    private String color;

    @ApiModelProperty("规格")
    @JsonProperty("specification")
    private String specification;

    @ApiModelProperty("型号")
    @JsonProperty("model")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    @JsonProperty("unit")
    private String unit;

    @ApiModelProperty("类别")
    @JsonProperty("classes")
    private String classes;

    @ApiModelProperty("类型")
    @JsonProperty("type")
    private String type;

    @ApiModelProperty("税率")
    @JsonProperty("tax")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    @JsonProperty("tax_price")
    private Long taxPrice;

    @ApiModelProperty("含税总成本")
    @JsonProperty("tax_amount")
    private Long taxAmount;

    @ApiModelProperty("图片地址")
    @JsonProperty("picture_url")
    private String pictureUrl;

    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="修改时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="创建者id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value="修改者id")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value="创建者")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="修改者")
    @JsonProperty("update_by_name")
    private String updateByName;

}

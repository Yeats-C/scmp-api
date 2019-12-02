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
    private String logisticsCenterCode;

    @ApiModelProperty("仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("库房编号")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("订单编号")
    private String orderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("行号")
    private Long lineNum;

    @ApiModelProperty("原因")
    private String reason;

    @ApiModelProperty("供应商code")
    private String supplyCode;
    /**                        */
    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("品类")
    private String category;

    @ApiModelProperty("品牌")
    private String brand;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("规格")
    private String specification;

    @ApiModelProperty("型号")
    private String model;

    @ApiModelProperty("单位(销售单位)")
    private String unit;

    @ApiModelProperty("类别")
    private String classes;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("税率")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    private Long taxPrice;

    @ApiModelProperty("含税总成本")
    private Long taxAmount;

    @ApiModelProperty("图片地址")
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

package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("移库商品表")
@Data
public class MovementProduct extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private String movementCode;

    @ApiModelProperty("")
    private String skuCode;

    @ApiModelProperty("")
    private String skuName;

    @ApiModelProperty("")
    private String category;

    @ApiModelProperty("")
    private String brand;

    @ApiModelProperty("")
    private String color;

    @ApiModelProperty("")
    private String specification;

    @ApiModelProperty("")
    private String model;

    @ApiModelProperty("")
    private String unit;

    @ApiModelProperty("")
    private String classes;

    @ApiModelProperty("")
    private String type;

    @ApiModelProperty("")
    private String inventoryUnit;

    @ApiModelProperty("库存")
    private Long inventory;

    @ApiModelProperty("税率")
    private BigDecimal tax;

    @ApiModelProperty("含税成本")
    private BigDecimal taxPrice;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    private BigDecimal taxAmount;

    @ApiModelProperty("删除标记，0未删除 1已删除")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("")
    private String createBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("")
    private String updateBy;

    @ApiModelProperty("")
    private String pictureUrl;

    @ApiModelProperty("行号")
    private Long linenum;
}
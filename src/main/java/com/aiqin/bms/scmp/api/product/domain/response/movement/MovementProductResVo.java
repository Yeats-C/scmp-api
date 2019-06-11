package com.aiqin.bms.scmp.api.product.domain.response.movement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname: MovementProductResVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Data
public class MovementProductResVo implements Serializable {

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
    private Long tax;

    @ApiModelProperty("含税成本")
    private Long taxPrice;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税总成本")
    private Long taxAmount;

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

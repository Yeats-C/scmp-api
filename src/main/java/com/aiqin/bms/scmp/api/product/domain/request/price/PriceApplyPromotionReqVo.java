package com.aiqin.bms.scmp.api.product.domain.request.price;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 促销申请表
 * PriceApplyPromotion
 * 数据库表：price_apply_promotion
 */
@Data
@ApiModel("促销申请请求vo")
public class PriceApplyPromotionReqVo extends PageReq {

    /**
     *
     * 表字段 : price_apply_promotion.id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 申请促销编号
     * 表字段 : price_apply_promotion.apply_promotion_no
     */
    @ApiModelProperty("申请促销编号")
    private String applyPromotionNo;

    /**
     * 申请促销编号名称
     * 表字段 : price_apply_promotion.apply_promotion_name
     */
    @ApiModelProperty("申请促销名称")
    private String applyPromotionName;

    /**
     *  0:未完成 1：已完成
     * 表字段 : price_apply_promotion.status
     */
    @ApiModelProperty("状态：0:未完成 1：已完成 2:取消")
    private Integer status;

    /**
     * 促销单编号
     * 表字段 : price_apply_promotion.promotion_no
     */
    @ApiModelProperty("促销单编号")
    private String promotionNo;

    /**
     * 促销单名称
     * 表字段 : price_apply_promotion.promotion_name
     */
    @ApiModelProperty("促销单名称")
    private String promotionName;

    /**
     * 创建人
     * 表字段 : price_apply_promotion.create_name
     */
    @ApiModelProperty("创建人")
    private String createName;

    /**
     * 创建时间
     * 表字段 : price_apply_promotion.create_timestamp
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimestamp;

    /**
     * 修改人名称
     * 表字段 : price_apply_promotion.update_name
     */
    @ApiModelProperty("修改人名称")
    private String updateName;

    /**
     * 修改时间
     * 表字段 : price_apply_promotion.update_timestamp
     */
    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimestamp;

    /**
     * 操作人
     * 表字段 : price_apply_promotion.operate_name
     */
    @ApiModelProperty("操作人")
    private String operateName;

    /**
     * 操作时间
     * 表字段 : price_apply_promotion.operate_timestamp
     */
    @ApiModelProperty("操作时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTimestamp;

    /**
     * 采购组编码
     * 表字段 : price_apply_promotion.procurement_section_code
     */
    @ApiModelProperty("采购组编码")
    private String procurementSectionCode;

    /**
     * 采购组名称
     * 表字段 : price_apply_promotion.procurement_section_name
     */
    @ApiModelProperty("采购组名称")
    private String procurementSectionName;

    /**
     * 供货渠道编码
     * 表字段 : price_apply_promotion.categories_supply_channels_code
     */
    @ApiModelProperty("供货渠道编码")
    private String categoriesSupplyChannelsCode;

    /**
     * 供货渠道名称
     * 表字段 : price_apply_promotion.categories_supply_channels_name
     */
    @ApiModelProperty("供货渠道名称")
    private String categoriesSupplyChannelsName;

    /**
     * 备注
     * 表字段 : price_apply_promotion.remark
     */
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("直属上级编码")
    @NotEmpty(message = "直属上级编码不能为空！")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    @NotEmpty(message = "直属上级名称不能为空！")
    private String directSupervisorName;

    //获取当前用户的采购组列表
    List<String> purchaseGroupCodes=new ArrayList<>();

    /**
     * 下属的商品详情
     */
    @ApiModelProperty("下属的商品规则详情")
    @NotEmpty(message = "下属的商品规则详情！")
    private List<PricePromotionDetailReqVo> pricePromotionDetailReqVoList;
}
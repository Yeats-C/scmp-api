package com.aiqin.bms.scmp.api.product.domain.request.price;

/**
 * @Auther: mamingze
 * @Date: 2019-11-05 17:54
 * @Description:
 */

import com.aiqin.bms.scmp.api.base.PageReq;
import com.aiqin.bms.scmp.api.product.domain.response.price.PricePromotionDetailRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 促销单实体
 * PricePromotionProduct
 * 数据库表：price_promotion_product
 */
@Data
@ApiModel("促销单实体返回Vo")
public class PricePromotionReqVo extends PageReq {

    /**
     * 主键id
     * 表字段 : price_apply_promotion.promotion_no
     */
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 促销单编号
     * 表字段 : price_apply_promotion.promotion_no
     */
    @ApiModelProperty("促销单编号")
    private String promotionNo;

    /**
     * 审批单号
     * 表字段 : price_apply_promotion.promotion_no
     */
    @ApiModelProperty("审批单号")
    private String formNo;

    /**
     * 促销单名称
     * 表字段 : price_apply_promotion.promotion_name
     */
    @ApiModelProperty("促销单名称")
    private String promotionName;

    /**
     * 部门
     */
    @ApiModelProperty("部门编号")
    private String departmentCode;

    /**
     * 部门
     */
    @ApiModelProperty("部门名称")
    private String departmentName;

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
     * 状态
     */
    @ApiModelProperty("状态:1.待审核,2.审核中，3.审核通过，4.审核不通过，5.取消")
    private Byte status;

    /**
     * 备注
     * 表字段 : price_apply_promotion.remark
     */
    @ApiModelProperty("备注")
    private String remark;

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
    private Date createTimestamp;

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
    private Date operateTimestamp;


    /**
     * 关联审批编号
     */
    @ApiModelProperty("关联审批编号")
    private String approvalNo ;

    /**
     * 申请促销编号
     * 表字段 : price_apply_promotion.apply_promotion_no
     */
    @ApiModelProperty("申请促销编号")
    private String applyPromotionNo;

    /**
     * 买赠列表
     */
    @ApiModelProperty("买赠列表")
    private List<PricePromotionDetailRespVo> buyPromotionDetailList;

    /**
     * 满赠列表
     */
    @ApiModelProperty("满赠列表")
    private List<PricePromotionDetailRespVo> enoughPromotionDetailList;

    /**
     * 满减列表
     */
    @ApiModelProperty("满减列表")
    private List<PricePromotionDetailRespVo> reducePromotionDetailList;

    /**
     * 满折列表
     */
    @ApiModelProperty("满折列表")
    private List<PricePromotionDetailRespVo> discountPromotionDetailList;



}

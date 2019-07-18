package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuApplyVo
 * @date 2019/7/5 21:08
 * @description TODO
 */
@ApiModel("商品审批查看详情")
@Data
public class ProductSkuApplyVo {

    @ApiModelProperty(value = "申请类型")
    private Byte applyType;

    @ApiModelProperty(value = "申请类型名称")
    private String applyTypeName;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty("商品品牌code")
    private String productBrandCode;

    @ApiModelProperty("商品品牌")
    private String productBrandName;

    @ApiModelProperty("商品品类code")
    private String productCategoryCode;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("所属商品编码")
    private String productCode;

    @ApiModelProperty("所属商品名称")
    private String productName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("申请状态")
    private Integer applyStatus;

    @ApiModelProperty("表单号")
    private String formNo;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty(value = "改变内容",hidden = true)
    private String changeContent;
}

package com.aiqin.bms.scmp.api.product.domain.product.apply;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-15
 * @time: 10:00
 */
@Data
@ApiModel("详情返回列表")
public class ProductApplyInfoRespVO<T> {
    @ApiModelProperty("申请编码")
    private String code;

    @ApiModelProperty("表单号")
    private String formNo;

    @ApiModelProperty("申请人")
    private String applyBy;

    @ApiModelProperty("申请时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditorTime;

    @ApiModelProperty("商品数量")
    private Integer spuNum;

    @ApiModelProperty("sku数量")
    private Integer skuNum;

    @ApiModelProperty("状态")
    private Integer applyStatus;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("列表数据")
    private List<T> data;
}

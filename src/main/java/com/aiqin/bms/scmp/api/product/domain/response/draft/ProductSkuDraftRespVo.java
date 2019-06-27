package com.aiqin.bms.scmp.api.product.domain.response.draft;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuDraftRespVo
 * @date 2019/5/10 09:53
 * @description 商品申请单管理-商品信息数据
 */
@ApiModel("商品申请单管理-商品信息数据")
@Data
public class ProductSkuDraftRespVo {

    @ApiModelProperty(value = "申请类型")
    private Byte applyType;

    @ApiModelProperty(value = "申请类型名称")
    private String applyTypeName;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

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


}

package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("SPU")
@Data
public class NewProduct extends CommonBean {
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("条形码")
    private String barCode;

    @ApiModelProperty("商品id")
    private String productId;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("价格")
    private Integer price;

    @ApiModelProperty("spu code")
    private String spuCode;

    @ApiModelProperty("零售价格")
    private Integer retailPrice;

    @ApiModelProperty("列表图")
    private String logo;

    @ApiModelProperty("封面图")
    private String images;

    @ApiModelProperty("详情图")
    private String itroImages;

    @ApiModelProperty("商品分类id")
    private String categoryId;

    @ApiModelProperty("商品分类名称")
    private String categoryName;

    @ApiModelProperty("品牌类型id")
    private String brandId;

    @ApiModelProperty("品牌类型名称")
    private String brandName;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("申请状态(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte applyStatus;

    @ApiModelProperty("申请商品编码")
    private String applyProductCode;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("申请时间起始时间")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("申请结束时间")
    private Date selectionEffectiveEndTime;

    @ApiModelProperty("(0:不撤销,1:撤销)")
    private Byte priceRevoke;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("采购组编号")
    private String purchasingGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchasingGroupName;

    @ApiModelProperty("款号")
    private String styleNumber;

    @ApiModelProperty("简称")
    private String abbreviation;
}
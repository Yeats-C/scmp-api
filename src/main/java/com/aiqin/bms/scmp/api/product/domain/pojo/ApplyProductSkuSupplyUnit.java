package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("")
@Data
public class ApplyProductSkuSupplyUnit extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供货单位code")
    private String supplyUnitCode;

    @ApiModelProperty("供货单位名称")
    private String supplyUnitName;

    @ApiModelProperty("无税进价")
    private Long noTaxPurchasePrice;

    @ApiModelProperty("含税进价")
    private Long taxIncludedPrice;

    @ApiModelProperty("联营扣率")
    private Long jointFranchiseRate;

    @ApiModelProperty("返点")
    private Long point;

    @ApiModelProperty("税率")
    private Long taxRate;

    @ApiModelProperty("厂商SKU编码")
    private String factorySkuCode;

    @ApiModelProperty("是否缺省（0:否,1：是）")
    private Byte isDefault;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("0:未用 1:在用")
    private Byte usageStatus;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("供货渠道类别编码")
    private String categoriesSupplyChannelsCode;

    @ApiModelProperty("供货渠道类别名称")
    private String categoriesSupplyChannelsName;

    @ApiModelProperty("是否生效(0未生效1已生效）")
    private Integer beEffective;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("审批编码")
    private String formNo;

    @ApiModelProperty("申请状态(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte auditorStatus;

    @ApiModelProperty("申请类型(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte applyType;

    @ApiModelProperty("申请类型(0:待审 1:审核中 2:审核通过 3:审核未通过 4:已撤销)")
    private Byte applyShow;

    @ApiModelProperty("公司名称")
    private String companyCode;

    @ApiModelProperty("公司编码")
    private String companyName;

}
package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("")
@Data
public class ApplyProductSkuDisInfo extends CommonBean {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("商品编号")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("规格")
    private String spec;

    @ApiModelProperty("单位code")
    private String unitCode;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("配送码")
    private String deliveryCode;

    @ApiModelProperty("基商品含量")
    private Integer baseProductContent;

    @ApiModelProperty("拆零系数")
    private Long zeroRemovalCoefficient;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("商品sku 编码")
    private String productSkuCode;

    @ApiModelProperty("商品sku 名称")
    private String productSkuName;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("最大订货数量")
    private Integer maxOrderNum;
}
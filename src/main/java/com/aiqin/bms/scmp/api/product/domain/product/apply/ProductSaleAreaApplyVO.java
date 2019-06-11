package com.aiqin.bms.scmp.api.product.domain.product.apply;

import com.aiqin.bms.scmp.api.product.domain.response.salearea.ProductSaleAreaInfoRespVO;
import com.aiqin.bms.scmp.api.product.domain.response.salearea.QueryProductSaleAreaRespVO;
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
 * @time: 10:15
 */
@Data
@ApiModel("销售区域申请详情信息")
public class ProductSaleAreaApplyVO {

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("是否禁用(0禁用1启用)")
    private Integer beDisable;

    @ApiModelProperty("申请类型(1新增2修改)")
    private Integer applyType;

    @ApiModelProperty("申请状态")
    private Integer applyStatus;

    @ApiModelProperty("表单号")
    private String formNo;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("审核人")
    private String auditorBy;

    @ApiModelProperty("审核时间")
    private Date auditorTime;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效开始时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date selectionEffectiveStartTime;

    @ApiModelProperty("sku信息")
    private List<QueryProductSaleAreaRespVO> skuList;

    @ApiModelProperty("区域信息")
    private List<ProductSaleAreaInfoRespVO> areaList;
}

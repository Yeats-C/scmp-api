package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.product.domain.response.newproduct.ApplyProductDetailsResponseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/21 0021 14:00
 */
@Data
@ApiModel("商品申请详情返回")
public class ApplySkuDetailResp {
    @ApiModelProperty("申请人")
    private String createBy;

    @ApiModelProperty("申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("申请编码")
    private String applyCode;

    @ApiModelProperty("流程编码")
    private String formNo;

    @ApiModelProperty("是否使用生效时间(0:是1:否)")
    private Byte selectionEffectiveTime;

    @ApiModelProperty("生效起始时间")
    private String selectionEffectiveStartTime;

    @ApiModelProperty("生效结束时间")
    private String selectionEffectiveEndTime;

    @ApiModelProperty("申请详情商品列表")
    List<ApplyProductDetailsResponseVO> applyDetailProductListResps;

    @ApiModelProperty("申请详情sku列表")
    List<ApplyDetailSkuListResp> applyDetailSkuListResps;
}

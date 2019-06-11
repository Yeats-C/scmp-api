package com.aiqin.bms.scmp.api.supplier.domain.response.skuconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/2/20 0020 17:46
 */
@Data
@ApiModel("sku配置申请详情返回")
public class ApplySkuConfigDetailResp {
    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("进货状态")
    private String purchaseStatusName;

    @ApiModelProperty("销售状态")
    private String saleStatusName;

    @ApiModelProperty("进货状态值")
    private Byte purchaseStatus;

    @ApiModelProperty("销售状态值")
    private Byte saleStatus;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("商品编码")
    private String productCode;

    @ApiModelProperty("商品名称")
    private String productName;

    @ApiModelProperty("商品类别名称")
    private String productSortName;

    @ApiModelProperty("商品品类名称")
    private String productCategoryName;

    @ApiModelProperty("商品品牌名称")
    private String productBrandName;

    @ApiModelProperty("申请人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("申请时间")
    private Date createTime;

    @ApiModelProperty("流程编码")
    private String formNo;


}

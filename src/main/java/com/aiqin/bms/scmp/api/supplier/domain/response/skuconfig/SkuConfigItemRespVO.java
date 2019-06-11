package com.aiqin.bms.scmp.api.supplier.domain.response.skuconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/18 0018 15:56
 */
@Data
@ApiModel("sku配置列表返回项")
public class SkuConfigItemRespVO {

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("进货状态")
    private String purchaseStatusName;

    @ApiModelProperty("销售状态")
    private String saleStatusName;

    @ApiModelProperty("sku编码")
    private String skuCode;

}

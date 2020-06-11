package com.aiqin.bms.scmp.api.purchase.domain.response.returngoods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-06-19
 * @time: 18:56
 */
@Data
@ApiModel("入库单返回vo")
public class ReturnOrderInfoApplyInboundRespVO {

    @ApiModelProperty("入库申请单号")
    private String code;

    @ApiModelProperty("入库时间")
    private Date inboundTime;

    @ApiModelProperty("状态1新建2完成")
    private Integer status;

    @ApiModelProperty("仓库名称")
    private String warehouseName;

    @ApiModelProperty("批次信息")
    private List<ReturnOrderInboundBatchResponse> detailList;
}

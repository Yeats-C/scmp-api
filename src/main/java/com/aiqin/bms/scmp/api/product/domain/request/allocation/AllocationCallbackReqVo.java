package com.aiqin.bms.scmp.api.product.domain.request.allocation;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by 爱亲 on 2019/8/12.
 */
@Data
@ApiModel("移库回传vo")
public class AllocationCallbackReqVo {

    @ApiModelProperty("出库单编号")
    private String sourceOderCode;

    @ApiModelProperty("出库库房编码")
    private String callOutWarehouseCode;

    @ApiModelProperty("出库库房名称")
    private String callOutWarehouseName;

    @ApiModelProperty("入库库房编码")
    private String callInWarehouseCode;

    @ApiModelProperty("入库库房名称")
    private String callInWarehouseName;

    @ApiModelProperty("实际主单位数量")
    private Long praMainUnitNum;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private List<AllocationProductCallbackReqVo> list;
}
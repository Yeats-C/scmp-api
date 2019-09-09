package com.aiqin.bms.scmp.api.product.domain.pojo;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by 爱亲 on 2019/6/20.
 */
@ApiModel("出库批次详情表")
@Data
public class OutboundBatch extends PagesRequest {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("批次号")
    private String outboundBatchCode;

    @ApiModelProperty("生产日期")
    private Date manufactureTime;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("预计数量")
    private Long preQty;

    @ApiModelProperty("实际数量")
    private Long praQty;

    @ApiModelProperty("库位号")
    private String storeHouseCode;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("行号")
    private Long lineNum;

    @ApiModelProperty("供应商code")
    private String supplyCode;

    @ApiModelProperty("供应商name")
    private String supplyName;

    public OutboundBatch(String outboundOderCode, Integer pageSize, Integer pageNo) {
        this.outboundOderCode = outboundOderCode;
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
    }

    public OutboundBatch() {
    }
}
package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by 爱亲 on 2019/6/20.
 */
@ApiModel("采购入库批次请求参数")
@Data
public class InboundBatchPurchaseReq extends PagesRequest{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("批次号")
    private String inboundBatchCode;

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

    @ApiModelProperty("采购调用次数")
    private Integer purchaseNum;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("行号")
    private Long linenum;

    @ApiModelProperty("来源单号")
    private String sourceOderCode;

    public InboundBatchPurchaseReq(String sourceOderCode, Integer purchaseNum, Integer pageSize, Integer pageNo) {
        this.sourceOderCode = sourceOderCode;
        this.purchaseNum = purchaseNum;
        this.setPageSize(pageSize);
        this.setPageNo(pageNo);
    }

    public InboundBatchPurchaseReq() {
    }
}
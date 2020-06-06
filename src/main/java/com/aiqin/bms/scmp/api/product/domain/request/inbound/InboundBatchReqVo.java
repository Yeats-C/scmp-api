package com.aiqin.bms.scmp.api.product.domain.request.inbound;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by 爱亲 on 2019/6/20.
 */
@ApiModel("入库批次详情表")
@Data
public class InboundBatchReqVo extends PagesRequest{

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("入库单号")
    private String inboundOderCode;

    @ApiModelProperty("批次号/wms批次号")
    private String batchCode;

    @ApiModelProperty("批次编号")
    private String batchInfoCode;

    @ApiModelProperty("sku编号")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("批次备注")
    private String batchRemark;

    @ApiModelProperty("生产日期")
    private String productDate;

    @ApiModelProperty("过期日期")
    private String beOverdueDate;

    @ApiModelProperty("最小单位数量")
    private Long totalCount;

    @ApiModelProperty("实际最小单位数量")
    private Long actualTotalCount;

    @ApiModelProperty("库位号")
    private String locationCode;

    @ApiModelProperty("行号")
    private Long lineCode;

    @ApiModelProperty("创建人id")
    private String createById;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新人id")
    private String updateById;

    @ApiModelProperty("更新人")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("供应商Code")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    public InboundBatchReqVo(String inboundOderCode, Integer pageSize, Integer pageNo) {
        this.inboundOderCode = inboundOderCode;
        super.setPageSize(pageSize);
        super.setPageNo(pageNo);
    }

    public InboundBatchReqVo() {
    }
}
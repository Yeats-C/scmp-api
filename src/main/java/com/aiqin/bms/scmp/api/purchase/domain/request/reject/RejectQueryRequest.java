package com.aiqin.bms.scmp.api.purchase.domain.request.reject;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * ━━━━━━神兽出没━━━━━━
 * 　　┏┓　　　┏┓+ +
 * 　┏┛┻━━━┛┻┓ + +
 * 　┃　　　　　　　┃
 * 　┃　　　━　　　┃ ++ + + +
 * ████━████ ┃+
 * 　┃　　　　　　　┃ +
 * 　┃　　　┻　　　┃
 * 　┃　　　　　　　┃
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃                  神兽保佑, 永无BUG!
 * 　　　┃　　　┃
 * 　　　┃　　　┃     Code is far away from bug with the animal protecting
 * 　　　┃　 　　┗━━━┓
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * <p>
 * 思维方式*热情*能力
 */
@Data
@ApiModel("退供单查询类")
public class RejectQueryRequest extends PagesRequest {
    @ApiModelProperty(value="创建开始时间")
    @JsonProperty("create_begin_time")
    private String createBeginTime;

    @ApiModelProperty(value="创建结束时间")
    @JsonProperty("create_finish_time")
    private String createFinishTime;

    @ApiModelProperty(value="修改开始时间")
    @JsonProperty("update_begin_time")
    private String updateBeginTime;

    @ApiModelProperty(value="修改结束时间")
    @JsonProperty("update_finish_time")
    private String updateFinishTime;

    @ApiModelProperty(value="采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="退供单号")
    @JsonProperty("reject_record_code")
    private String rejectRecordCode;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="仓库编码")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value = "库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "退供单状态: 0 查看 1 确认  2 撤销 3 重发")
    @JsonProperty("reject_status")
    private Integer rejectStatus;

    @ApiModelProperty(value="来源类型 0.退供申请 1.退货单")
    @JsonProperty("source_type")
    private Integer sourceType;

    @ApiModelProperty(value="来源单号")
    @JsonProperty("source_code")
    private String sourceCode;

    @ApiModelProperty()
    private List<PurchaseGroupVo> groupList;

    public RejectQueryRequest() {
    }

    public RejectQueryRequest(String createBeginTime, String createFinishTime, String updateBeginTime, String updateFinishTime,
                              String purchaseGroupCode, String rejectRecordCode, String supplierCode, String supplierName,
                              String transportCenterCode, String warehouseCode, Integer rejectStatus, Integer sourceType,
                              String sourceCode) {
        this.createBeginTime = createBeginTime;
        this.createFinishTime = createFinishTime;
        this.updateBeginTime = updateBeginTime;
        this.updateFinishTime = updateFinishTime;
        this.purchaseGroupCode = purchaseGroupCode;
        this.rejectRecordCode = rejectRecordCode;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.transportCenterCode = transportCenterCode;
        this.warehouseCode = warehouseCode;
        this.rejectStatus = rejectStatus;
        this.sourceType = sourceType;
        this.sourceCode = sourceCode;
    }
}

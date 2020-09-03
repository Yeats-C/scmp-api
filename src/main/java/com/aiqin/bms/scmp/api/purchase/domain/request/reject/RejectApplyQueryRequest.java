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
@ApiModel("退供申请单查询请求类")
public class RejectApplyQueryRequest extends PagesRequest {

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

    @ApiModelProperty(value="退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value="供应商编码")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value="供应商名称")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value="申请单类型: 0 手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value="退供申请单状态: 0.待提交 1.待审核 2.审核中 3.审核通过 4.审核不通过 5.撤销")
    @JsonProperty("apply_record_status")
    private Integer applyRecordStatus;

    @ApiModelProperty(value="库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value="不需要传的参数")
    private List<PurchaseGroupVo> groupList;

    public RejectApplyQueryRequest(String createBeginTime, String createFinishTime, String updateBeginTime, String updateFinishTime,
                                   String purchaseGroupCode, String rejectApplyRecordCode, String supplierCode, String supplierName,
                                   Integer applyType, Integer applyRecordStatus) {
        this.createBeginTime = createBeginTime;
        this.createFinishTime = createFinishTime;
        this.updateBeginTime = updateBeginTime;
        this.updateFinishTime = updateFinishTime;
        this.purchaseGroupCode = purchaseGroupCode;
        this.rejectApplyRecordCode = rejectApplyRecordCode;
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.applyType = applyType;
        this.applyRecordStatus = applyRecordStatus;
    }

    public RejectApplyQueryRequest() {
    }
}

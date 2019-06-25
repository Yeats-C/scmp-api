package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.base.PagesRequest;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
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

    @ApiModelProperty(value="退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value="申请单类型: 0 手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value="采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value="退供申请单状态: 0  已完成 1 待提交")
    @JsonProperty("apply_record_status")
    private Integer applyRecordStatus;

    @ApiModelProperty(value="开始时间")
    @JsonProperty("begin_time")
    private String beginTime;

    @ApiModelProperty(value="结束时间")
    @JsonProperty("finish_time")
    private String finishTime;

    @ApiModelProperty(value="不需要传的参数")
    private List<PurchaseGroupVo> groupList;

    public RejectApplyQueryRequest(String rejectApplyRecordCode, Integer applyType, String purchaseGroupCode, Integer applyRecordStatus, String beginTime, String finishTime) {
        this.rejectApplyRecordCode = rejectApplyRecordCode;
        this.applyType = applyType;
        this.purchaseGroupCode = purchaseGroupCode;
        this.applyRecordStatus = applyRecordStatus;
        this.beginTime = beginTime;
        this.finishTime = finishTime;
    }

    public RejectApplyQueryRequest() {
    }
}

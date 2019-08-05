package com.aiqin.bms.scmp.api.purchase.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class RejectApplyQueryResponse {

    @ApiModelProperty(value="退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value="申请单类型: 0 手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value="采购组")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value="退供申请单状态: 0  已完成 1 待提交")
    @JsonProperty("apply_record_status")
    private Integer applyRecordStatus;

    @ApiModelProperty(value="总sku数量")
    @JsonProperty("sum_sku")
    private Integer sumSku;

    @ApiModelProperty(value="总退供数量")
    @JsonProperty("sum_count")
    private Integer sumCount;

    @ApiModelProperty(value="商品含税金额")
    @JsonProperty("sum_amount")
    private Long sumAmount;

    @ApiModelProperty(value="实物返含税金额")
    @JsonProperty("sum_return_amount")
    private Long sumReturnAmount;

    @ApiModelProperty(value="赠品含税金额")
    @JsonProperty("sum_gift_amount")
    private Long sumGiftAmount;

    @ApiModelProperty(value="创建人")
    @JsonProperty("create_by_name")
    private String createByName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="创建时间")
    @JsonProperty("create_time")
    private Date createTime;

    @ApiModelProperty(value="编辑人")
    @JsonProperty("update_by_name")
    private String updateByName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="编辑时间")
    @JsonProperty("update_time")
    private Date updateTime;

    @ApiModelProperty(value="商品批次列表")
    @JsonProperty("detail_list")
    private List<RejectApplyDetailResponse> detailList;

    @ApiModelProperty(value="是否可编辑,根据是否有提交过的商品  0否  1是")
    @JsonProperty("update_status")
    private Integer updateStatus;

}

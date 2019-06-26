package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.RejectApplyRecordDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RejectApplyRequest {

    @ApiModelProperty(value = "退货申请单号")
    @JsonProperty("reject_apply_record_code")
    private String rejectApplyRecordCode;

    @ApiModelProperty(value = "创建人id")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "创建人名称")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value = "修改人id")
    @JsonProperty("update_by_id")
    private String updateById;

    @ApiModelProperty(value = "修改人名称")
    @JsonProperty("update_by_name")
    private String updateByName;

    @ApiModelProperty(value = "商品 结算方式")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value = "供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "仓编码(物流中心编码)")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value = "库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value = "采购组 ")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty("创建人公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("创建人公司名称")
    @JsonProperty("company_name")
    private String companyName;

    @ApiModelProperty(value = "申请单类型:0手动 1自动")
    @JsonProperty("apply_type")
    private Integer applyType;

    @ApiModelProperty(value = "商品列表")
    @JsonProperty("detail_list")
    private List<RejectApplyRecordDetail> detailList;

    @ApiModelProperty(value = "退货申请单号集合(生成退供单使用)")
    @JsonProperty("reject_apply_record_codes")
    private List<String> rejectApplyRecordCodes;

}

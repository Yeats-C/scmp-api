package com.aiqin.bms.scmp.api.purchase.domain.request;

import com.aiqin.bms.scmp.api.purchase.domain.FileRecord;
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
@ApiModel("退供单请求类")
public class RejectRequest {

    @ApiModelProperty(value = "退货申请单号集合(生成退供单使用)  --")
    @JsonProperty("reject_apply_record_codes")
    private List<String> rejectApplyRecordCodes;

    @ApiModelProperty(value = "商品 结算方式")
    @JsonProperty("settlement_method_code")
    private String settlementMethodCode;

    @ApiModelProperty(value = "商品 结算方式")
    @JsonProperty("settlement_method_name")
    private String settlementMethodName;

    @ApiModelProperty(value = "供应商code")
    @JsonProperty("supplier_code")
    private String supplierCode;

    @ApiModelProperty(value = "供应商")
    @JsonProperty("supplier_name")
    private String supplierName;

    @ApiModelProperty(value = "仓编码(物流中心编码)")
    @JsonProperty("transport_center_code")
    private String transportCenterCode;

    @ApiModelProperty(value = "仓名称(物流中心名称)")
    @JsonProperty("transport_center_name")
    private String transportCenterName;

    @ApiModelProperty(value = "库房编码")
    @JsonProperty("warehouse_code")
    private String warehouseCode;

    @ApiModelProperty(value = "库房名称")
    @JsonProperty("warehouse_name")
    private String warehouseName;

    @ApiModelProperty(value = "采购组 code")
    @JsonProperty("purchase_group_code")
    private String purchaseGroupCode;

    @ApiModelProperty(value = "采购组")
    @JsonProperty("purchase_group_name")
    private String purchaseGroupName;

    @ApiModelProperty(value = "数量 --")
    @JsonProperty("sum_count")
    private Integer sumCount;

    @ApiModelProperty(value = "退供总金额 --")
    @JsonProperty("sum_amount")
    private Long sumAmount;

    @ApiModelProperty(value = "单品数量 --")
    @JsonProperty("single_count")
    private Integer singleCount;

    @ApiModelProperty(value = "单品金额 --")
    @JsonProperty("untaxed_amount")
    private Long untaxedAmount;

    @ApiModelProperty(value = "实物返回数量 --")
    @JsonProperty("return_count")
    private Integer returnCount;

    @ApiModelProperty(value = "实物返回金额 --")
    @JsonProperty("return_amount")
    private Long returnAmount;

    @ApiModelProperty(value = "负责人")
    @JsonProperty("duty_person")
    private String dutyPerson;

    @ApiModelProperty(value = "联系人")
    @JsonProperty("contacts_person")
    private String contactsPerson;

    @ApiModelProperty(value = "联系人电话")
    @JsonProperty("contacts_person_phone")
    private String contactsPersonPhone;

    @ApiModelProperty(value = "收货区域 :省")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value = "")
    @JsonProperty("province_name")
    private String provinceName;

    @ApiModelProperty(value = "市")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value = "")
    @JsonProperty("city_name")
    private String cityName;

    @ApiModelProperty(value = "县")
    @JsonProperty("district_id")
    private String districtId;

    @ApiModelProperty(value = "")
    @JsonProperty("district_name")
    private String districtName;

    @ApiModelProperty(value = "收货地址")
    @JsonProperty("address")
    private String address;

    @ApiModelProperty(value = "预计配送时间")
    @JsonProperty("expect_time")
    private String expectTime;

    @ApiModelProperty(value = "有效期")
    @JsonProperty("valid_day")
    private String validDay;

    @ApiModelProperty(value = "直属上级id (取字典表数据)")
    @JsonProperty("dictionary_id")
    private String dictionaryId;

    @ApiModelProperty(value = "")
    @JsonProperty("dictionary_name")
    private String dictionaryName;

    @ApiModelProperty(value = "")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "")
    @JsonProperty("create_by_id")
    private String createById;

    @ApiModelProperty(value = "")
    @JsonProperty("create_by_name")
    private String createByName;

    @ApiModelProperty(value="文件list")
    @JsonProperty("file_list")
    List<FileRecord> fileList;

    @ApiModelProperty("创建人公司编码")
    @JsonProperty("company_code")
    private String companyCode;

    @ApiModelProperty("创建人公司名称")
    @JsonProperty("company_name")
    private String companyName;

}


package com.aiqin.bms.scmp.api.account.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

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
public class PersonRequest {

    @ApiModelProperty(value = "人员编码")
    @JsonProperty(value = "person_id")
    private String personId;

    @ApiModelProperty(value = "人员名称")
    private String name;

    @ApiModelProperty(value = "员工唯一标识ID")
    @JsonProperty(value = "user_id")
    private String userId;

    @ApiModelProperty(value = "性别1男2女")
    private Integer gender;

    @ApiModelProperty(value = "1身份证2护照3军官证4驾驶证5其他")
    @JsonProperty(value = "card_type")
    private Integer cardType;

    @ApiModelProperty(value = "证件号码")
    @JsonProperty(value = "card_no")
    private String cardNo;

    @ApiModelProperty(value = "移动电话")
    private String mobile;

    @ApiModelProperty(value = "移动电话类型")
    @JsonProperty(value = "mobile_type")
    private String mobileType;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "人员状态")
    @JsonProperty(value = "person_status")
    private Integer personStatus;

    @ApiModelProperty(value = "人员类型")
    @JsonProperty(value = "person_type")
    private Integer personType;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonProperty(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "创建人")
    @JsonProperty(value = "create_by")
    private String createBy;

    @ApiModelProperty(value = "更新人")
    @JsonProperty(value = "update_by")
    private String updateBy;

}

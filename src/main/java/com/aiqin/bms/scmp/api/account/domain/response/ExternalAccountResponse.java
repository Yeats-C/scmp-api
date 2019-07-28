package com.aiqin.bms.scmp.api.account.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class ExternalAccountResponse {

    @ApiModelProperty(value = "部门id")
    @JsonProperty("department_code")
    private String departmentCode;

    @ApiModelProperty(value = "部门名称")
    @JsonProperty("department_name")
    private String departmentName;

    @ApiModelProperty(value = "岗位id")
    @JsonProperty("position_code")
    private String positionCode;

    @ApiModelProperty(value = "岗位名称")
    @JsonProperty("position_name")
    private String positionName;

    @ApiModelProperty(value="账号id")
    @JsonProperty("account_id")
    private String accountId;

}

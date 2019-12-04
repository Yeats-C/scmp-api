package com.aiqin.bms.scmp.api.product.domain.response;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author knight.xie
 * @className QueryPositionReqVo
 * @date 2019/12/3 16:12
 */
@Data
@ApiModel("查询职位信息请求VO")
public class PositionRespVo implements Serializable {

    // private static final long serialVersionUID = -447331905703381983L;
    //"id":1713,"username":"0911182","account_id":"c0ee64bff2e54c639447d458f08935f2","person_id":"11182","person_name":"徐学浩",
    // "position_code":"GW0820","position_name":"采购总监","company_code":"09","company_name":"宁波熙耘科技有限公司",
    // "department_code":"090400004","department_name":"供应链中心管理办公室","user_position_status":0,"begin_time":"2019-09-01 00:00:00",
    // "finish_time":"2035-01-01 00:00:00","create_time":"2019-08-13 21:09:34","position_level_code":"002","position_level_name":"总监"
    @JsonProperty("id")
    private String id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("person_id")
    private String personId;

    @JsonProperty("person_name")
    private String personName;

    @JsonProperty("position_code")
    private String positionCode;

    @JsonProperty("position_name")
    private String positionName;

    @JsonProperty("company_code")
    private String companyCode;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("department_code")
    private String departmentCode;

    @JsonProperty("department_name")
    private String departmentName;

    @JsonProperty("user_position_status")
    private String userPositionStatus;

    @JsonProperty("position_level_code")
    private String positionLevelCode;

    @JsonProperty("position_level_name")
    private String positionLevelName;


}

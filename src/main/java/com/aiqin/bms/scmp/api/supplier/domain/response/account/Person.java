package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author boyd
 * @since 2018-09-05
 */
@Data
@ApiModel(value = "人员")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;
    /**
     * 员工编码
     */
    @ApiModelProperty(value = "人员编码")
    @JsonProperty(value = "person_id")
    private String personId;
    /**
     *
     */
    @ApiModelProperty(value = "人员名称")
    private String name;

    @ApiModelProperty(value = "员工唯一标识ID")
    @JsonProperty(value = "user_id")
    private String userId;
    /**
     * 1男2女
     */
    @ApiModelProperty(value = "性别1男2女")
    private Integer gender;
    /**
     * 1身份证2护照3军官证4驾驶证5其他
     */
    @ApiModelProperty(value = "1身份证2护照3军官证4驾驶证5其他")
    @JsonProperty(value = "card_type")
    private Integer cardType;
    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @JsonProperty(value = "card_no")
    private String cardNo;
    /**
     * 移动电话
     */
    @ApiModelProperty(value = "移动电话")
    private String mobile;
    /**
     *
     */
    @ApiModelProperty(value = "移动电话类型")
    @JsonProperty(value = "mobile_type")
    private String mobileType;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     *
     */
    @ApiModelProperty(value = "人员状态")
    @JsonProperty(value = "person_status")
    private Integer personStatus;


    @ApiModelProperty(value = "人员类型")
    @JsonProperty(value = "person_type")
    private Integer personType;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonProperty(value = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonProperty(value = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @JsonProperty(value = "create_by")
    private String createBy;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @JsonProperty(value = "update_by")
    private String updateBy;

    private List<Account> accounts;

    private String companys;

}

package com.aiqin.bms.scmp.api.supplier.domain.response.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "岗位")
public class Positions {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Long id;
    /**
     *
     */
    @ApiModelProperty(value = "岗位编码")
    @JsonProperty(value = "position_code")
    private String positionCode;
    /**
     * 职位名称
     */
    @ApiModelProperty(value = "岗位名称")
    @JsonProperty(value = "position_name")
    private String positionName;
    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @JsonProperty(value = "company_code")
    private String companyCode;
    /**
     *
     */
    @ApiModelProperty(value = "公司编码")
    @JsonProperty(value = "company_name")
    private String companyName;
    /**
     * 部门编码
     */
    @ApiModelProperty(value = "部门编码")
    @JsonProperty(value = "department_code")
    private String departmentCode;
    /**
     *
     */
    @ApiModelProperty(value = "部门名称")
    @JsonProperty(value = "department_name")
    private String departmentName;
    /**
     *
     */
    @ApiModelProperty(value = "生效时间")
    @JsonProperty(value = "begin_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    /**
     *
     */
    @ApiModelProperty(value = "岗位状态")
    @JsonProperty(value = "position_status")
    private Integer positionStatus;
    /**
     *
     */
    @ApiModelProperty(value = "失效时间")
    @JsonProperty(value = "finish_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
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

    private String remark;
    @ApiModelProperty(value = "岗位级别编码")
    @JsonProperty(value = "position_level_code")
    private String positionLevelCode;

    @ApiModelProperty(value = "岗位级别编码")
    @JsonProperty(value = "position_level_name")
    private String positionLevelName;

    @ApiModelProperty(value = "删除标记")
    @JsonProperty(value = "delete_mark")
    private String deleteMark;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public Positions() {
    }

    public Positions(String positionCode) {
        this.positionCode = positionCode;
    }

}

package com.aiqin.bms.scmp.api.supplier.domain.response.tag;

import com.aiqin.bms.scmp.api.common.StatusTypeCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className QueryTagRespVo
 * @date 2019/4/29 14:41
 * @description TODO
 */

@ApiModel("列表查询请求返回")
@Data
public class QueryTagRespVo {

    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("标签编码")
    private String tagCode;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty("标签类型编码")
    private String tagTypeCode;

    @ApiModelProperty("标签类型名称")
    private String tagTypeName;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("启用禁用状态")
    private Byte enable;

    @ApiModelProperty("启用禁用状态名称")
    private String enableName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("标签使用次数")
    private Integer tagUseNum;

    @ApiModelProperty("标签分类")
    private String classify;

    @ApiModelProperty("标签使用记录")
    List<DetailTagUseRespVo> tagUseRespVos;

    public void setEnable(Byte enable) {
        this.enable = enable;
        setEnableName();
    }

    public void setEnableName() {
        if (StatusTypeCode.EN_ABLE.getStatus().equals(this.enable)) {
            this.enableName = StatusTypeCode.EN_ABLE.getName();
        } else {
            this.enableName = StatusTypeCode.DIS_ABLE.getName();
        }
    }


}

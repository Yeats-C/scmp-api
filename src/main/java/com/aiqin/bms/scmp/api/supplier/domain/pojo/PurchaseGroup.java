package com.aiqin.bms.scmp.api.supplier.domain.pojo;


import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("采购组主表")
@Data
public class PurchaseGroup extends CommonBean {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("禁用启用状态，0是禁用，1是未禁用")
    private Byte enable;

    @ApiModelProperty("逻辑删除，0表示未删除，1表示已删除")
    private Byte delFlag;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("排序")
    private Integer purchaseGroupOrder;
}
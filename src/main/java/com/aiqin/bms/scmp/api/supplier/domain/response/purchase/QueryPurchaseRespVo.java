package com.aiqin.bms.scmp.api.supplier.domain.response.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author HuangLong
 * @date 2018/12/24
 */
@ApiModel("采购列表list返回vo")
@Data
public class QueryPurchaseRespVo {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("采购单编码")
    private String code;

    @ApiModelProperty("供应单位编码")
    private String supplyCode;

    @ApiModelProperty("供应单位名称")
    private String supplyName;

    @ApiModelProperty("供应商编码")
    private String supplierCode;

    @ApiModelProperty("供应商名称")
    private String supplierName;

    @ApiModelProperty("供应单位账户名称")
    private String accountName;

    @ApiModelProperty("供应单位账户")
    private String account;

    @ApiModelProperty("物流中心编码")
    private String transportCenterCode;

    @ApiModelProperty("物流中心名称")
    private String transportCenterName;

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("合同编号")
    private String contractCode;

    @ApiModelProperty("负责人名字")
    private String chargeMan;

    @ApiModelProperty("联系人名称")
    private String linkMan;

    @ApiModelProperty("联系人电话")
    private String linkPhone;

    @ApiModelProperty("预计到货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expectedTime;

    @ApiModelProperty("有效期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date effectiveTime;

    @ApiModelProperty("创建人公司编码")
    private String companyCode;

    @ApiModelProperty("创建人公司名称")
    private String companyName;

    @ApiModelProperty("采购总数量")
    private Long totalNum;

    @ApiModelProperty("含税采购总金额")
    private Long totalAmount;

    @ApiModelProperty("实际到货数量")
    private Long actualNum;

    @ApiModelProperty("实际含税采购总金额")
    private Long actualAmount;

    @ApiModelProperty("采购单状态(0,待提交,1待审核,2,审核中,3,待供应商确认,4,待入库,5,已入库(目前不用这个状态),6,完成,7,取消,8,审核不通过)")
    private Byte purchaseStatus;

    @ApiModelProperty("是否删除(撤销,0,否,1,是)")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("创建人名字")
    private String createBy;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("更新人名字")
    private String updateBy;

    @ApiModelProperty("采购单类型(0,人工创建,1自动创建的直供单,2,自动创建的配送单)")
    private Byte type;

    @ApiModelProperty("采购单来源(0,采购模块新建,1,订单模块自动创建)")
    private Byte origin;

    @ApiModelProperty("订单编号(自动创建的订单才传此字段)")
    private String orderCode;
}

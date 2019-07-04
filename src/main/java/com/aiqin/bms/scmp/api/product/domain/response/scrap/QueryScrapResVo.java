package com.aiqin.bms.scmp.api.product.domain.response.scrap;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Classname: QueryScrapResVo
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@Data
@ApiModel("报废单列表展示返回实体")
public class QueryScrapResVo {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("报废编码")
    private String scrapCode;

    @ApiModelProperty("所属仓库编码")
    private String logisticsCenterCode;

    @ApiModelProperty("所属仓库名称")
    private String logisticsCenterName;

    @ApiModelProperty("所属库房编码")
    private String warehouseCode;

    @ApiModelProperty("所属库房名称")
    private String warehouseName;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("负责人")
    private String principal;

    @ApiModelProperty("数量")
    private Long quantity;

    @ApiModelProperty("含税库存成本，展示时需要除以100")
    private Long taxInventoryCost;

    @ApiModelProperty("出库单号")
    private String outboundOderCode;

    @ApiModelProperty("报废状态编码")
    private Byte scrapStatusCode;

    @ApiModelProperty("报废状态名称")
    private String scrapStatusName;

    @ApiModelProperty("删除标记，0未删除 1已删除")
    private Byte delFlag;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("审批流水编号")
    private String formNo;

    @ApiModelProperty("公司编码")
    private String companyCode;

    @ApiModelProperty("公司名称")
    private String companyName;

}

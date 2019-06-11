package com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:库房列表请求实体
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("库房列表请求实体")
public class QueryWarehouseReqVo extends PageReq {

    @ApiModelProperty("库房编码")
    private String warehouseCode;

    @ApiModelProperty("库房名称")
    private String warehouseName;

    @ApiModelProperty("启用禁用状态")
    private Byte enable;

    @ApiModelProperty("物流中心名称")
    private String logisticsCenterName;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;
}

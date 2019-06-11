package com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname: QueryManufacturerReqVo
 * 描述: 制造商列表查看请求实体
 * @Author: Kt.w
 * @Date: 2019/2/14
 * @Version 1.0
 * @Since 1.0
 */
@ApiModel("制造商列表查看请求实体")
@Data
public class QueryManufacturerReqVo  extends PageReq {
    @ApiModelProperty("制造商编号")
    private String manufacturerCode;

    @ApiModelProperty("制造商名称")
    private String name;

   @ApiModelProperty("启用禁用状态")
    private Byte enable;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填", hidden = true)
    private String companyCode;
}

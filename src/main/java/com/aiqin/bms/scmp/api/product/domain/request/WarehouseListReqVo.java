package com.aiqin.bms.scmp.api.product.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/1/16 0016 10:18
 */
@Data
@ApiModel("地区编码查询仓库请求参数")
public class WarehouseListReqVo {
    @ApiModelProperty("省编码")
    private String provinceCode;

    @ApiModelProperty("市编码")
    private String cityCode;

    @ApiModelProperty("区编码")
    private String countyCode;

    @ApiModelProperty("仓库类型编码 0 大效期库 1 销售库 2 退货库 3 销售库 大效期库 null 查询全部")
    private Integer warehouseTypeCode;

}

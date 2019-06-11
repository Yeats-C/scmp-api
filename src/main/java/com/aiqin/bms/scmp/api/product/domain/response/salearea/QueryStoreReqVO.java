package com.aiqin.bms.scmp.api.product.domain.response.salearea;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-16
 * @time: 15:18
 */
@Data
@ApiModel("门店列表查询")
public class QueryStoreReqVO extends PageReq {

    @ApiModelProperty("查询条件/门店名称或编码")
    private String condition;

    @ApiModelProperty("省id")
    private String provinceId;

    @ApiModelProperty("市id")
    private String cityId;

    @ApiModelProperty("县id")
    private String districtId;
}

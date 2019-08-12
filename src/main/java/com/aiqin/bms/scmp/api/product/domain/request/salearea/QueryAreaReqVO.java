package com.aiqin.bms.scmp.api.product.domain.request.salearea;

import com.aiqin.bms.scmp.api.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-16
 * @time: 10:09
 */
@ApiModel("区域查询vo")
@Data
public class QueryAreaReqVO extends PageReq {
    @ApiModelProperty("区域名称")
    private String areaName;
}

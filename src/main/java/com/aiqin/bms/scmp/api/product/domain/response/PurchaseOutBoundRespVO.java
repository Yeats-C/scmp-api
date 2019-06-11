package com.aiqin.bms.scmp.api.product.domain.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: zth
 * @date: 2019-02-25
 * @time: 14:24
 */
@Data
@ApiModel("退供保存库存流水vo")
public class PurchaseOutBoundRespVO {
    /**
     * 错误信息
     */
    private List<VerifyReturnSupplyErrorRespVo> supplyErrorRespVos;
    /**
     * 出库编码
     */
    private String outBoundCode;
}

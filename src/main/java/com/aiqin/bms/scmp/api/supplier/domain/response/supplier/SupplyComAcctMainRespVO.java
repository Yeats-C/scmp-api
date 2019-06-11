package com.aiqin.bms.scmp.api.supplier.domain.response.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 供货单位详情页面展示vo
 * @author zth
 * @date 2018/12/10
 */
@ApiModel("供货单位账户信息详情")
@Data
public class SupplyComAcctMainRespVO {
    @ApiModelProperty("新供货单位账户信息")
    private SupplyComAcctInfoRespVO newSupplyAccount;

    @ApiModelProperty("旧供货单位账户信息")
    private SupplyComAcctInfoRespVO oldSupplyAccount;

    @ApiModelProperty("日志信息")
    private List<LogData> logData;

}

package com.aiqin.bms.scmp.api.supplier.domain.response;

import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComDetailRespVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApplyComDetailRespVO extends SupplyComDetailRespVO {
    @ApiModelProperty("申请修改时需要展示的供应商编码")
    private String formalCode;
}

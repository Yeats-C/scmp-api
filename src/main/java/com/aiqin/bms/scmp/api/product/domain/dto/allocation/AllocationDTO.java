package com.aiqin.bms.scmp.api.product.domain.dto.allocation;

import com.aiqin.bms.scmp.api.product.domain.pojo.Allocation;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProductBatch;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-07-11
 * @time: 17:03
 */
@ApiModel("调拨dto")
@Data
public class AllocationDTO extends Allocation {
    @ApiModelProperty("商品信息")
    private List<AllocationProduct> products;

    @ApiModelProperty("商品批次信息")
    private List<AllocationProductBatch> list;
}

package com.aiqin.bms.scmp.api.supplier.domain.response.salearea;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-16
 * @time: 16:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("销售区域模糊搜索结果")
public class ProductSaleAreaFuzzySearchRespVO {
    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("类型(1区域2门店")
    private Integer  type;
}

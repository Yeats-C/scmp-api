package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuPicDescRespVo
 * @date 2019/5/15 10:03
 */
@ApiModel("sku商品说明信息返回")
@Data
public class ProductSkuPicDescRespVo extends CommonBean {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("排序号")
    private Long sortingNumber;

    @ApiModelProperty("图片说明路径")
    private String picDescPath;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;
}

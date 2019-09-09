package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuFileRespVO
 * @date 2019/5/15 10:05
 */
@ApiModel("sku文件管理信息返回")
@Data
public class ProductSkuFileRespVO extends CommonBean {
    @ApiModelProperty("主键Id")
    private Long id;

    @ApiModelProperty("文件编码")
    private String fileCode;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;
}

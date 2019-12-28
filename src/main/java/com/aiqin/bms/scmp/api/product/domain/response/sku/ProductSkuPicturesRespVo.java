package com.aiqin.bms.scmp.api.product.domain.response.sku;

import com.aiqin.bms.scmp.api.base.PropertyMsg;
import com.aiqin.bms.scmp.api.common.CommonBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ProductSkuPicturesRespVo
 * @date 2019/5/14 16:06
 */
@ApiModel("sku图片及介绍信息返回")
@Data
public class ProductSkuPicturesRespVo extends CommonBean {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("图片路径")
    @PropertyMsg("图片路径")
    private String productPicturePath;

    @ApiModelProperty("图片名称")
    @PropertyMsg("图片名称")
    private String productPictureName;

    @ApiModelProperty("商品介绍")
    @PropertyMsg("商品介绍")
    private String productIntroduction;

    @ApiModelProperty("商品sku code")
    private String productSkuCode;

    @ApiModelProperty("商品sku名称")
    private String productSkuName;

    @ApiModelProperty("是否主图，0不是主图，1为主图")
    @PropertyMsg(value = "是否主图",replace = "1_是,0_不是")
    private Byte mainPicture;
}

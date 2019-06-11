package com.aiqin.bms.scmp.api.product.domain.response.sku.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/30 0030 14:07
 */
@ApiModel("门店sku图文信息")
@Data
public class StoreSkuPicInfo {
    @ApiModelProperty("图片路径")
    @JsonProperty("picture_path")
    private String picturePath;

    @ApiModelProperty("商品介绍")
    @JsonProperty("product_introduction")
    private String productIntroduction;

    @ApiModelProperty("是否主图,0不是主图，1为主图")
    @JsonProperty("main_picture")
    private Byte mainPicture;
}

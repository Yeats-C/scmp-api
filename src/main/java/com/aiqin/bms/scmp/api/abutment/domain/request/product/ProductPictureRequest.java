package com.aiqin.bms.scmp.api.abutment.domain.request.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("DL- 商品推送图片、图片说明信息")
public class ProductPictureRequest {

    @ApiModelProperty(value="图片路径")
    @JsonProperty("product_picture_path")
    private String productPicturePath;

    @ApiModelProperty(value="图片名称")
    @JsonProperty("product_picture_name")
    private String productPictureName;

}

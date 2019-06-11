package com.aiqin.bms.scmp.api.product.domain.request.brand;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *商品品牌类型DTO
 * @author zth
 * @date 2018-12-13
 * @time: 10:27
 */
@Data
public class QueryProductBrandReqVO extends PageReq {

    @ApiModelProperty(value = "品牌类型code")
    private String brandId;

    @ApiModelProperty(value = "品牌类型名称")
    private String brandName;

    @ApiModelProperty(value = "状态，0为启用，1为禁用")
    private Integer brandStatus;

//    @ApiModelProperty(value = "品牌code")
//    private String brandCode;

    @ApiModelProperty(value = "品牌首字母")
    private String brandInitials;

    @ApiModelProperty(value = "创建时间从")
    @JsonFormat(timezone = "GTM+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateStart;

    @ApiModelProperty(value = "创建时间到")
    @JsonFormat(timezone = "GTM+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDateEnd;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填")
    private String companyCode;

}

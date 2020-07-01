package com.aiqin.bms.scmp.api.product.domain.request.sku;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("sku列表请求条件")
public class QuerySkuListReqVO extends PageReq {

    @ApiModelProperty("商品(SPU)名称")
    private String productName;

    @ApiModelProperty("商品(SPU编码")
    private String productCode;

    @ApiModelProperty("sku编码")
    private String skuCode;

    @ApiModelProperty("sku名称")
    private String skuName;

    @ApiModelProperty("属性名称")
    private String productPropertyName;

    @ApiModelProperty("属性编码")
    private String productPropertyCode;

    @ApiModelProperty("品类名称")
    private String productCategoryName;

    @ApiModelProperty("品类编码")
    private String productCategoryCode;

    @ApiModelProperty("品类集合编码 1级编码 2级编码 3级编码 4级编码")
    private List<String> productCategoryCodes;

    @ApiModelProperty(value = "1级品类编码",hidden = true)
    private String productCategoryLv1Code;

    @ApiModelProperty(value ="2级品类编码",hidden = true)
    private String productCategoryLv2Code;

    @ApiModelProperty(value ="3级品类编码",hidden = true)
    private String productCategoryLv3Code;

    @ApiModelProperty(value ="4级品类编码",hidden = true)
    private String productCategoryLv4Code;

    @ApiModelProperty("品牌名称")
    private String productBrandName;

    @ApiModelProperty("品牌编码")
    private String productBrandCode;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("状态(0:再用 1:停止进货 2:停止配送 3:停止销售)")
    private Byte skuStatus;

    @ApiModelProperty(value = "公司编码", notes = "前端查询接口可以不传,但是其他第三方系统此字段必填")
    private String companyCode;

    @ApiModelProperty(value = "当前登录人",hidden = true)
    private String personId;

    @ApiModelProperty(value = "销售条形码")
    private String barCode;

    @ApiModelProperty(value = "质检报告（0.有 1.无）")
    private Integer inspectionStatus;

    @ApiModelProperty(value = "监管仓开单类型")
    private String orderType;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeStart;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeEnd;

    private HttpServletResponse httpServletResponse;

}

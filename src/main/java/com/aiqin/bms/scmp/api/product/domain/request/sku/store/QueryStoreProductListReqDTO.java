package com.aiqin.bms.scmp.api.product.domain.request.sku.store;

import com.aiqin.bms.scmp.api.base.PageReq;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterApiResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseApiResVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @功能说明:
 * @author wangxu
 * @date 2018/12/27 0027 16:06
 */
@Data
@ApiModel("门店商品列表请求条件")
public class QueryStoreProductListReqDTO extends PageReq {
    /*@ApiModelProperty(value = "门店所属城市id")
    @JsonProperty("city_id")
    private String cityId;

    @ApiModelProperty(value = "门店所属省id")
    @JsonProperty("province_id")
    private String provinceId;

    @ApiModelProperty(value = "门店所属区id")
    @JsonProperty("district_id")
    private String districtId;*/

    @ApiModelProperty(value = "品类id")
    @JsonProperty("category_id")
    private String categoryId;

    @ApiModelProperty(value = "品牌id")
    @JsonProperty("brand_id")
    private String brandId;

   /* @ApiModelProperty(value = "特卖商品: 1 查询特卖商品")
    @JsonProperty("special_sell")
    private Integer specialSell;*/

    @ApiModelProperty(value = "送货方式")
    @JsonProperty("ship_method")
    private String shipMethod;

    @ApiModelProperty(value = "商品标签,1为主推，2为新品")
    @JsonProperty("product_type")
    private Byte productType;

    @ApiModelProperty(value = "sku编码集合")
    @JsonProperty("sku_codes")
    private List<String> skuCodes;

    @ApiModelProperty(value = "显示有货,0 否  1 是")
    @JsonProperty("show_type")
    private Integer showType;

    @ApiModelProperty(value = "搜索框条件")
    @JsonProperty("text")
    private String text;

    @ApiModelProperty("排序类型 0 销量 1 进货价,3 订货量,4 综合排序")
    @JsonProperty("sort_by")
    private Integer sortBy;

    @ApiModelProperty(value = "排序 降序 desc,升序 asc")
    @JsonProperty("sort")
    private String sort;

    @ApiModelProperty(value = "门店所属物流中心")
    @JsonProperty("logistics_centers")
    private List<LogisticsCenterApiResVo> logisticsCenterApiResVos;

    @JsonProperty("request_time")
    @ApiModelProperty("请求时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date requestTime;

    //private List<LogisticsCenterApiResVo> logisticsCenterApiResVoList;

    private List<WarehouseApiResVo> warehouseApiResVos;


}

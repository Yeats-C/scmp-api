package com.aiqin.bms.scmp.api.product.domain.request.changeprice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author: NullPointException
 * @date: 2019-05-20
 * @time: 17:28
 */
@Data
@ApiModel("新增变价请求VO")
public class ProductSkuChangePriceReqVO {

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("变价类型编码")
    private String changePriceType;

    @ApiModelProperty("变价类型名称")
    private String changePriceName;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("采购组编码")
    private String purchaseGroupCode;

    @ApiModelProperty("采购组名称")
    private String purchaseGroupName;

    @ApiModelProperty("备注")
    private String remake;

    @ApiModelProperty("直属上级编码")
    private String directSupervisorCode;

    @ApiModelProperty("直属上级名称")
    private String directSupervisorName;

    @ApiModelProperty("运费承担方编码")
    private String costBearerCode;

    @ApiModelProperty("运费承担方名称")
    private String costBearerName;

    @ApiModelProperty("预算")
    private Long budget;

    @ApiModelProperty("公司名称")
    private String companyCode;

    @ApiModelProperty("公司编码")
    private String companyName;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("调价原因编码")
    private String changePriceReasonCode;

    @ApiModelProperty("调价原因描述")
    private String changePriceReasonName;

    @ApiModelProperty("审批表单号")
    private String formNo;

    @ApiModelProperty("提交or保存1提交2保存")
    @NotNull(message = "操作类型不能为空")
    private Integer operation;

    @ApiModelProperty("sku列表信息")
    @NotEmpty(message = "sku信息不能为空")
    private List<ProductSkuChangePriceInfoReqVO> infoLists;

    @ApiModelProperty("图片集合")
    private List<ProductSkuChangePriceImageReqVO> picUrlList;

    @ApiModelProperty("区域信息")
    private List<ProductSkuChangePriceAreaInfoReqVO> areaList;
}

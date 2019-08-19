package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author sunx
 * @description
 * @date 2019-08-01
 * MEINS	UNIT	3	0	基本计量单位
 * MATKL	CHAR	9	0	物料组
 * BRGEW	QUAN	13	3	毛重
 * NTGEW	QUAN	13	3	净重
 * GEWEI	UNIT	3	0	重量单位
 * VOLUM	QUAN	13	3	体积
 * VOLEH	UNIT	3	0	体积单位
 * SPART	CHAR	2	0	产品组
 * MSTAE	CHAR	2	0	跨工厂物料状态
 * MSTAV	CHAR	2	0	跨分销链物料状态
 * LVORM	CHAR	1	0	删除标识符
 * PRDHA	CHAR	18	0	产品层次结构
 * EAN11	CHAR	18	0	国际文件号(EAN/UPC)
 * LABOR	CHAR	3	0	实验室/设计室
 * ZEINR	CHAR	22	0	凭证/规格
 * GROES	CHAR	32	0	大小/量纲
 * EXTWG	CHAR	18	0	外部物料组
 * FERTH	CHAR	18	0	生产/检验备忘录
 * MHDRZ	DEC	4	0	最短剩余货架寿命
 * MHDHB	DEC	4	0	总货架寿命
 * NORMT	CHAR	18	0	工业标准描述 (例如 ANSI 或 ISO)
 */
@Data
@ToString(callSuper = true)
@ApiModel("scmp商品基本信息")
public class SapProductSkuBase extends SapBaseVO {
    /**
     * 基本计量单位 EA 个
     */
    @ApiModelProperty("基本计量单位,EA个")
    @JsonProperty("MEINS")
    private String unit;
    /**
     *产品组
     */
    @ApiModelProperty("产品组,14")
    @JsonProperty("SPART")
    private String productGroupCode;
    /**
     * 物料组
     */
    @ApiModelProperty("物料组,05020205")
    @JsonProperty("MATKL")
    private String groupCode;
    /**
     * 重量 KG
     */
    @ApiModelProperty("重量单位,KG")
    @JsonProperty("GEWEI")
    private String weight;

    @ApiModelProperty("凭证/规格,50g")
    @JsonProperty("ZEINR")
    private String standard;

    /**
     * 外部物料组
     */
    @ApiModelProperty("外部物料组,Z02")
    @JsonProperty("EXTWG")
    private String exGroupCode;
}

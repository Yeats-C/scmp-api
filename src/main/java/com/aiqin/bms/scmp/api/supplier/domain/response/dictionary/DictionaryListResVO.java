package com.aiqin.bms.scmp.api.supplier.domain.response.dictionary;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("供应商字典返回")
@Data
public class DictionaryListResVO {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("字典编号")
    private String dictionaryCode;

    @ApiModelProperty("字典名称")
    private String dictionaryName;

    @ApiModelProperty("字典类型")
    private String dictionaryType;
    @ApiModelProperty("字典类型名称")
    private String dictionaryTypeName;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @ApiModelProperty("是否启用(0,1)")
    private Byte enabled;

//    public String getDictionaryTypeName() {
////        if(dictionaryType.equals("supplyChain")){
////            this.dictionaryTypeName="供应链";
////        }if(dictionaryType.equals("logistics")) {
////            this.dictionaryTypeName="物流,商品";
////        }
////        if(dictionaryType.equals("purchase")) {
////            this.dictionaryTypeName="采购";
////        }
//
//        return dictionaryTypeName;
//    }

//    public void setDictionaryTypeName(String dictionaryTypeName) {
//        if(dictionaryType.equals("supplyChain")){
//            this.dictionaryTypeName="供应链";
//        }if(dictionaryType.equals("logistics")) {
//            this.dictionaryTypeName="物流,商品";
//        }
//        if(dictionaryType.equals("purchase")) {
//            this.dictionaryTypeName="采购";
//        }
//    }
}
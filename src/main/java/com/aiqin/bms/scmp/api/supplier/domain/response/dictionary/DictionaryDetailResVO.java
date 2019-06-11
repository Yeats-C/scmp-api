package com.aiqin.bms.scmp.api.supplier.domain.response.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ApiModel("供应商字典返回实体")
public class DictionaryDetailResVO{
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("供应商字典名称")
    @NotEmpty(message = "供应商字典名称不能为空")
    private String dictionaryName;

    @ApiModelProperty("供应商字典类型")
    @NotEmpty(message = "供应商字典类型不能为空")
    private String dictionaryType;
    @ApiModelProperty("供应商字典类型名称")
    private String dictionaryTypeName;

    @ApiModelProperty("删除标记(0:正常 1:删除)")
    private Byte delFlag;
    @ApiModelProperty("0 启用/1 禁用")
    private Byte enabled;

    private List<DictionaryInfoResponseVO> listInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType;
    }

    public String getDictionaryTypeName() {
        if(dictionaryType.equals("SupplyChain")){
            this.dictionaryTypeName="供应链";
        }if(dictionaryType.equals("logistics")) {
            this.dictionaryTypeName="物流";
        }
        return dictionaryTypeName;
    }

    public void setDictionaryTypeName(String dictionaryTypeName) {
        if(dictionaryType.equals("SupplyChain")){
            this.dictionaryTypeName="供应链";
        }if(dictionaryType.equals("logistics")) {
            this.dictionaryTypeName="物流";
        }
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public List<DictionaryInfoResponseVO> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<DictionaryInfoResponseVO> listInfo) {
        this.listInfo = listInfo;
    }

    public Byte getEnabled() {
        return enabled;
    }

    public void setEnabled(Byte enabled) {
        this.enabled = enabled;
    }
}
package com.aiqin.bms.scmp.api.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("分页数据")
@Data
public class PageResData<T> {
    @ApiModelProperty("总条数")
    private Integer totalCount;
    @ApiModelProperty("返回数据")
    private List<T> dataList;

    @ApiModelProperty("是否重复")
    private Integer isRepeat;

    public PageResData() {
    }

    public PageResData(Integer totalCount, List<T> dataList) {
        this.totalCount = totalCount;
        this.dataList = dataList;
    }

    public PageResData(Integer totalCount, List<T> dataList, Integer isRepeat) {
        this.totalCount = totalCount;
        this.dataList = dataList;
        this.isRepeat = isRepeat;
    }
}

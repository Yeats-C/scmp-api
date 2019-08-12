package com.aiqin.bms.scmp.api.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("报表导出数据")
public class PageImportResData<T> {
    @ApiModelProperty("总条数")
    private T dataSum;
    @ApiModelProperty("返回数据")
    private List<T> dataList;


    public PageImportResData(T dataSum, List<T> dataList) {
        this.dataSum = dataSum;
        this.dataList = dataList;
    }

    public PageImportResData() {
    }

    public T getDataSum() {
        return dataSum;
    }

    public void setDataSum(T dataSum) {
        this.dataSum = dataSum;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}

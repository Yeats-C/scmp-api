package com.aiqin.bms.scmp.api.base;


import com.aiqin.bms.scmp.api.bireport.domain.response.SupplierArrivalRateRespVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel("分页报表数据")
public class PageReportResData<T> {
    @ApiModelProperty("总条数")
    private Integer totalCount;
    @ApiModelProperty("返回数据")
    private List<T> dataList;
    @ApiModelProperty("返回列名")
    private T column;

    public PageReportResData(Integer totalCount, List<T> dataList, T column) {
        this.totalCount = totalCount;
        this.dataList = dataList;
        this.column = column;
    }

    public PageReportResData() {
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public T getColumn() {
        return column;
    }

    public void setColumn(T column) {
        this.column = column;
    }
}

/**
 *
 */
package com.aiqin.bms.scmp.api.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "分页传输对象")
public class PagesRequest {

    @ApiModelProperty(value = "每页条数")
    @JsonProperty("page_size")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页")
    @JsonProperty("page_no")
    private Integer pageNo;

    @ApiModelProperty(value = "不用传这个", hidden = true)
    @JsonProperty("page_index")
    private Integer beginIndex;

    public Integer getPageSize() {
        pageSize = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        pageNo = (pageNo == null || pageNo < 1) ? 1 : pageNo;
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getBeginIndex() {
        beginIndex = (getPageNo() - 1) * getPageSize();
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }
}

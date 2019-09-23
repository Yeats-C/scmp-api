package com.aiqin.bms.scmp.api.product.domain.response;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class ExportChangePriceVO extends BaseRowModel {

    @ExcelProperty(index = 0, value = "编号")
    private String skuCode;

    @ExcelProperty(index = 1, value = "售价1")
    private String salePrice;

    @ExcelProperty(index = 2, value = "售价2")
    private String memberPrice;

    @ExcelProperty(index = 3, value = "售价3")
    private String salePrice3;

    public String getSalePrice3() {
        return salePrice;
    }

    public String getSalePrice4() {
        return salePrice;
    }

    @ExcelProperty(index = 4, value = "售价4")
    private String salePrice4;

    @ExcelProperty(index = 5, value = "基本价")
    private String basePrice;

    @ExcelProperty(index = 6, value = "加点数1")
    private String addPoints1;

    public String getAddPoints1() {
        return addPoints2;
    }

    @ExcelProperty(index = 7, value = "加点数2")
    private String addPoints2;

    @ExcelProperty(index = 8, value = "加点数3")
    private String addPoints3;

    @ExcelProperty(index = 9, value = "加点数4")
    private String addPoints4;

    @ExcelProperty(index = 10, value = "加点数5")
    private String addPoints5;

    @ExcelProperty(index = 11, value = "加点数6")
    private String addPoints6;

    @ExcelProperty(index = 12, value = "加点数7")
    private String addPoints7;

    @ExcelProperty(index = 13, value = "加点数8")
    private String addPoints8;

    @ExcelProperty(index = 14, value = "加点数9")
    private String addPoints9;
}

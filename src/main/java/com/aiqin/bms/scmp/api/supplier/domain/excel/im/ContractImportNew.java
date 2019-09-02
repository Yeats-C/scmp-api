package com.aiqin.bms.scmp.api.supplier.domain.excel.im;

import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className ContractImportNew
 * @date 2019/9/2 15:10
 */
@Data
@ApiModel("合同新增导入模板")
public class ContractImportNew extends BaseRowModel {
    public static final String HEADER = "";
}

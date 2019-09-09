package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.SaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.CompanyAndDeptResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.category.CategoryResponse;

import java.util.List;

public interface StatDeptCategorySalesDao {
    CategoryResponse categorySum(SaleRequest saleRequest);

    List<CompanyAndDeptResponse> categoryByCompany(SaleRequest saleRequest);

    List<CategoryResponse> categoryList(SaleRequest saleRequest);
}

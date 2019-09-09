package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.request.SaleRequest;
import com.aiqin.bms.scmp.api.statistics.domain.response.CompanyAndDeptResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.sale.SaleCompanyResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.sale.SaleDeptResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.sale.SaleStoreResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.sale.SaleSumResponse;

import java.util.List;

public interface StatComMonthAccSalesDao {

    SaleSumResponse saleSum(SaleRequest saleRequest);

    List<CompanyAndDeptResponse> saleByDept(SaleRequest saleRequest);

    SaleDeptResponse saleSumDept(SaleRequest saleRequest);

    List<CompanyAndDeptResponse> saleByCompany(SaleRequest saleRequest);

    SaleCompanyResponse saleSumCompany(SaleRequest saleRequest);

    List<SaleStoreResponse> saleStoreList(SaleRequest saleRequest);
}
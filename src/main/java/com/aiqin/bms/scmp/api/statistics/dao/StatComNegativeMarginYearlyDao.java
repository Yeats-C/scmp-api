package com.aiqin.bms.scmp.api.statistics.dao;

import com.aiqin.bms.scmp.api.statistics.domain.response.CompanyAndDeptResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.negative.NegativeCategoryResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.negative.NegativeCompanyResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.negative.NegativeDeptResponse;
import com.aiqin.bms.scmp.api.statistics.domain.response.negative.NegativeSumResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatComNegativeMarginYearlyDao {

    NegativeSumResponse negativeByDeptSum(Long year);

    List<CompanyAndDeptResponse> negativeByDept(Long year);

    List<CompanyAndDeptResponse> negativeByCompany(@Param("year") Long year,
                                                   @Param("productSortCode") String productSortCode);

    NegativeCompanyResponse negativeCompanyList(@Param("year") Long year,
                                                      @Param("priceChannelCode") String priceChannelCode,
                                                      @Param("productSortCode") String productSortCode);

    NegativeDeptResponse negativeDeptList(@Param("year") Long year,
                                                @Param("productSortCode") String productSortCode);

    List<NegativeCategoryResponse> negativeCategoryList(@Param("year") Long year,
                                                    @Param("priceChannelCode") String priceChannelCode,
                                                    @Param("productSortCode") String productSortCode);

}
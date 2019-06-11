package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.ProductCycle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 爱亲 on 2018/11/20.
 */
public interface ProductCycleDao {

    List<ProductCycle> selectProductCycleByProductIdList(@Param(value = "productIdList") List<String> productIdList);
}

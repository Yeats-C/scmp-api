package com.aiqin.bms.scmp.api.purchase.mapper;

import com.aiqin.bms.scmp.api.purchase.domain.pojo.returngoods.ReturnOrderInfoInspectionItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnOrderInfoInspectionItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReturnOrderInfoInspectionItem record);

    int insertSelective(ReturnOrderInfoInspectionItem record);

    ReturnOrderInfoInspectionItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReturnOrderInfoInspectionItem record);

    int updateByPrimaryKey(ReturnOrderInfoInspectionItem record);
    /**
     * 保存验货数据
     * @author NullPointException
     * @date 2019/6/24
     * @param items
     * @return int
     */
    int insertBatch(List<ReturnOrderInfoInspectionItem> items);

    List<ReturnOrderInfoInspectionItem> returnBatchList(@Param("skuCode") String skuCode,
                                                        @Param("returnOrderCode") String returnOrderCode,
                                                        @Param("productLineNum") Integer productLineNum);
}
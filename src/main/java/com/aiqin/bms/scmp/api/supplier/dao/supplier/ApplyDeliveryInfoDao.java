package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplyDeliveryInformation;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplyDeliveryInfoReqDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 18:07
 */
public interface ApplyDeliveryInfoDao {

    Long insertApply(ApplyDeliveryInfoReqDTO applyDeliveryInfoReqDTO);

    int updateAndCode(@Param("name") String name, @Param("code") String code);

    List<ApplyDeliveryInformation> getApplyDeliveryInfo(String applySupplyComCode);

    int updateBatch(List<ApplyDeliveryInformation> applyDeliveryInfos);

    int deleteBatch(String applyCode);

}

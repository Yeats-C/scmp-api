package com.aiqin.bms.scmp.api.supplier.dao.purchasegroup;


import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.QueryPurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PurchaseGroupDao {


    /**
     * 分页查询列表
     * @param vo
     * @return
     */
    List<PurchaseGroupDTO> selectByPurchaseGroup(QueryPurchaseGroupReqVo vo);

//    int deleteByPrimaryKey(Long id);
//
 //  int insert(PurchaseGroupDTO record);
//

    /**
     * 保存采购组主体
     * @param record
     * @return
     */
   int insertSelective(PurchaseGroupDTO record);


    /**
     * 通过id去获取采购详情
     * @param id
     * @return
     */
    PurchaseGroupDTO selectByPrimaryKey(Long id);

    /**
     * 通过id去更新采购管理组主体
     * @param record
     * @return
     */
   int updateByPrimaryKeySelective(PurchaseGroupDTO record);
//
//    int updateByPrimaryKey(PurchaseGroupDTO record);


    /**
     * 获取未禁用的采购组
     * @param companyCode
     * @param personId
     * @return
     */
    List<PurchaseGroupVo> getPurchaseGroup(@Param("companyCode") String companyCode, @Param("personId") String personId,@Param("name") String name);

    /**
     * 验证名字是否重复
     * @param purchaseGroupName
     * @param id
     * @param companyCode
     * @return
     */
    Integer checkName(@Param("purchaseGroupName") String purchaseGroupName, @Param("id") Long id, @Param("companyCode") String companyCode);
    @MapKey("purchaseGroupName")
    Map<String, PurchaseGroupDTO> selectByNames(@Param("list") Set<String> purchaseGroupList, @Param("companyCode") String companyCode);
}
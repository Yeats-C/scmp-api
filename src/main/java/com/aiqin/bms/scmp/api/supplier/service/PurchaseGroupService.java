package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.request.dictionary.EnabledSave;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.BatchOperatePurchaseGroupReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupBuyerDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.dto.PurchaseGroupDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.PurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.QueryPurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.UpdatePurchaseGroupReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.purchasegroup.vo.UserPositionsRequest;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.PurchaseGroupVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.purchasegroup.QueryPurchaseGroupResVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述:  采购组管理service层
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
public interface PurchaseGroupService {

    /**
     * 查询合同申请List
     * @param vo
     * @return
     */
    BasePage<QueryPurchaseGroupResVo> findPurchaseGroupList(QueryPurchaseGroupReqVo vo);

    /**
     * 新增采购组转化实体
     * @param purchaseGroupReqVo
     * @return
     */
    HttpResponse<Integer> savePurchaseGroup(PurchaseGroupReqVo purchaseGroupReqVo);

    /**
     * 通过id去获取采购组详情
     * @param id
     * @return
     */
     PurchaseGroupResVo findPurchaseGroupDetail(Long id);

    /**
     * 更新采购组转化实体
     * @param updatePurchaseGroupReqVo
     * @return
     */
    HttpResponse<Integer>   updatePurchaseGroup(UpdatePurchaseGroupReqVo updatePurchaseGroupReqVo);
    /**
     * 保存采购组主体
     * @param record
     * @return
     */
    int insertSelective(PurchaseGroupDTO record);

    /**
     * 批量保存采购组人员
     * @param purchaseGroupBuyerDTOS
     * @return
     */
    int saveList(List<PurchaseGroupBuyerDTO> purchaseGroupBuyerDTOS);

    /**
     * 通过id去更新采购管理组主体
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PurchaseGroupDTO record);

    /**
     * 批量更新采购管理组专员
     * @param purchaseGroupBuyerDTOS
     * @return
     */
    int  updateList(List<PurchaseGroupBuyerDTO> purchaseGroupBuyerDTOS);


    /**
     * 提供采购组接口
     * @return
     */
    List<PurchaseGroupVo> getPurchaseGroup(String name);


    /**
     * 获取外购人员信息
     * @return
     */
    HttpResponse getPurchaseGroupBuyerList(UserPositionsRequest userPositionsRequest);

    /**
     * 通过名称获取采购组集合
     * @param purchaseGroupList
     * @param companyCode
     * @return
     */
    Map<String, PurchaseGroupDTO> selectByNames(Set<String> purchaseGroupList, String companyCode);

    /**
     *
     * 功能描述: 启用/禁用
     *
     * @param enabledSave
     * @return
     * @auther knight.xie
     * @date 2019/8/6 21:46
     */
    Integer enabled(EnabledSave enabledSave);

    /**
     * 批量操作采购组人员
     * @param reqVo
     * @return
     */
    Boolean batchOperatePurchaseGroup(BatchOperatePurchaseGroupReqVO reqVo);

    /**
     * 删除
     * @param reqVo
     * @return
     */
    Boolean deletePurchaseGroupPersonnel(BatchOperatePurchaseGroupReqVO reqVo);

    /**
     * 修改的
     * @param reqVo
     * @return
     */
    Boolean updatePurchaseGroupPersonnel(BatchOperatePurchaseGroupReqVO reqVo);
}

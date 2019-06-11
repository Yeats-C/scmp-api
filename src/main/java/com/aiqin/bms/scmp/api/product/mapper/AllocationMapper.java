package com.aiqin.bms.scmp.api.product.mapper;


import com.aiqin.bms.scmp.api.product.domain.pojo.Allocation;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.QueryAllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.QueryAllocationResVo;

import java.util.List;


public interface AllocationMapper {


    /**
     * 列表展示以及搜索
     * @param vo
     * @return
     */
    List<QueryAllocationResVo> getList(QueryAllocationReqVo vo);

    int deleteByPrimaryKey(Long id);


    int insert(Allocation record);

    /**
     * 有选择的插入
     * @param record
     * @return
     */
    Long insertSelective(Allocation record);


    /**
     * 查看调拨单主体
     * @param id
     * @return
     */
    Allocation selectByPrimaryKey(Long id);


    /**
     * 有选择的更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Allocation record);

    int updateByPrimaryKey(Allocation record);

    /**
     * 根据流程变成查询申请合同
     * @param FormNo
     * @return
     */
    Allocation selectByFormNO(String FormNo);


    Long findIdByFormNo(String FormNo);

    /**
     * 根据编码查询调拨单主体
     * @param allocationCode
     * @return
     */
    Allocation selectByCode(String allocationCode);
}
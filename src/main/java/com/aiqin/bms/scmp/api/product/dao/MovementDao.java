package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.Movement;
import com.aiqin.bms.scmp.api.product.domain.request.movement.QueryMovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.QueryMovementResVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MovementDao {

    /**
     * 列表展示以及搜索
     * @param vo
     * @return
     */
    List<QueryMovementResVo> getList(QueryMovementReqVo vo);

    int deleteByPrimaryKey(Long id);

    int insert(Movement record);

    /**
     * 有选择的添加移库单
     * @param record
     * @return
     */
    int insertSelective(Movement record);

    Movement selectByPrimaryKey(Long id);

    Movement selectByCode(@Param("movementCode") String movementCode);

    int updateByPrimaryKeySelective(Movement record);

    int updateByPrimaryKey(Movement record);

    Movement selectByFormNo(String formNo);
}
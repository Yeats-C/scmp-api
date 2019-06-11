package com.aiqin.bms.scmp.api.product.dao;

import com.aiqin.bms.scmp.api.product.domain.pojo.MovementProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MovementProductDao {

    /**
     * 批量新增
     * @param list
     * @return
     */
    int saveList(@Param("list") List<MovementProduct> list);

    int deleteByPrimaryKey(Long id);

    int insert(MovementProduct record);

    int insertSelective(MovementProduct record);



    MovementProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MovementProduct record);

    int updateByPrimaryKey(MovementProduct record);

    /**
     * 通过移库单编码查询
     * @param movementCode
     * @return
     */
    List<MovementProduct> selectByCode(@Param("movementCode") String movementCode);


    /**
     * 通过移库单编码查询图片等信息
     * @param movementCode
     * @return
     */
    List<MovementProduct> selectDetailByCode(@Param("movementCode") String movementCode);
}
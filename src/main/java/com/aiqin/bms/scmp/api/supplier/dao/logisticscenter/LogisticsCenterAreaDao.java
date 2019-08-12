package com.aiqin.bms.scmp.api.supplier.dao.logisticscenter;


import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterAreaDTO;

import java.util.List;

public interface LogisticsCenterAreaDao {

    /**
     * 批量插入物流中心服务范围
     * @param logisticsCenterAreaDTOS
     * @return
     */
    int saveList(List<LogisticsCenterAreaDTO> logisticsCenterAreaDTOS);

    /**
     * 查询所有的物流中心服务区范围
     * @param logisticsCenterCode
     * @return
     */
    List<LogisticsCenterAreaDTO> disEnableByLogisticsCenterCode(String logisticsCenterCode);

    /**
     * 根据id去批量修改物流中心
     * @param logisticsCenterAreaDTOS
     * @return
     */
    int updateList(List<LogisticsCenterAreaDTO> logisticsCenterAreaDTOS);


    /**
     * 查询未被禁用的物流中心
     * @param logisticsCenterCode
     * @return
     */
    List<LogisticsCenterAreaDTO> enableByLogisticsCenterCode(String logisticsCenterCode);
    int deleteByPrimaryKey(Long id);

    int insert(LogisticsCenterAreaDTO record);

    int insertSelective(LogisticsCenterAreaDTO record);

    LogisticsCenterAreaDTO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogisticsCenterAreaDTO record);

    int updateByPrimaryKey(LogisticsCenterAreaDTO record);
    /**
     * 通过关联编码查询数据
     * @author NullPointException
     * @date 2019/7/9
     * @param logisticsCenterCode
     * @return java.util.List<com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterAreaDTO>
     */
    List<LogisticsCenterAreaDTO> selectByCode(String logisticsCenterCode);
    /**
     * 通过关联编码删除
     * @author NullPointException
     * @date 2019/7/9
     * @param logisticsCenterCode
     * @return int
     */
    int deleteByCode(String logisticsCenterCode);
}
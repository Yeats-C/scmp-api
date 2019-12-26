package com.aiqin.bms.scmp.api.supplier.dao.logisticscenter;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.QueryLogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseListReqVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LogisticsCenterDao {

    /**
     * 分页查询以及搜索
     * @param vo
     * @return
     */
     List<LogisticsCenterDTO> getLogisticsCenterList(QueryLogisticsCenterReqVo vo);

    int deleteByPrimaryKey(Long id);

    int insert(LogisticsCenterDTO record);

    /**
     * 保存物流中心主体
     * @param record
     * @return
     */
    int insertSelective(LogisticsCenterDTO record);

    LogisticsCenterDTO selectByPrimaryKey(Long id);

    /**
     * 有选择的更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(LogisticsCenterDTO record);

    int updateByPrimaryKey(LogisticsCenterDTO record);

    /**
     * 查询未被禁用的物流中心主体
     * @return
     */
   List<LogisticsCenterDTO> getEnableLogisticsCenterList(@Param("companyCode") String companyCode);

    /**
     * 验证名字是否重复
     * @param logisticsCenterName
     * @return
     */
    Integer checkName(@Param("logisticsCenterName") String logisticsCenterName, @Param("code") String code, @Param("companyCode") String companyCode);

    /**
     * 根据省市编码，服务范围查询物流中心
     * @param warehouseListReqVo
     * @return
     */
    List<LogisticsCenterDTO> getLogisticsCenterListByArea(WarehouseListReqVo warehouseListReqVo);

    /**
     * 根据仓库名称查询仓库信息 (退供导入使用)
     */
    LogisticsCenter selectByCenterName(@Param("logisticsCenterName")String logisticsCenterName);

    /**
     * 通过编码更新
     * @author NullPointException
     * @date 2019/7/9
     * @param record
     * @return int
     */
    int updateByCodeSelective(LogisticsCenterDTO record);

    /**
     * 名称集合匹配
     * @author NullPointException
     * @date 2019/7/18
     * @param warehouseList
     * @param companyCode
     * @return java.util.List<com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter>
     */
    @MapKey("logisticsCenterName")
    Map<String,LogisticsCenterDTO> selectByCenterNames(@Param("list") Set<String> warehouseList, @Param("companyCode") String companyCode);

}
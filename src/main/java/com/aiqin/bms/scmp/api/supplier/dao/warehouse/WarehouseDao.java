package com.aiqin.bms.scmp.api.supplier.dao.warehouse;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.Warehouse;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.QueryWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseListReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo2;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface WarehouseDao {

   /**
    * 分页搜索
    */
   List<WarehouseDTO> findWarehouseList(@RequestBody QueryWarehouseReqVo vo);

   /**
    * 插入一条库房记录
    */
   int insertSelective(WarehouseDTO record);

   /**
    * 通过id查询库房详情
    */
    WarehouseDTO selectByPrimaryKey(Long id);

   /**
    * 有选择的更新实体
    */
   int updateByPrimaryKeySelective(WarehouseDTO record);

   /**
    * 验证名字是否重复
    */
   Integer checkName(@Param("warehouseName") String warehouseName, @Param("id") Long id, @Param("companyCode") String companyCode);

   /**
    * 通过编码获取库房详情
    */
   WarehouseDTO getWarehouseByCode(String warehouseCode);

   /**
    * 通过物流中心编码获取库房
    */
   List<WarehouseDTO> getWarehouseByLogisticsCenterCode(@Param("logisticsCenterCode") String logisticsCenterCode, @Param("warehouseTypeCode") Byte warehouseTypeCode);

   /**
    * 根据地区编码查询库房
    */
   List<WarehouseDTO>  getWarehouseApi(WarehouseListReqVo warehouseListReqVo);

   List<WarehouseDTO> getWarehouseTypeByLogisticsCenterCode(@Param("logisticsCenterCode") String logisticsCenterCode, @Param("warehouseTypeCode") Byte warehouseTypeCode);

   /**
    * 根据库房名称查询库房信息 (退供导入使用)
    */
   Warehouse selectByWarehouseName(@Param("warehouseName") String warehouseName);

   List<WarehouseDTO> getWarehouseByLogisticsCenterCodeAndNotExistsType(@Param("logisticsCenterCode") String logisticsCenterCode, @Param("warehouseTypeCode") Byte warehouseTypeCode);

   List<WarehouseDTO> warehouseByBatchMange();

    List<WarehouseDTO>  findWarehouseListForBatch();

    /** 通过仓库编码查询启用库房信息 */
   List<WarehouseDTO> getWarehouseCodeByTransportCenterCode(@Param("transportCenterCode") String transportCenterCode);
}
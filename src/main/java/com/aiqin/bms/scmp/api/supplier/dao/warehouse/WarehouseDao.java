package com.aiqin.bms.scmp.api.supplier.dao.warehouse;

import com.aiqin.bms.scmp.api.product.domain.response.WarehouseConfigResp;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Warehouse;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.QueryWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseListReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface WarehouseDao {

   List<WarehouseDTO> findWarehouseList(@RequestBody QueryWarehouseReqVo vo);

   int insertSelective(WarehouseDTO record);

   WarehouseDTO selectByPrimaryKey(Long id);

   int updateByPrimaryKeySelective(WarehouseDTO record);

   Integer checkName(@Param("warehouseName") String warehouseName, @Param("id") Long id, @Param("companyCode") String companyCode);

   WarehouseDTO getWarehouseByCode(String warehouseCode);

   List<WarehouseDTO> getWarehouseByLogisticsCenterCode(@Param("logisticsCenterCode") String logisticsCenterCode, @Param("warehouseTypeCode") Byte warehouseTypeCode);

   List<WarehouseDTO> getWarehouseApi(WarehouseListReqVo warehouseListReqVo);

   List<WarehouseDTO> getWarehouseTypeByLogisticsCenterCode(@Param("logisticsCenterCode") String logisticsCenterCode, @Param("warehouseTypeCode") Byte warehouseTypeCode);

   Warehouse selectByWarehouseName(@Param("warehouseName") String warehouseName);

   List<WarehouseDTO> getWarehouseByLogisticsCenterCodeAndNotExistsType(@Param("logisticsCenterCode") String logisticsCenterCode, @Param("warehouseTypeCode") Byte warehouseTypeCode);

   List<WarehouseDTO> warehouseByBatchMange();

   List<WarehouseDTO> findWarehouseListForBatch();

   List<WarehouseDTO> getWarehouseCodeByTransportCenterCode(@Param("transportCenterCode") String transportCenterCode);

   WarehouseDTO selectWarehouseByWms(@Param("wmsWarehouseCode") String wmsWarehouseCode, @Param("wmsWarehouseType") Integer wmsWarehouseType);

   WarehouseConfigResp refresh(String warehouseCode);

   List<WarehouseDTO> warehouseList();

   List<WarehouseDTO> warehouseWms(@Param("wmsWarehouseId") String wmsWarehouseId);

   WarehouseDTO warehouseDl(@Param("wmsWarehouseId") String wmsWarehouseId, @Param("wmsWarehouseType") Integer wmsWarehouseType);
}
package com.aiqin.bms.scmp.api.supplier.dao.warehouse;


import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.QueryWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseListReqVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface WarehouseDao {

   /**
    * 分页搜索
    * @param vo
    * @return
    */
   List<WarehouseDTO> findWarehouseList(@RequestBody QueryWarehouseReqVo vo);

//    int deleteByPrimaryKey(Long id);
//
//    int insert(WarehouseDTO record);
//

   /**
    * 插入一条库房记录
    * @param record
    * @return
    */
   int insertSelective(WarehouseDTO record);

   /**
    * 通过id查询库房详情
    * @param id
    * @return
    */
    WarehouseDTO selectByPrimaryKey(Long id);

   /**
    * 有选择的更新实体
    * @param record
    * @return
    */
   int updateByPrimaryKeySelective(WarehouseDTO record);
//
//    int updateByPrimaryKey(WarehouseDTO record);

   /**
    * 验证名字是否重复
    * @param warehouseName
    * @param id
    * @param companyCode
    * @return
    */
   Integer checkName(@Param("warehouseName") String warehouseName, @Param("id") Long id, @Param("companyCode") String companyCode);


   /**
    * 通过编码获取库房详情
    * @param warehouseCode
    * @return
    */
   WarehouseDTO getWarehouseByCode(String warehouseCode);


   /**
    * 通过物流中心编码获取库房
    * @param logisticsCenterCode
    * @return
    */
   List<WarehouseDTO> getWarehouseByLogisticsCenterCode(@Param("logisticsCenterCode") String logisticsCenterCode, @Param("warehouseTypeCode") Byte warehouseTypeCode);


   /**
    * 根据地区编码查询库房
    * @param warehouseListReqVo
    * @return
    */
   List<WarehouseDTO>  getWarehouseApi(WarehouseListReqVo warehouseListReqVo);

   WarehouseDTO getWarehouseTypeByLogisticsCenterCode(@Param("logisticsCenterCode") String logisticsCenterCode, @Param("warehouseTypeCode") Byte warehouseTypeCode);
}
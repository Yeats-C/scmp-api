package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.QueryWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.UpdateWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseListReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterApiResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;

import java.util.List;

/**
 * 描述: 库房service层
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
public interface WarehouseService {

    /**
     * 列表展示以及搜索
     * @param vo
     * @return
     */
    BasePage<QueryWarehouseResVo> findWarehouseList(QueryWarehouseReqVo vo);

    /**
     * 保存转化实体
     * @param warehouseReqVo
     * @return
     */
    HttpResponse<Integer> saveWarehouse(WarehouseReqVo warehouseReqVo);

    /**
     * 通过id查询库房详情
     * @param id
     * @return
     */
    WarehouseResVo selectByPrimaryKey(Long id);

    /**
     * 修改转化实体
     * @param updateWarehouseReqVo
     * @return
     */
    HttpResponse<Integer> updateWarehouse(UpdateWarehouseReqVo updateWarehouseReqVo);
    /**
     * 保存库房实体
     * @param record
     * @return
     */
    int insertSelective(WarehouseDTO record);

    /**
     * 更新库房实体
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WarehouseDTO record);

    /**
     * 根据库房编码查询库房
     * @param warehouseCode
     * @return
     */
    WarehouseResVo getWarehouseByCode(String warehouseCode);


    /**
     * 根据物流中心编码查询库房列表
     * @param logisticsCenterCode
     * @return
     */
    List<WarehouseResVo> getWarehouseByLogisticsCenterCode(String logisticsCenterCode);


    /**
     * 根据地区编码查询库房
     * @param warehouseListReqVo
     * @return
     */
    List<LogisticsCenterApiResVo>  getWarehouseApi(WarehouseListReqVo warehouseListReqVo);

    /**
     * 通过物流中心编码获取有类型的库房
     * @param logisticsCenterCode
     * @param warehouseTypeCode
     * @return
     */
    List<WarehouseResVo> getWarehouseTypeByLogisticsCenterCode(String logisticsCenterCode, Byte warehouseTypeCode);

    /**
     *
     * 功能描述: 通过物流中心编码以及库房类型查询不在库房类型外所有库房
     *
     * @param logisticsCenterCode
     * @param warehouseTypeCode
     * @return
     * @auther knight.xie
     * @date 2019/7/29 21:45
     */
    List<WarehouseResVo> getWarehouseByLogisticsCenterCodeAndNotExistsType(String logisticsCenterCode, Byte warehouseTypeCode);
}

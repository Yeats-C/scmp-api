package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.supplier.dao.warehouse.WarehouseDao;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.common.Save;
import com.aiqin.bms.scmp.api.common.Update;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.dto.WarehouseDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.QueryWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.UpdateWarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseListReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.warehouse.vo.WarehouseReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterApiResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.QueryWarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseApiResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.warehouse.WarehouseResVo;
import com.aiqin.bms.scmp.api.supplier.service.WarehouseService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述:库房service实现层
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;

    @Autowired
    private EncodingRuleDao  encodingRuleDao;


    @Autowired
    private LogisticsCenterDao logisticsCenterDao;
    @Override
    public BasePage<QueryWarehouseResVo> findWarehouseList(QueryWarehouseReqVo vo) {

        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            vo.setCompanyCode(authToken.getCompanyCode());
        }
        List<WarehouseDTO> warehouseDTOS = warehouseDao.findWarehouseList(vo);
        // 获取分页参数
        BasePage<QueryWarehouseResVo> basePage = PageUtil.getPageList(vo.getPageNo(),warehouseDTOS);
        try {
            basePage.setDataList(BeanCopyUtils.copyList(warehouseDTOS,QueryWarehouseResVo.class));
            return basePage;
        }catch (Exception e){

        }
        throw new GroundRuntimeException("分页查询失败");
    }

    /**
     * 保存转化实体
     * @param warehouseReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<Integer> saveWarehouse(WarehouseReqVo warehouseReqVo) {
        // 验证名字是否重复
        String warehouseName = warehouseReqVo.getWarehouseName();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        Integer integer = warehouseDao.checkName(warehouseName,null,companyCode);
        if(integer>0){
            throw new GroundRuntimeException("库房名称重复无法添加");
        }
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        BeanCopyUtils.copy(warehouseReqVo,warehouseDTO);
        //设置库房编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.WAREHOUSE_CODE);
        warehouseDTO.setWarehouseCode(String.valueOf(encodingRule.getNumberingValue()));
        // 更新数据库编码尺度
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        //设置采购组主体的删除状态，启用禁用状态
        warehouseDTO.setDelFlag(Byte.parseByte("0"));
        warehouseDTO.setEnable(Byte.parseByte("1"));
        //保存采购组的主体
        int k = ((WarehouseService) AopContext.currentProxy()).insertSelective(warehouseDTO);
        if(k>0){
            return HttpResponse.success(k);
        }else {
            throw new GroundRuntimeException("库房新增失败");
        }
    }
    /**
     * 通过id查询库房详情
     * @param id
     * @return
     */
    @Override
    public WarehouseResVo selectByPrimaryKey(Long id) {
        try {
            WarehouseResVo warehouseResVo = new WarehouseResVo();
            WarehouseDTO warehouseDTO=warehouseDao.selectByPrimaryKey(id);
            BeanCopyUtils.copy(warehouseDTO,warehouseResVo);
            return warehouseResVo;
        }catch (Exception e){
            throw new GroundRuntimeException("查询失败");
        }

    }

    /**
     * 修改转化实体
     * @param updateWarehouseReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<Integer>updateWarehouse(UpdateWarehouseReqVo updateWarehouseReqVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        Integer integer = warehouseDao.checkName(updateWarehouseReqVo.getWarehouseName(),updateWarehouseReqVo.getId(),companyCode);
        if(integer>0){
            throw new GroundRuntimeException("库房名称重复无法修改");
        }
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        BeanCopyUtils.copy(updateWarehouseReqVo,warehouseDTO);
        //更新采购组主体
        int k = ((WarehouseService) AopContext.currentProxy()).updateByPrimaryKeySelective(warehouseDTO);
        if(k>0){
            return HttpResponse.success(k);
        }else {
            throw new GroundRuntimeException("库房修改失败");
        }
    }

    /**
     * 保存库房实体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int insertSelective(WarehouseDTO record) {
        try{
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                record.setCompanyCode(authToken.getCompanyCode());
                record.setCompanyName(authToken.getCompanyName());
            }
        return warehouseDao.insertSelective(record);
        }catch (Exception e){
            throw new GroundRuntimeException("库房新增失败");
        }
    }
    /**
     * 更新库房实体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateByPrimaryKeySelective(WarehouseDTO record) {
        try{
            return warehouseDao.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            throw new GroundRuntimeException("库房新增失败");
        }
    }

    /**
     * 根据库房编码查询库房
     * @param code
     * @return
     */
    @Override
    public WarehouseResVo getWarehouseByCode(String code) {
        try {
            WarehouseResVo warehouseResVo = new WarehouseResVo();
            WarehouseDTO warehouseDTO=warehouseDao.getWarehouseByCode(code);
            BeanCopyUtils.copy(warehouseDTO,warehouseResVo);
            return warehouseResVo;
        }catch (Exception e){
            throw new GroundRuntimeException("查询失败");
        }
    }


    /**
     * 根据物流中心编码查询库房列表
     * @param logisticsCenterCode
     * @return
     */
    @Override
    public List<WarehouseResVo> getWarehouseByLogisticsCenterCode(String logisticsCenterCode) {


        List<WarehouseDTO>  dtoList = warehouseDao.getWarehouseByLogisticsCenterCode(logisticsCenterCode, null);
        if(dtoList.size()>0){
            try{
                List<WarehouseResVo>  list =   BeanCopyUtils.copyList(dtoList,WarehouseResVo.class);
                return list;
            }catch (Exception e){
                throw new GroundRuntimeException("查询失败");
            }
        }else {
            return null;
        }

    }

    /**
     * 根据地区编码查询库房
     * @param warehouseListReqVo
     * @return
     */
    @Override
    public List<LogisticsCenterApiResVo> getWarehouseApi(WarehouseListReqVo warehouseListReqVo) {
            try{
                AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
                if(null != authToken){
                    warehouseListReqVo.setCompanyCode(authToken.getCompanyCode());
                }
                // 根据省市查询物流中心
                List<LogisticsCenterDTO> listByArea1 = logisticsCenterDao.getLogisticsCenterListByArea(warehouseListReqVo);
                 List<LogisticsCenterApiResVo>                      list = BeanCopyUtils.copyList(listByArea1,LogisticsCenterApiResVo.class);
                for (LogisticsCenterApiResVo logisticsCenterDTO : list) {
                    List<WarehouseDTO>  dtoList = warehouseDao.getWarehouseByLogisticsCenterCode(logisticsCenterDTO.getLogisticsCenterCode(),warehouseListReqVo.getWarehouseTypeCode());
                    List<WarehouseApiResVo>warehouseResVos =   BeanCopyUtils.copyList(dtoList,WarehouseApiResVo.class);
                    logisticsCenterDTO.setList(warehouseResVos);
                }

                return list;
            }catch (Exception e){
                throw new GroundRuntimeException("查询失败");
            }
    }

    /**
     * 通过物流中心编码获取有类型的库房
     * @param logisticsCenterCode
     * @param warehouseTypeCode
     * @return
     */
    @Override
    public WarehouseResVo getWarehouseTypeByLogisticsCenterCode(String logisticsCenterCode, Byte warehouseTypeCode) {
        WarehouseResVo warehouseResVo = new WarehouseResVo();
         WarehouseDTO dtoList = warehouseDao.getWarehouseTypeByLogisticsCenterCode(logisticsCenterCode, warehouseTypeCode);
        BeanCopyUtils.copy(dtoList,warehouseResVo);
        return warehouseResVo;
    }
}

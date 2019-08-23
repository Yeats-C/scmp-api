package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.manufacturer.ManufacturerBrandDao;
import com.aiqin.bms.scmp.api.supplier.dao.manufacturer.ManufacturerDao;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Manufacturer;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ManufacturerBrand;
import com.aiqin.bms.scmp.api.supplier.domain.request.OperationLogVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.ManufacturerReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.ManufacturerUpdateReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.manufacturer.vo.QueryManufacturerReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.LogData;
import com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer.ManufacturerBrandResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer.ManufacturerResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.manufacturer.QueryManufacturerResVo;
import com.aiqin.bms.scmp.api.supplier.service.SupplierCommonService;
import com.aiqin.bms.scmp.api.supplier.service.ManufacturerService;
import com.aiqin.bms.scmp.api.supplier.service.OperationLogService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述:
 *
 * @Author: Kt.w
 * @Date: 2018/12/11
 * @Version 1.0
 * @since 1.0
 */
@Service
@Slf4j
public class ManufacturerServiceImp  implements ManufacturerService {

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Autowired
    private  ManufacturerBrandDao manufacturerBrandDao;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private SupplierCommonService supplierCommonService;

    @Autowired
    private OperationLogService operationLogService;
    /**
     * 制造商列表查询
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryManufacturerResVo> list(QueryManufacturerReqVo vo) {
        try {
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                vo.setCompanyCode(authToken.getCompanyCode());
            }
            PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
            List<Manufacturer> manufacturerList = manufacturerDao.list(vo);
            BasePage<QueryManufacturerResVo> basePage = PageUtil.getPageList(vo.getPageNo(),manufacturerList);
            basePage.setDataList(BeanCopyUtils.copyList(manufacturerList,QueryManufacturerResVo.class));   ;
            return basePage;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error("制造商分页查询失败");
            throw new GroundRuntimeException(ex.getMessage());
        }
    }

    /**
     * 制造商新增转化实体
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<Integer> save(ManufacturerReqVo vo) {

        String companyCode = "";
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        // 验证名字是否重复
        String codNa = vo.getName();
        Integer integer=manufacturerDao.checkName(codNa,null,companyCode);
        if(integer>0){
            throw new GroundRuntimeException("制造商名称不能重复");
        }

        //制造商主体转化成数据库访问实体
        Manufacturer manufacturer = new Manufacturer();
        BeanCopyUtils.copy(vo,manufacturer);
        //产生编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.MANUFACTURER_CODE);
        manufacturer.setManufacturerCode(String.valueOf(encodingRule.getNumberingValue()));
        //更新数据库编码尺度最大值
        try{
            encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("制造商编码尺度更新失败");
            throw new GroundRuntimeException("制造商编码尺度更新失败");
        }
        // 保存制造商主体
        int k =((ManufacturerService) AopContext.currentProxy()).insert(manufacturer);
        if(k>0){
            try {
                List<ManufacturerBrand>list = BeanCopyUtils.copyList(vo.getList(),ManufacturerBrand.class);
                //设置制造商关联品牌编码
                list.stream().forEach(manufacturerBrand -> manufacturerBrand.setManufacturerCode(String.valueOf(manufacturer.getManufacturerCode())));
                int kp =((ManufacturerService) AopContext.currentProxy()).saveList(list);
                if(kp>0){
                    return  HttpResponse.success(kp);
                }else {
                    log.error("制造商品牌转化实体失败");
                    throw new GroundRuntimeException("制造商品牌转化实体失败");
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("制造商品牌转化实体失败");
                throw new GroundRuntimeException("制造商品牌转化实体失败");
            }
            }else {
            log.error("制造商主体保存失败");
            throw new GroundRuntimeException("制造商主体保存失败");
        }
        }
    /**
     * 查询制造商
     * @param id
     * @return
     */
    @Override
    public ManufacturerResVo view(Long id) {
        //new页面返回实体
        ManufacturerResVo manufacturerResVo = new ManufacturerResVo();
        //查询制造商主体
        Manufacturer manufacturer = manufacturerDao.selectByPrimaryKey(id);
        BeanCopyUtils.copy(manufacturer,manufacturerResVo);
        //查询制造商关联品牌
        List<ManufacturerBrand>list = manufacturerBrandDao.selectByPrimaryKey(manufacturer.getManufacturerCode());
        try {
            List<ManufacturerBrandResVo> manufacturerBrandResVoList=  BeanCopyUtils.copyList(list, ManufacturerBrandResVo.class);
            manufacturerResVo.setList(manufacturerBrandResVoList);
        } catch (Exception e) {
            log.error("error", e);
            log.error("制造商查看关联品牌转化失败");
            throw new GroundRuntimeException("制造商查看关联品牌转化失败");
        }
        //获取操作日志
        OperationLogVo operationLogVo = new OperationLogVo();
        operationLogVo.setPageNo(1);
        operationLogVo.setPageSize(100);
        operationLogVo.setObjectType(ObjectTypeCode.MANUFACTURER.getStatus());
        operationLogVo.setObjectId(manufacturerResVo.getManufacturerCode());
        BasePage<LogData> pageList = operationLogService.getLogType(operationLogVo, 62);
        List<LogData> logDataList = new ArrayList<>();
        if (null != pageList.getDataList() && pageList.getDataList().size() > 0) {
            logDataList = pageList.getDataList();
        }
        manufacturerResVo.setLogDataList(logDataList);
        return manufacturerResVo;
    }
    /**
     * 制造商修改转化实体
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<Integer>  update(ManufacturerUpdateReqVo vo) {
        // 验证名字是否重复
        String companyCode = "";
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        String codNa = vo.getName();
        Integer integer=manufacturerDao.checkName(codNa,vo.getId(),companyCode);
        if(integer>0){
            throw new GroundRuntimeException("制造商名称不能重复");
        }
        Manufacturer manufacturer = new Manufacturer();
        BeanCopyUtils.copy(vo,manufacturer);
        int k = ((ManufacturerService)AopContext.currentProxy()).updateByPrimaryKeySelective(manufacturer);
       if(k>0){
           try {
               List<ManufacturerBrand> list = BeanCopyUtils.copyList(vo.getList(),ManufacturerBrand.class);
               //对于品牌设置两种处理方式，有id的执行修改操作，没有id的执行添加操作
               List<ManufacturerBrand> saveList = new ArrayList<>();
               List<ManufacturerBrand> updateList = new ArrayList<>();
               for (ManufacturerBrand manufacturerBrand : list) {
                   if(manufacturerBrand.getId()!=null){
                       updateList.add(manufacturerBrand);
                   }else {
                       saveList.add(manufacturerBrand);
                   }
               }
               if(saveList.size()>0){
                   try{
                       saveList.stream().forEach(manufacturerBrand -> manufacturerBrand.setManufacturerCode(vo.getManufacturerCode()));
                       int kp = ((ManufacturerService) AopContext.currentProxy()).saveList(saveList);
                   }catch (Exception e){
                       log.error("error", e);
                       log.error("制造商关联品牌添加失败");
                       throw new GroundRuntimeException("制造商关联品牌添加失败");
                   }
               }
               if(updateList.size()>0){
                try{
                    updateList.stream().forEach(manufacturerBrand -> manufacturerBrand.setManufacturerCode(vo.getManufacturerCode()));
                    int kp = ((ManufacturerService) AopContext.currentProxy()).updateList(updateList);
                }catch (Exception e){
                    log.error("error", e);
                    log.error("制造商关联品牌更新失败");
                    throw new GroundRuntimeException("制造商关联品牌更新失败");
                }
               }
               return HttpResponse.success(k) ;
           } catch (Exception e) {
               log.error("error", e);
               log.error("品牌实体转化失败");
               throw new GroundRuntimeException("品牌实体转化失败");
           }
       }else{
           log.error("制造商主体修改失败");
           throw new GroundRuntimeException("制造商主体修改失败");
       }
    }

    /**
     * 保存制造商主体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int insert(Manufacturer record) {
        try{
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                record.setCompanyCode(authToken.getCompanyCode());
                record.setCompanyName(authToken.getCompanyName());
            }
            record.setEnable(Byte.parseByte("0"));
            return manufacturerDao.insert(record);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("制造商主体保存失败");
            throw new GroundRuntimeException("制造商主体保存失败");
        }
    }

    /**
     * 批量插入制造商关联品牌
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @SaveList
    public int saveList(List<ManufacturerBrand> list) {
        try{
            list.stream().forEach(manufacturerBrand -> manufacturerBrand.setEnable(Byte.parseByte("1")));
            return manufacturerBrandDao.saveList(list);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("制造商关联品牌保存失败");
            throw new GroundRuntimeException("制造商关联品牌保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateByPrimaryKeySelective(Manufacturer record) {
            try{
                return manufacturerDao.updateByPrimaryKeySelective(record);
            }catch (Exception e){
                log.error(e.getMessage());
                log.error("制造商主体修改失败");
                throw new GroundRuntimeException("制造商主体修改失败");
            }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @UpdateList
    public int updateList(List<ManufacturerBrand> list) {
        try{
            return manufacturerBrandDao.updateByPrimaryKeySelective(list);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("制造商品牌修改失败");
            throw new GroundRuntimeException("制造商品牌修改失败");
        }
    }

    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public int enable(String manufacturerCode, byte enable) {
        try{
            return manufacturerDao.enable(manufacturerCode,enable);
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("制造商品牌修改失败");
            throw new GroundRuntimeException("制造商品牌修改失败");
        }
    }

    @Override
    public Map<String, Manufacturer> selectByManufactureNames(Set<String> manufactureList, String companyCode) {
        return manufacturerDao.selectByManufactureNames(manufactureList,companyCode);
    }
}
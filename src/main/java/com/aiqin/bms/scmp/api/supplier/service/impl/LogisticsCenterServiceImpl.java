package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.mgs.control.component.service.AreaBasicService;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterAreaDao;
import com.aiqin.bms.scmp.api.supplier.dao.logisticscenter.LogisticsCenterDao;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.domain.LogisticsCenterEnum;
import com.aiqin.bms.scmp.api.base.AreaBasic;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterAreaDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.LogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.QueryLogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.UpdateLogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterAreaResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.QueryLogisticsCenterResVo;
import com.aiqin.bms.scmp.api.supplier.service.LogisticsCenterService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
@Service
public class LogisticsCenterServiceImpl implements LogisticsCenterService {

    @Autowired
    private LogisticsCenterDao logisticsCenterDao;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private LogisticsCenterAreaDao logisticsCenterAreaDao;
    @Resource
    private AreaBasicService areaBasicService;
    /**
     *  列表展示以及搜索
     * @param vo
     * @return
     */
    @Override
    public BasePage<QueryLogisticsCenterResVo> getLogisticsCenterList(QueryLogisticsCenterReqVo vo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            vo.setCompanyCode(authToken.getCompanyCode());
        }
        PageHelper.startPage(vo.getPageNo(), vo.getPageSize());
        List<LogisticsCenterDTO> logisticsCenterDTOS = logisticsCenterDao.getLogisticsCenterList(vo);
        // 获取分页参数
        BasePage<QueryLogisticsCenterResVo> basePage = PageUtil.getPageList(vo.getPageNo(),logisticsCenterDTOS);
        try {
            basePage.setDataList(BeanCopyUtils.copyList(logisticsCenterDTOS,QueryLogisticsCenterResVo.class));
            return basePage;
        }catch (Exception e){

        }
        throw new GroundRuntimeException("分页查询失败");
    }

    /**
     * 请求保存转化实体
     * @param logisticsCenterReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<Integer> saveLogisticsCenter(LogisticsCenterReqVo logisticsCenterReqVo) {

        // 验证名字是否重复
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        String logisticsCenterName = logisticsCenterReqVo.getLogisticsCenterName();
        Integer integer = logisticsCenterDao.checkName(logisticsCenterName,null,companyCode);
        if(integer>0){
            throw new GroundRuntimeException("物流中心名称不能重复");
        }
       LogisticsCenterDTO  logisticsCenterDTO = new LogisticsCenterDTO();
       BeanCopyUtils.copy(logisticsCenterReqVo,logisticsCenterDTO);
        //设置库房编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.LOGISTICS_CENTER_CODE);
        logisticsCenterDTO.setLogisticsCenterCode(String.valueOf(encodingRule.getNumberingValue()));
        // 更新数据库编码尺度
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),  encodingRule.getId());
        //设置采购组主体的删除状态，启用禁用状态
        logisticsCenterDTO.setDelFlag(Byte.parseByte("0"));
        logisticsCenterDTO.setEnable(Byte.parseByte("1"));
        //保存物流中心主体
        int k = ((LogisticsCenterService) AopContext.currentProxy()).insertSelective(logisticsCenterDTO);
        if (k>0){
            try {
                if(CollectionUtils.isNotEmptyCollection(logisticsCenterReqVo.getLogisticsCenterAreaReqVos())){
                    List<LogisticsCenterAreaDTO> list = BeanCopyUtils.copyList(logisticsCenterReqVo.getLogisticsCenterAreaReqVos(),LogisticsCenterAreaDTO.class);
                    //设置关联编码
                    list.stream().forEach(purchase -> purchase.setLogisticsCenterCode(String.valueOf(logisticsCenterDTO.getLogisticsCenterCode())));
                    //设置启用禁用状态
                    list.stream().forEach(purchase -> purchase.setEnable(Byte.parseByte("1")));
                    // 设置逻辑删除状态
                    list.stream().forEach(purchase -> purchase.setDelFlag(Byte.parseByte("0")));
                    //批量插入
                    int kp = ((LogisticsCenterService)AopContext.currentProxy()).saveList(list);
                    if(kp>0){
                        return HttpResponse.success(kp);
                    }else {
                        throw new GroundRuntimeException("物流中心服务范围新增失败");
                    }
                }else{
                    return HttpResponse.success(k);
                }
            }catch (Exception e){
                throw new GroundRuntimeException("物流中心服务范围新增失败");
            }

        }else {
            throw new GroundRuntimeException("物流中心新增失败");
        }

    }

    /**
     * 通过id返回物流中心实体
     * @param id
     * @return
     */
    @Override
    public LogisticsCenterResVo getLogisticsCenter(Long id) {
        LogisticsCenterResVo logisticsCenterResVo = new LogisticsCenterResVo();

        try {
            LogisticsCenterDTO logisticsCenterDTO = logisticsCenterDao.selectByPrimaryKey(id);
            BeanCopyUtils.copy(logisticsCenterDTO,logisticsCenterResVo);
          // 根据编码去查询服务范围
           List< LogisticsCenterAreaDTO> logisticsCenterAreaDTOS =logisticsCenterAreaDao.disEnableByLogisticsCenterCode(logisticsCenterResVo.getLogisticsCenterCode());
           logisticsCenterResVo.setLogisticsCenterAreaResVos(BeanCopyUtils.copyList(logisticsCenterAreaDTOS, LogisticsCenterAreaResVo.class));
           return logisticsCenterResVo;

        }catch (Exception e){
            throw new GroundRuntimeException("查询失败");
        }
    }

    /**
     * 请求修改转化实体
     * @param updateLogisticsCenterReqVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    public HttpResponse<Integer> updateLogisticsCenter(UpdateLogisticsCenterReqVo updateLogisticsCenterReqVo) {

        //验证名字是否冲突
        String logisticsCenterName = updateLogisticsCenterReqVo.getLogisticsCenterName();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if(null != authToken){
            companyCode = authToken.getCompanyCode();
        }
        Integer integer = logisticsCenterDao.checkName(logisticsCenterName,updateLogisticsCenterReqVo.getId(),companyCode);
        if(integer>0){
            throw new GroundRuntimeException("物流中心名称不能重复");
        }
        LogisticsCenterDTO  logisticsCenterDTO = new LogisticsCenterDTO();
        BeanCopyUtils.copy(updateLogisticsCenterReqVo,logisticsCenterDTO);
        // 更新物流中心主体
        int k  = ((LogisticsCenterService)AopContext.currentProxy()).updateByPrimaryKeySelective(logisticsCenterDTO);
        if(k>0){
            //  服务区范围转化实体
            try {
                if(CollectionUtils.isNotEmptyCollection(updateLogisticsCenterReqVo.getLogisticsCenterAreaReqVoList())) {
                    List<LogisticsCenterAreaDTO> logisticsCenterAreaDTOS = BeanCopyUtils.copyList(updateLogisticsCenterReqVo.getLogisticsCenterAreaReqVoList(), LogisticsCenterAreaDTO.class);
                    // 分两种情况。有id的执行修改u，没有id的执行添加
                    List<LogisticsCenterAreaDTO> addList = new ArrayList<>();
                    List<LogisticsCenterAreaDTO> updateList = new ArrayList<>();
                    for (LogisticsCenterAreaDTO logisticsCenterAreaDTO : logisticsCenterAreaDTOS) {
                        if (logisticsCenterAreaDTO.getId() == null) {
                            addList.add(logisticsCenterAreaDTO);
                        } else {
                            updateList.add(logisticsCenterAreaDTO);
                        }
                    }
                    if (addList.size() > 0) {
                        //设置关联编码
                        addList.stream().forEach(purchase -> purchase.setLogisticsCenterCode(String.valueOf(logisticsCenterDTO.getLogisticsCenterCode())));
                        //保存新增实体
                        int kp = ((LogisticsCenterService) AopContext.currentProxy()).saveList(addList);
                        if (kp < 1) {
                            throw new GroundRuntimeException("保存新增的物流中心服务区范围失败");
                        }
                    }
                    if (updateList.size() > 0) {
                        //有id的批量更新
                        int kp = ((LogisticsCenterService) AopContext.currentProxy()).updateList(updateList);
                        if (kp < 1) {
                            throw new GroundRuntimeException("修改的物流中心服务区范围失败");
                        }
                    }
                }
                return  HttpResponse.success(k);
            } catch (Exception e) {
                throw new GroundRuntimeException("转化实体出错");
            }
        }
        throw new GroundRuntimeException("修改失败");
    }

    /**
     * 保存物流中心主体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Save
    public int insertSelective(LogisticsCenterDTO record) {
        try{
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                record.setCompanyCode(authToken.getCompanyCode());
                record.setCompanyName(authToken.getCompanyName());
            }
            return logisticsCenterDao.insertSelective(record);
        }catch (Exception e){
            throw new GroundRuntimeException("物流中心新增失败");
        }
    }


    /**
     * 批量插入物流中心服务范围
     * @param logisticsCenterAreaDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @SaveList
    public int saveList(List<LogisticsCenterAreaDTO> logisticsCenterAreaDTOS) {
        try{
            return logisticsCenterAreaDao.saveList(logisticsCenterAreaDTOS);
        }catch (Exception e){
            throw new GroundRuntimeException("物流中心服务区新增失败");
        }
    }


    /**
     * 更新物流中心主体
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @Update
    public int updateByPrimaryKeySelective(LogisticsCenterDTO record) {
        try {
         return logisticsCenterDao.updateByPrimaryKeySelective(record);
        }catch (Exception e){
            throw new GroundRuntimeException("物流中心修改失败");
        }
    }

    /**
     * 批量更新物流中心服务范围
     * @param logisticsCenterAreaDTOS
     * @return
     */
    @Override
    @Transactional(rollbackFor = GroundRuntimeException.class)
    @UpdateList
    public int updateList(List<LogisticsCenterAreaDTO> logisticsCenterAreaDTOS) {
        try {
            return logisticsCenterAreaDao.updateList(logisticsCenterAreaDTOS);
        }catch (Exception e){
            throw new GroundRuntimeException("物流中心服务区修改失败");
        }
    }

    /**
     * 物流中心API
     * @return
     */
    @Override
    public List<LogisticsCenterResVo> getLogisticsCenterAPI(String companyCode) {
        try {
            if(StringUtils.isBlank(companyCode)){
                AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
                if(null != authToken){
                    companyCode = authToken.getCompanyCode();
                }
            }
            List<LogisticsCenterDTO>  logisticsCenterDTOS = logisticsCenterDao.getEnableLogisticsCenterList(companyCode);
            List<LogisticsCenterResVo> logisticsCenterResVos = BeanCopyUtils.copyList(logisticsCenterDTOS,LogisticsCenterResVo.class);
            for (LogisticsCenterResVo vo : logisticsCenterResVos) {
                vo.setLogisticsCenterAreaResVos(BeanCopyUtils.copyList(logisticsCenterAreaDao.enableByLogisticsCenterCode(vo.getLogisticsCenterCode()),LogisticsCenterAreaResVo.class));
            }
            return logisticsCenterResVos;
        } catch (Exception e) {
            throw new GroundRuntimeException("物流中心查询失败");
        }
    }


    /**
     * 查询所有省份并且返回全国信息
     * @return
     */
    @Override
    public HttpResponse getProvinceList() {
        try {
            AreaBasic areaBasic = new AreaBasic();
            areaBasic.setArea_id(LogisticsCenterEnum.CHINA.getStatus());
            areaBasic.setArea_name(LogisticsCenterEnum.CHINA.getName());
            HttpResponse result = areaBasicService.areaTypeInfo(3);
            if (result != null && result.getData()!=null){
                List<AreaBasic> areaBasicList = (List<AreaBasic>)result.getData();
                areaBasicList.add(0,areaBasic);
                return HttpResponse.success(areaBasicList);
            }

            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        } catch (GroundRuntimeException e){
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    /**
     * 查询市
     * @param provinceId
     * @return
     */
    @Override
    public HttpResponse getCityList(String provinceId) {
        try {
            AreaBasic areaBasic = new AreaBasic();
            HttpResponse result = areaBasicService.selectAreaList(provinceId);
            // 如果是北京
            if(LogisticsCenterEnum.BEI_JING.getStatus().equals(provinceId)){
                areaBasic.setParent_id(provinceId);
                areaBasic.setArea_id(LogisticsCenterEnum.BEI_JING_TOTAL.getStatus());
                areaBasic.setArea_name(LogisticsCenterEnum.BEI_JING_TOTAL.getName());
            }
            // 如果是天津
            else if (LogisticsCenterEnum.TIAN_JIN.getStatus().equals(provinceId)){
                areaBasic.setParent_id(provinceId);
                areaBasic.setArea_id(LogisticsCenterEnum.TIAN_JIN_TOTAL.getStatus());
                areaBasic.setArea_name(LogisticsCenterEnum.TIAN_JIN_TOTAL.getName());

            }//如果是上海
            else if (LogisticsCenterEnum.SHANG_HAI.getStatus().equals(provinceId)){
                areaBasic.setParent_id(provinceId);
                    areaBasic.setArea_id(LogisticsCenterEnum.SHANG_HAI_TOTAL.getStatus());
                    areaBasic.setArea_name(LogisticsCenterEnum.SHANG_HAI_TOTAL.getName());
            } // 如果是重亲
            else if (LogisticsCenterEnum.CHONG_QING.getStatus().equals(provinceId)){
                areaBasic.setParent_id(provinceId);
                areaBasic.setArea_id(LogisticsCenterEnum.CHONG_QING_TOTAL.getStatus());
                areaBasic.setArea_name(LogisticsCenterEnum.CHONG_QING_TOTAL.getName());
            }//如果是全国
            else if(LogisticsCenterEnum.CHINA.getStatus().equals(provinceId)){
                List<AreaBasic> areaBasicList = new ArrayList<>();
                areaBasic.setParent_id(provinceId);
                areaBasic.setArea_id(LogisticsCenterEnum.CHINA.getStatus());
                areaBasic.setArea_name(LogisticsCenterEnum.CHINA.getName());
                areaBasicList.add(0,areaBasic);
                return HttpResponse.success(areaBasicList);
            }else {
                areaBasic.setParent_id(provinceId);
                areaBasic.setArea_id(LogisticsCenterEnum.PROVINCE.getStatus());
                areaBasic.setArea_name(LogisticsCenterEnum.PROVINCE.getName());

            }
            if (result != null && result.getData()!=null){
                List<AreaBasic> areaBasicList = (List<AreaBasic>)result.getData();
                areaBasicList.add(0,areaBasic);
                return HttpResponse.success(areaBasicList);
            }

            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        } catch (GroundRuntimeException e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }
}

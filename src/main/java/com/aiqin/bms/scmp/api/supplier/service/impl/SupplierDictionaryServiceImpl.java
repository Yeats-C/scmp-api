package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.abutment.web.SapAbutmentController;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.base.UrlConfig;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryDao;
import com.aiqin.bms.scmp.api.supplier.dao.dictionary.SupplierDictionaryInfoDao;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionary;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierDictionaryInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.dictionary.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.DictionaryType;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryCodeResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryDetailResVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryInfoResponseVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.dictionary.DictionaryListResVO;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierDictionaryInfoMapper;
import com.aiqin.bms.scmp.api.supplier.mapper.SupplierDictionaryMapper;
import com.aiqin.bms.scmp.api.supplier.service.*;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.json.JsonUtil;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dixin
 * @data 2018/11/30 18点06分
 */
@Service
@Slf4j
public class SupplierDictionaryServiceImpl implements SupplierDictionaryService {

    private static Logger LOGGER = LoggerFactory.getLogger(SupplierDictionaryServiceImpl.class);


    @Autowired
    private SupplierDictionaryDao supplierDictionaryDao;
    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private SupplierCommonService supplierCommonService;
    @Autowired
    private SupplierDictionaryInfoDao supplierDictionaryInfoDao;
    @Autowired
    private EncodingRuleService encodingRuleService;
    @Autowired
    private SupplierDictionaryMapper supplierDictionaryMapper;
    @Autowired
    private SupplierEventTriggeringServie supplierEventTriggeringServie;
    @Autowired
    private SupplierDictionaryInfoMapper supplierDictionaryInfoMapper;
    @Autowired
    private SupplierDictionaryInfoService supplierDictionaryInfoService;
    @Autowired
    private UrlConfig urlConfig;

    /**
     * 查看详情
     *
     * @param id
     * @return
     */
    @Override
    public HttpResponse<DictionaryDetailResVO> getListDictionary(Long id,String project) {
        LOGGER.info("查看字典管理详情信息:{}", id+","+project);
        try {
            // 判断id和project不为空的
            projectAndId(id, project);
            DictionaryDetailResVO supplierDictionaryDetailResVO = new DictionaryDetailResVO();
            if (id != null) {
                SupplierDictionary supplierDictionary = supplierDictionaryMapper.selectByPrimaryKey(id);
                if (supplierDictionary != null) {
                    supplierDictionaryDetailResVO.setId(id);
                    supplierDictionaryDetailResVO.setDictionaryName(supplierDictionary.getSupplierDictionaryName());
                    supplierDictionaryDetailResVO.setDictionaryType(supplierDictionary.getSupplierType());
                    supplierDictionaryDetailResVO.setDelFlag(supplierDictionary.getDelFlag());
                    supplierDictionaryDetailResVO.setEnabled(supplierDictionary.getEnabled());
                    String code = supplierDictionary.getSupplierDictionaryCode();
                    if (StringUtils.isNotBlank(code)) {
                        List<DictionaryInfoResponseVO> supplierDictionaryInfoResVOS = supplierDictionaryInfoDao.getCode(code);
                        if (supplierDictionaryInfoResVOS != null && supplierDictionaryInfoResVOS.size() > 0) {
                            supplierDictionaryDetailResVO.setListInfo(supplierDictionaryInfoResVOS);
                        } else {
                            supplierDictionaryDetailResVO.setListInfo(new ArrayList<>());
                        }
                    } else {
                        log.error("关联code 不能为空");
                        throw new GroundRuntimeException("关联code 不能为空");
                    }
                } else {
                    log.error("当前对象不存在");
                    throw new GroundRuntimeException("当前对象不存在");
                }
            }
            return HttpResponse.success(supplierDictionaryDetailResVO);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            return HttpResponse.failure(MessageId.create(Project.SUPPLIER_API, -1, ex.getMessage()));
        }
    }


    /**
     * 添加
     *
     * @param record
     * @return
     */
    @Override
    @Transactional
    @Save
    public Long insertSelective(SupplierDictionary record) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            record.setCompanyCode(authToken.getCompanyCode());
            record.setCompanyName(authToken.getCompanyName());
        }
        int resultNum = supplierDictionaryDao.insertSelective(record);
        if (resultNum > 0) {
            return record.getId();
        } else {
            log.error("新增供应商字典失败");
            throw new GroundRuntimeException("新增供应商字典失败");
        }
    }


    /**
     * 批量添加
     *
     * @param listDTO
     * @return
     */
    @Override
    @Transactional
    public HttpResponse<Integer> batchInsert(DictionarySaveReqDTO listDTO) {
        LOGGER.info("字典管理新增信息:{}", JsonUtil.toJson(listDTO));
        int flg = 0;
        String project=listDTO.getDictionaryType();
        try {
//            if (project == null) {
//                log.error("project 不能为空");
//                throw new GroundRuntimeException("project 不能为空");
//            }
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            String companyCode = "";
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            // 通过字典名判断表里数据是否重复
            String codNa = listDTO.getDictionaryName();
//            Integer integer = supplierDictionaryDao.checkName(codNa, null,companyCode);
//                if (integer > 0) {
//                log.error("字典名称不能重复");
//                throw new GroundRuntimeException("字典名称不能重复");
//            }
            // 查询供应商字典表 (编码要手动填写 不用自动生成)
//            EncodingRule encodingRule = encodingRuleService.getNumberingType(EncodingRuleType.SUPPLIER_DICTIONARY_CODE);
//            long code = encodingRule.getNumberingValue();
//            encodingRuleService.updateNumberValue(code, encodingRule.getId());
            String code = listDTO.getDictionaryCode();
            Integer integer = supplierDictionaryDao.checkCode(code, null,companyCode);
            if (integer > 0) {
                log.error("字典编码不能重复");
                LOGGER.info("字典编码不能重复:{}", code);
                throw new GroundRuntimeException("字典编码不能重复");
            }

            // 获取保存数据进行保存
            List<SupplierDictionaryInfo> save = new LinkedList<>();
            SupplierDictionary supplierDictionary = new SupplierDictionary();
            supplierDictionary.setSupplierDictionaryCode(code);
            supplierDictionary.setSupplierDictionaryName(codNa);
            supplierDictionary.setSupplierType(listDTO.getDictionaryType());
            supplierDictionary.setEnabled(HandlingExceptionCode.ZERO);
            supplierDictionary.setDelFlag(HandlingExceptionCode.ZERO);
            ((SupplierDictionaryService) AopContext.currentProxy()).insertSelective(supplierDictionary);
            // 详情数据不为空 添加详情数据
            if (listDTO.getListInfo() != null && listDTO.getListInfo().size() > 0) {
                listDTO.getListInfo().forEach(infoReqVO -> {
                    SupplierDictionaryInfo supplierDic1 = new SupplierDictionaryInfo();
                    // 查询供应商字典详情表
                    EncodingRule encodingRule1 = encodingRuleService.getNumberingType(EncodingRuleType.SUPPLIER_DICTIONARY_INFO_CODE);
                    long coif = encodingRule1.getNumberingValue();
                    encodingRuleService.updateNumberValue(coif, encodingRule1.getId());
                    supplierDic1.setSupplierContent(infoReqVO.getDictionaryContent());
                    supplierDic1.setSupplierDictionaryCode(code);
                    supplierDic1.setSupplierDictionaryName(codNa);
                    supplierDic1.setEnabled(HandlingExceptionCode.ZERO);
                    supplierDic1.setDelFlag(HandlingExceptionCode.ZERO);
                    supplierDic1.setSupplierDictionaryValue(infoReqVO.getDictionaryValue());
                    supplierDic1.setSupplierDictionaryInfoCode(coif+"");
                    supplierDic1.setSupplierWeight(infoReqVO.getDictionaryWeight());
                    save.add(supplierDic1);
                });
            }
            if (save.size() > 0) {
                flg = ((SupplierDictionaryService) AopContext.currentProxy()).insertList(save);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
        return HttpResponse.success(flg);
    }

    /**
     * 批量修改
     *
     * @param listDTO
     * @return
     */
    @Override
    @Transactional
    public HttpResponse<Integer> batchUpdate(DictionaryUpdateReqDTO listDTO) {
        String project=listDTO.getDictionaryType();
        int flg = 0;
        Long dId = listDTO.getId();
        // 判断id和project不为空的
        projectAndId(dId, project);
        try {
            String companyCode = "";
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                companyCode = authToken.getCompanyCode();
            }
            String codNa = listDTO.getDictionaryName();
            // 字典名称不能重复 要换成通过code去判断
//            Integer integer = supplierDictionaryDao.checkName(codNa, dId,companyCode);
//            if (integer > 0) {
//                log.error("字典名称不能重复");
//                throw new GroundRuntimeException("字典名称不能重复");
//            }
            // 通过code去判断编码是否重复
            String codCo = listDTO.getDictionaryCode();
            Integer integer = supplierDictionaryDao.checkCode(codCo, dId,companyCode);
            if (integer > 0) {
                log.error("字典编码不能重复");
                LOGGER.info("字典编码不能重复:{}", codCo);
                throw new GroundRuntimeException("字典编码不能重复");
            }
//            String code = null;
            // 详情表存在的情况下修改 不存在的话新增
            List<SupplierDictionaryInfo> save = new LinkedList<>();
            List<SupplierDictionaryInfo> update = new LinkedList<>();
            if (dId != null) {
                SupplierDictionary supplierDictionary = supplierDictionaryMapper.selectByPrimaryKey(dId);
                SupplierDictionary supplierDictionary1 = new SupplierDictionary();
//                code = supplierDictionary.getSupplierDictionaryCode();
                supplierDictionary1.setSupplierDictionaryName(codNa);
                supplierDictionary1.setSupplierType(listDTO.getDictionaryType());
                supplierDictionary1.setDelFlag(listDTO.getDelFlag());
                supplierDictionary1.setId(dId);
                supplierDictionary1.setSupplierDictionaryCode(codCo);
                supplierDictionary1.setCreateBy(supplierDictionary.getCreateBy());
                supplierDictionary1.setCreateTime(supplierDictionary.getCreateTime());
                supplierDictionary1.setUpdateBy(supplierDictionary.getUpdateBy());
                supplierDictionary1.setUpdateTime(supplierDictionary.getUpdateTime());
                supplierDictionary1.setEnabled(listDTO.getEnabled());
                LOGGER.info("更新字典编码主表:{}", supplierDictionary1);
                ((SupplierDictionaryService) AopContext.currentProxy()).updateDictionary(supplierDictionary1);
                String finalCode = codCo;
                if (listDTO.getListInfo() != null && listDTO.getListInfo().size() > 0) {
                    listDTO.getListInfo().forEach(infoReqVO -> {
                        if (infoReqVO.getId() != null && infoReqVO.getId() != 0) {
                            SupplierDictionaryInfo supplierDic = supplierDictionaryInfoMapper.selectByPrimaryKey(infoReqVO.getId());
                            supplierDic.setId(infoReqVO.getId());
                            supplierDic.setEnabled(infoReqVO.getEnabled());
                            supplierDic.setSupplierContent(infoReqVO.getDictionaryContent());
                            supplierDic.setSupplierDictionaryCode(finalCode);
                            supplierDic.setSupplierDictionaryName(codNa);
                            supplierDic.setSupplierDictionaryValue(infoReqVO.getDictionaryDictionaryValue());
                            supplierDic.setSupplierWeight(infoReqVO.getDictionaryWeight());
                            update.add(supplierDic);
                        } else {
                            EncodingRule encodingRule1 = encodingRuleService.getNumberingType(EncodingRuleType.SUPPLIER_DICTIONARY_INFO_CODE);
                            long coif = encodingRule1.getNumberingValue();
                            encodingRuleService.updateNumberValue(coif, encodingRule1.getId());
                            SupplierDictionaryInfo supplierDic1 = new SupplierDictionaryInfo();
                            supplierDic1.setSupplierContent(infoReqVO.getDictionaryContent());
                            supplierDic1.setSupplierDictionaryCode(finalCode);
                            supplierDic1.setSupplierDictionaryName(codNa);
                            supplierDic1.setSupplierDictionaryValue(infoReqVO.getDictionaryDictionaryValue());
                            supplierDic1.setSupplierWeight(infoReqVO.getDictionaryWeight());
                            supplierDic1.setEnabled(HandlingExceptionCode.ZERO);
                            supplierDic1.setDelFlag(HandlingExceptionCode.ZERO);
                            supplierDic1.setSupplierDictionaryInfoCode(coif+"");
                            save.add(supplierDic1);
                        }
                    });
                }
            }
            if (save.size() > 0) {
                LOGGER.info("新增编辑新加字典编码详情表:{}", save);
                flg = ((SupplierDictionaryService) AopContext.currentProxy()).insertList(save);
            }
            if (update.size() > 0) {
                LOGGER.info("更新编辑字典编码详情表:{}", update);
                flg = ((SupplierDictionaryService) AopContext.currentProxy()).updateList(update);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new GroundRuntimeException(ex.getMessage());
        }
        return HttpResponse.success(flg);
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public HttpResponse<Integer> offOrOn(Long id,String project) {
        LOGGER.info("删除字典编码参数:{}", id+","+project);
        int resultNum = 0;
        // 判断id和project不为空的
        projectAndId(id, project);
        try {
            SupplierDictionary supplierDictionary = supplierDictionaryMapper.selectByPrimaryKey(id);
            if (supplierDictionary != null) {
                String code = supplierDictionary.getSupplierDictionaryCode();
                supplierDictionary.setDelFlag(StatusTypeCode.DEL_FLAG.getStatus());
                if (StringUtils.isNotBlank(code)) {
                    List<SupplierDictionaryInfo> supplierDictionaries = supplierDictionaryInfoService.getList(code);
                    supplierDictionaryInfoDao.deleteList(code);
                    resultNum = supplierDictionaryDao.offOrOn(id);
                } else {
                    log.error("code 关联不存在");
                    LOGGER.info("code 关联不存在:{}", code);
                    throw new GroundRuntimeException("code 关联不存在");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GroundRuntimeException(e.getMessage());
        }
        return HttpResponse.success(resultNum);
    }

    /**
     * 分页条件查询
     *
     * @param querDictionaryReqVO
     * @return
     */
    @Override
    public  HttpResponse<BasePage<DictionaryListResVO>> listDictionary(QueryDictionaryReqVO querDictionaryReqVO,String project) {
//        if (project == null) {
//            throw new GroundRuntimeException("project 不能为空");
//        }
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if (null != authToken) {
            querDictionaryReqVO.setCompanyCode(authToken.getCompanyCode());
        }
        PageHelper.startPage(querDictionaryReqVO.getPageNo(), querDictionaryReqVO.getPageSize());
        List<DictionaryListResVO> querySupplierDictionaryResVOS = supplierDictionaryDao.listDictionary(querDictionaryReqVO);
        return HttpResponse.success(PageUtil.getPageList(querDictionaryReqVO.getPageNo(), querySupplierDictionaryResVOS));
    }

    /**
     * 修改
     *
     * @param
     * @return
     */
    @Override
    @Transactional
    @Update
    public int updateDictionary(SupplierDictionary supplierDictionary) {
        String neme = supplierDictionary.getSupplierDictionaryName();
        Long id = supplierDictionary.getId();
        if (StringUtils.isNotBlank(neme) && id != null) {
            SupplierDictionary newSupplier = supplierDictionaryMapper.selectByPrimaryKey(id);
            // name不同改变 通过code改详情表name
            if (!neme.equals(newSupplier.getSupplierDictionaryName())) {
                supplierEventTriggeringServie.pushSuplier(DataBaseType.SUPPLIER_DICTIONARY, newSupplier.getSupplierDictionaryCode(), neme);
            }
        }
        // 更新供应商字典主表
        int k = supplierDictionaryMapper.updateByPrimaryKeySelective(supplierDictionary);
        if (k > 0) {
            return k;
        } else {
            log.error("更新失败");
            throw new GroundRuntimeException("更新失败");
        }
    }

    @Override
    @Transactional
    public HttpResponse<Integer> enabled(EnabledSave enabledSave,String project) {
        Long id = enabledSave.getId();
        Byte status = enabledSave.getStatus();
        // 判断id和project不为空的
        projectAndId(id, project);
        SupplierDictionary supplierDictionary = supplierDictionaryMapper.selectByPrimaryKey(id);
        if (supplierDictionary != null) {
            String code = supplierDictionary.getSupplierDictionaryCode();
            supplierDictionary.setEnabled(status);
             supplierDictionaryInfoDao.infoEnabled(status,code);
            return HttpResponse.success(supplierDictionaryMapper.updateByPrimaryKeySelective(supplierDictionary));
        } else {
            log.error("字典不能为空");
            throw new GroundRuntimeException("字典不能为空");
        }


    }

    @Override
    @SaveList
    @Transactional
    public int insertList(Collection<SupplierDictionaryInfo> list) {
        int k = supplierDictionaryInfoDao.insertList(list);
        if (k > 0) {
            return k;
        } else {
            log.error("批量插入详情失败");
            throw new GroundRuntimeException("批量插入详情失败");
        }
    }

    @Override
    @Transactional
    @UpdateList
    public int updateList(Collection<SupplierDictionaryInfo> list) {
        int k = supplierDictionaryInfoDao.updateList(list);
        if (k > 0) {
            return k;
        } else {
            log.error("批量修改详情失败");
            throw new GroundRuntimeException("批量修改详情失败");
        }
    }



    @Override
    public List<DictionaryType> getType() {
        List<DictionaryType> dictionaryTypes = new ArrayList<>();
        DictionaryType dictionaryType = new DictionaryType();
        dictionaryType.setDictionaryType("supplyChain");
        dictionaryType.setDictionaryTypeName("供应链");
        DictionaryType dictionaryType1 = new DictionaryType();
        dictionaryType1.setDictionaryType("logistics");
        dictionaryType1.setDictionaryTypeName("物流,商品");
        DictionaryType dictionaryType2 = new DictionaryType();
        dictionaryType2.setDictionaryType("purchase");
        dictionaryType2.setDictionaryTypeName("采购");
        dictionaryTypes.add(dictionaryType);
        dictionaryTypes.add(dictionaryType1);
        dictionaryTypes.add(dictionaryType2);
        return dictionaryTypes;
    }

    @Override
    public List<DictionaryCodeResVo> getCode(DictionaryInfoReqVO dictionaryInfoReqVO,String dictionaryType) {
        try {
            LOGGER.info("通过name/code查询详情:{}", JsonUtil.toJson(dictionaryInfoReqVO));
            AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
            if(null != authToken){
                dictionaryInfoReqVO.setCompanyCode(authToken.getCompanyCode());
            }
            return supplierDictionaryDao.getCode(dictionaryInfoReqVO);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BizException(ResultCode.DICTIONARY_CODE);
        }
    }

    @Override
    public void projectAndId(Long id,String project) {
        if (id == null) {
            log.error("id=>不能为空");
            throw new GroundRuntimeException("id 不能为空");
        }
//        if (project == null) {
//            log.error("project=>不能为空");
//            throw new GroundRuntimeException("project 不能为空");
//        }
    }
    @Override
    public void handleException(HttpResponse httpResponse) {
        if (!HandlingExceptionCode.RESULTTYPE.equals(httpResponse.getCode())) {
            throw new GroundRuntimeException(httpResponse.getMessage());
        }
    }
}

package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.bms.scmp.api.supplier.dao.EncodingRuleDao;
import com.aiqin.bms.scmp.api.config.AuthenticationInterceptor;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.EncodingRuleType;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.*;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.EncodingRule;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.TagInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.QueryTagRespVo;
import com.aiqin.bms.scmp.api.supplier.mapper.TagInfoMapper;
import com.aiqin.bms.scmp.api.supplier.mapper.UseTagRecordMapper;
import com.aiqin.bms.scmp.api.supplier.service.TagInfoService;
import com.aiqin.bms.scmp.api.util.AuthToken;
import com.aiqin.bms.scmp.api.util.BeanCopyUtils;
import com.aiqin.bms.scmp.api.util.CollectionUtils;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author knight.xie
 * @version 1.0
 * @className TagInfoServiceImpl
 * @date 2019/4/29 14:59
 * @description TODO
 */
@Service
@Slf4j
public class TagInfoServiceImpl implements TagInfoService {

    @Autowired
    private TagInfoMapper tagInfoMapper;

    @Autowired
    private EncodingRuleDao encodingRuleDao;

    @Autowired
    private UseTagRecordMapper useTagRecordMapper;
    /**
     * 查询List分页
     *
     * @param reqVo
     * @return
     */
    @Override
    public BasePage<QueryTagRespVo> getList(QueryTagReqVo reqVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        PageHelper.startPage(reqVo.getPageNo(),reqVo.getPageSize());
        List<QueryTagRespVo> list = tagInfoMapper.getList(reqVo);
        return PageUtil.getPageList(reqVo.getPageNo(),list);
    }

    /**
     * 查询所有启用
     *
     * @return
     */
    @Override
    public List<QueryTagRespVo> getAll(String tagTypeCode) {
        QueryTagReqVo reqVo = new QueryTagReqVo();
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            reqVo.setCompanyCode(authToken.getCompanyCode());
        }
        reqVo.setTagTypeCode(tagTypeCode);
        reqVo.setEnable(StatusTypeCode.EN_ABLE.getStatus());
        return tagInfoMapper.getList(reqVo);
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Override
    public QueryTagRespVo view(Long id) {
        if(Objects.isNull(id)) {
            throw new BizException(ResultCode.ID_EMPTY);
        }
        TagInfo tagInfo = tagInfoMapper.selectByPrimaryKey(id);
        if(Objects.isNull(tagInfo)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        QueryTagRespVo respVo = new QueryTagRespVo();
        BeanCopyUtils.copy(tagInfo,respVo);
        respVo.setEnableName();
        List<DetailTagUseRespVo> tagUseRespVos = useTagRecordMapper.getListByTagCode(respVo.getTagCode());
        respVo.setTagUseRespVos(tagUseRespVos);
        return respVo;
    }

    /**
     * 新增
     *
     * @param addVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<QueryTagRespVo> add(AddTagReqVo addVo) {
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if (null != authToken) {
            companyCode = authToken.getCompanyCode();
        }
        Integer count = tagInfoMapper.checkName(addVo.getTagName(), null, companyCode);
        if(count>0) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API, 63,
                    addVo.getTagName()+"名称已存在,请重新输入"));
        }
        TagInfo tagInfo = new TagInfo();
        BeanCopyUtils.copy(addVo,tagInfo);
        //设置编码
        EncodingRule encodingRule = encodingRuleDao.getNumberingType(EncodingRuleType.TAG_INFO_CODE);
        tagInfo.setTagCode(String.valueOf(encodingRule.getNumberingValue()));
        // 更新编码
        encodingRuleDao.updateNumberValue(encodingRule.getNumberingValue(),encodingRule.getId());
        tagInfo.setEnable(StatusTypeCode.EN_ABLE.getStatus());
        tagInfo.setDelFlag(StatusTypeCode.UN_DEL_FLAG.getStatus());
        ((TagInfoService) AopContext.currentProxy()).insertSelective(tagInfo);
        return getAll(addVo.getTagTypeCode());
    }

    /**
     * 修改
     *
     * @param updateVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(UpdateTagReqVo updateVo) {
        if(Objects.isNull(updateVo.getId())) {
            throw new BizException(ResultCode.ID_EMPTY);
        }
        TagInfo tagInfo = tagInfoMapper.selectByPrimaryKey(updateVo.getId());
        if(Objects.isNull(tagInfo)){
            throw new BizException(ResultCode.OBJECT_EMPTY);
        }
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        String companyCode = "";
        if (null != authToken) {
            companyCode = authToken.getCompanyCode();
        }
        Integer count = tagInfoMapper.checkName(updateVo.getTagName(), updateVo.getId(), companyCode);
        if(count>0) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API, 63,
                    updateVo.getTagName()+"名称已存在,请重新输入"));
        }
        BeanCopyUtils.copy(updateVo,tagInfo);
        return ((TagInfoService) AopContext.currentProxy()).updateByPrimaryKeySelective(tagInfo);
    }

    @Save
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer insertSelective(TagInfo tagInfo){
        AuthToken authToken = AuthenticationInterceptor.getCurrentAuthToken();
        if(null != authToken){
            tagInfo.setCompanyCode(authToken.getCompanyCode());
            tagInfo.setCompanyName(authToken.getCompanyName());
        }
        return  tagInfoMapper.insertSelective(tagInfo);
    }

    @Update
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateByPrimaryKeySelective(TagInfo tagInfo){
        return  tagInfoMapper.updateByPrimaryKeySelective(tagInfo);
    }


    /**
     * 更新使用数量
     *
     * @param reqVos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateUseNum(List<UpdateUseNumReqVo> reqVos) {
        log.info("需要更新使用数量的数据:{}", JSON.toJSON(reqVos));
        if (CollectionUtils.isEmptyCollection(reqVos)) {
            throw new BizException(MessageId.create(Project.SUPPLIER_API, 63,
                    "需要更新的数据不存在"));
        }
        //过滤掉编码或数量为空的数据,只保留
        reqVos = reqVos.stream().filter(req -> StringUtils.isNotBlank(req.getTagCode())
                && null != req.getChangeNum()).collect(Collectors.toList());

        log.info("需要更新使用数量的数据:{}", JSON.toJSON(reqVos));
        return tagInfoMapper.updateUseNum(reqVos);
    }


    /**
     * 保存
     *
     * @param records
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveRecordList(List<SaveUseTagRecordReqVo> records) {
        log.info("原始数据:{}",JSON.toJSON(records));
        for (SaveUseTagRecordReqVo record : records) {
            ArrayList<SaveUseTagRecordItemReqVo> reqVos = record.getItemReqVos().stream()
                    .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(f -> f.getTagCode()))), ArrayList::new));
            record.setItemReqVos(reqVos);
        }

        log.info("去重后的数据:{}",JSON.toJSON(records));
        //需要新增的数据
        List<UseTagRecordReqVo> addList = Lists.newArrayList();
        //需要修改的数据/不动的数据
        List<UseTagRecordReqVo> updateList = Lists.newArrayList();
        //需要删除的数据
        List<UseTagRecordReqVo> delList = Lists.newArrayList();
        //对数据进行分类
        for (SaveUseTagRecordReqVo record : records) {
            try {
                List<UseTagRecordReqVo> reqVos =  BeanCopyUtils.copyList(record.getItemReqVos(),UseTagRecordReqVo.class);
                reqVos.forEach(item->{
                    item.setUseObjectCode(record.getUseObjectCode());
                    item.setUseObjectName(record.getUseObjectName());
                    item.setTagTypeCode(record.getTagTypeCode());
                    item.setTagTypeName(record.getTagTypeName());
                    item.setSourceCode(record.getSourceCode());
                });
                checkUseTagRecord(record.getUseObjectCode(),record.getTagTypeCode(),reqVos,addList,updateList,delList);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException(MessageId.create(Project.SUPPLIER_API, 63,
                        "系统错误"));
            }
        }
        log.info("需要新增的数据:{}",JSON.toJSON(addList));
        log.info("需要修改的数据/不动的数据:{}",JSON.toJSON(updateList));
        log.info("需要删除的数据:{}",JSON.toJSON(delList));
        List<UpdateUseNumReqVo> reqVos = Lists.newArrayList();
        int num = 0;
        if (CollectionUtils.isNotEmptyCollection(addList)) {
            num += ((TagInfoService) AopContext.currentProxy()).insertRecordSelective(addList);
            addList.forEach(item->{
                UpdateUseNumReqVo reqVo = new UpdateUseNumReqVo();
                reqVo.setChangeNum(1);
                reqVo.setTagCode(item.getTagCode());
                reqVos.add(reqVo);
            });
        }

        if (CollectionUtils.isNotEmptyCollection(delList)) {
            num += ((TagInfoService) AopContext.currentProxy()).updateRecordSelective(delList);
            delList.forEach(item->{
                UpdateUseNumReqVo reqVo = new UpdateUseNumReqVo();
                reqVo.setChangeNum(-1);
                reqVo.setTagCode(item.getTagCode());
                reqVos.add(reqVo);
            });
        }
        if (CollectionUtils.isNotEmptyCollection(reqVos)) {
            updateUseNum(reqVos);
        }


        return  num;
    }

    /**
     * 保存标签使用记录,同一个使用对象可以重复打标签
     *
     * @param records
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveRecordListRepeat(List<UseTagRecordReqVo> records) {
        log.info("原始数据:{}",JSON.toJSON(records));
        int num = 0;
        if (CollectionUtils.isNotEmptyCollection(records)) {
            List<UpdateUseNumReqVo>  updateUseNumReqVos = Lists.newArrayList();
            num += ((TagInfoService) AopContext.currentProxy()).insertRecordSelective(records);
            records.forEach(reqVo->{
                UpdateUseNumReqVo updateUseNumReqVo = new UpdateUseNumReqVo();
                updateUseNumReqVo.setChangeNum(1);
                updateUseNumReqVo.setTagCode(reqVo.getTagCode());
                updateUseNumReqVos.add(updateUseNumReqVo);
            });
            if (CollectionUtils.isNotEmptyCollection(updateUseNumReqVos)) {
                updateUseNum(updateUseNumReqVos);
            }
        }
        return num;
    }

    /**
     * 插入标签使用记录
     *
     * @param records
     * @return
     */
    @Override
    @SaveList
    @Transactional(rollbackFor = Exception.class)
    public Integer insertRecordSelective(List<UseTagRecordReqVo> records) {
        return  useTagRecordMapper.insertBatch(records);
    }

    /**
     * 修改标签使用记录
     *
     * @param records
     * @return
     */
    @Override
    @UpdateList
    @Transactional(rollbackFor = Exception.class)
    public Integer updateRecordSelective(List<UseTagRecordReqVo> records) {
        return  useTagRecordMapper.updateBatch(records);
    }

    /**
     * 检查数据
     * @param useObjectCode
     * @param tagTypeCode
     * @param reqVos
     * @param addList
     * @param updateList
     * @param delList
     */
    private void checkUseTagRecord(String useObjectCode, String tagTypeCode, List<UseTagRecordReqVo> reqVos, List<UseTagRecordReqVo> addList,
                                   List<UseTagRecordReqVo> updateList, List<UseTagRecordReqVo> delList){
        //根据使用对象编码查询标签信息,如果没有数据,则全部新增,有数据则需要比较,哪些是修改,哪些是删除
        List<UseTagRecordReqVo> useTagRecords = useTagRecordMapper.getUseTagByUseObjectCode(useObjectCode,tagTypeCode);
        if (CollectionUtils.isEmptyCollection(useTagRecords)) {
            addList.addAll(reqVos);
        } else {
            //新增的
            List<UseTagRecordReqVo> collect = reqVos.stream().filter(item -> !useTagRecords.contains(item)).collect(Collectors.toList());
            addList.addAll(collect);
            //不动的数据
            reqVos.removeAll(collect);
            updateList.addAll(reqVos);
            //删除的
            List<UseTagRecordReqVo> collect1 = useTagRecords.stream().filter(item -> !reqVos.contains(item)).collect(Collectors.toList());
            delList.addAll(collect1);
        }
    }

    /**
     * 根据使用对象和标签类型查询标签使用记录
     *
     * @param useObjectCode
     * @param tagTypeCode
     * @return
     */
    @Override
    public List<DetailTagUseRespVo> getUseTagRecordByUseObjectCode(String useObjectCode, String tagTypeCode) {
        return useTagRecordMapper.getListByUseObjectCode(useObjectCode,tagTypeCode);
    }

    /**
     * 根据使用对象和标签类型查询标签使用记录
     *
     * @param useObjectCode
     * @param tagTypeCode
     * @return
     */
    @Override
    public List<DetailTagUseRespVo> getUseTagRecordByUseObjectCode2(String useObjectCode, String tagTypeCode) {
        return useTagRecordMapper.getListByUseObjectCode2(useObjectCode,tagTypeCode);
    }

    /**
     * 根据来源单号和标签类型查询标签使用记录
     *
     * @param sourceCode
     * @param tagTypeCode
     * @return
     */
    @Override
    public List<DetailTagUseRespVo> getUseTagRecordBySourceCode(String sourceCode, String tagTypeCode) {
        return useTagRecordMapper.getListBySourceCode(sourceCode,tagTypeCode);
    }
}

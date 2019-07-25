package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.TagInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.*;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.QueryTagRespVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @date 2019/4/29 14:36
 * @description TODO
 */
public interface TagInfoService {

    /**
     * 查询List分页
     * @param reqVo
     * @return
     */
    BasePage<QueryTagRespVo> getList(QueryTagReqVo reqVo);


    /**
     * 查询所有启用
     * @return
     */
    List<QueryTagRespVo> getAll(String tagTypeCode);


    /**
     * 根据Id查询
     * @param id
     * @return
     */
    QueryTagRespVo view(Long id);

    /**
     * 新增
     * @param addVo
     * @return
     */
    List<QueryTagRespVo> add(AddTagReqVo addVo);

    /**
     * 修改
     * @param updateVo
     * @return
     */
    Integer update(UpdateTagReqVo updateVo);

    /**
     * 插入
     * @param tagInfo
     * @return
     */
    Integer insertSelective(TagInfo tagInfo);

    /**
     * 修改
     * @param tagInfo
     * @return
     */
    Integer updateByPrimaryKeySelective(TagInfo tagInfo);

    /**
     * 更新使用数量
     * @param reqVos
     * @return
     */
    Integer updateUseNum(List<UpdateUseNumReqVo> reqVos);

    /**
     * 保存标签使用记录,同一个使用对象只能打一次
     * @param records
     * @return
     */
    Integer saveRecordList(List<SaveUseTagRecordReqVo> records);


    /**
     * 保存标签使用记录,同一个使用对象可以重复打标签
     * @param records
     * @return
     */
    Integer saveRecordListRepeat(List<UseTagRecordReqVo> records);

    /**
     * 插入标签使用记录
     * @param records
     * @return
     */
    Integer insertRecordSelective(List<UseTagRecordReqVo> records);

    /**
     * 修改标签使用记录
     * @param records
     * @return
     */
    Integer updateRecordSelective(List<UseTagRecordReqVo> records);

    /**
     * 根据使用对象和标签类型查询标签使用记录
     * @param useObjectCode
     * @param tagTypeCode
     * @return
     */
    List<DetailTagUseRespVo> getUseTagRecordByUseObjectCode(String useObjectCode, String tagTypeCode);

    /**
     * 根据使用对象和标签类型查询标签使用记录
     * @param useObjectCode
     * @param tagTypeCode
     * @return
     */
    List<DetailTagUseRespVo> getUseTagRecordByUseObjectCode2(String useObjectCode, String tagTypeCode);

    /**
     * 根据来源单号和标签类型查询标签使用记录
     * @param sourceCode
     * @param tagTypeCode
     * @return
     */
    List<DetailTagUseRespVo> getUseTagRecordBySourceCode(String sourceCode, String tagTypeCode);

}

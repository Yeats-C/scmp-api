package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.TagInfo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.QueryTagReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.UpdateUseNumReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.QueryTagRespVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TagInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TagInfo record);

    int insertSelective(TagInfo record);

    TagInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TagInfo record);

    int updateByPrimaryKey(TagInfo record);


    List<QueryTagRespVo> getList(QueryTagReqVo reqVo);

    Integer checkName(@Param("name") String name, @Param("id") Long id, @Param("companyCode") String companyCode);

    Integer updateUseNum(List<UpdateUseNumReqVo> reqVos);
    @MapKey("tagName")
    Map<String, TagInfo> selectByTagNames(@Param("list") Set<String> skuTagList, @Param("companyCode") String companyCode);
}
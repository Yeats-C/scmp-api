package com.aiqin.bms.scmp.api.supplier.mapper;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.UseTagRecord;
import com.aiqin.bms.scmp.api.supplier.domain.request.tag.UseTagRecordReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.tag.DetailTagUseRespVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UseTagRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UseTagRecord record);

    int insertSelective(UseTagRecord record);

    UseTagRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UseTagRecord record);

    int updateByPrimaryKey(UseTagRecord record);

    List<UseTagRecordReqVo> getUseTagByUseObjectCode(@Param("useObjectCode") String useObjectCode, @Param("tagTypeCode") String tagTypeCode);

    List<DetailTagUseRespVo> getListByUseObjectCode(@Param("useObjectCode") String useObjectCode, @Param("tagTypeCode") String tagTypeCode);
    List<DetailTagUseRespVo> getListByUseObjectCode2(@Param("useObjectCode") String useObjectCode, @Param("tagTypeCode") String tagTypeCode);

    List<DetailTagUseRespVo> getListBySourceCode(@Param("sourceCode") String useObjectCode, @Param("tagTypeCode") String tagTypeCode);

    List<DetailTagUseRespVo> getListByTagCode(String tagCode);

    int insertBatch(List<UseTagRecordReqVo> records);
    int updateBatch(List<UseTagRecordReqVo> records);
}
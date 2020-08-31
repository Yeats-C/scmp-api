package com.aiqin.bms.scmp.api.abutment.dao;

import com.aiqin.bms.scmp.api.abutment.domain.DlOtherInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DlOtherInfoDao {

    Integer insert(DlOtherInfo record);

    Integer update(DlOtherInfo record);

    DlOtherInfo selectOtherInfo(DlOtherInfo dlOtherInfo);

    List<String> selectByCodes(@Param("list") List<String> codes,
                               @Param("documentType") Integer documentType,
                               @Param("stockType") Integer stockType);

}
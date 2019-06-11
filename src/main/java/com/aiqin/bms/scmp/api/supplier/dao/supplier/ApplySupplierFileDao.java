package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplierFile;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplierFileReqDTO;

import java.util.List;

/**
 * @功能说明:供应商文件
 * @author: wangxu
 * @date: 2018/12/18 0018 11:22
 */
public interface ApplySupplierFileDao {
    /**
     * 批量新增供应商文件
     * @param supplierFileReqDTOList
     * @return
     */
    int insertList(List<SupplierFileReqDTO> supplierFileReqDTOList);

    /**
     * 根据申请编码获取文件列表
     * @param applyCode
     * @return
     */
    List<ApplySupplierFile> getApplySupplierFile(String applyCode);

    /**
     * 根据申请编码删除文件列表
     * @param applyCode
     * @return
     */
    int deleteApplySupplierFileByApplyCode(String applyCode);


}

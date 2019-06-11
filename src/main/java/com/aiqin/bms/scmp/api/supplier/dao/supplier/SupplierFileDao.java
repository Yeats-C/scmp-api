package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierFile;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplierFileReqDTO;

import java.util.List;

/**
 * @功能说明:供应商文件
 * @author: wangxu
 * @date: 2018/12/18 0018 11:22
 */
public interface SupplierFileDao {
    /**
     * 批量新增供应商文件
     * @param supplierFileReqDTOList
     * @return
     */
    int insertList(List<SupplierFileReqDTO> supplierFileReqDTOList);

    /**
     * 根据编码获取文件列表
     * @param code
     * @return
     */
    List<SupplierFile> getSupplierFile(String code);

    /**
     * 根据供应商编码删除文件
     * @param supplierCode
     * @return
     */
    int deleteFile(String supplierCode);

    /**
     * 新增供应商文件正式数据返回主键id
     * @param supplierFile
     * @return
     */
    int insert(SupplierFile supplierFile);

    int insertFileList(List<SupplierFile> supplierFiles);


}

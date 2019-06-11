package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplierFile;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplierFileReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.SupplierFileReqVO;

import java.util.List;

/**
 * @功能说明: 供应商申请文件service
 * @author: wangxu
 * @date: 2018/12/18 0018 10:58
 */
public interface ApplySupplierFileService {
    /**
     * 复制新增对象
     * @param supplierFileReqVOS
     * @return
     */
    int copySaveInfo(List<SupplierFileReqVO> supplierFileReqVOS);

    /**
     * 批量新增
     * @param supplierFileReqDTOS
     * @return
     */
    int insertList(List<SupplierFileReqDTO> supplierFileReqDTOS);


    /**
     * 执行sql新增
     * @param supplierFile
     * @return
     */
    int insert(SupplierFile supplierFile);
}

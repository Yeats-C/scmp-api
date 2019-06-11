package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.Supplier;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.DownSupplierFileReq;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplierDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplierListRespVO;

import java.util.List;


/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 11:03
 */
public interface SupplierService {


    /**
     * 获取供应商列表
     * @param querySupplierReqVO
     * @return
     */
    BasePage<SupplierListRespVO> getSupplierList(QuerySupplierReqVO querySupplierReqVO);


    /**
     * 调用dao层修改数据
     * @param supplier
     * @return
     */
    int update(Supplier supplier);

    /**
     * 根据ID获取详情
     * @param id
     * @return
     */
    SupplierDetailRespVO getSupplierDetail(Long id);

    /**
     * 调用dao层新增数据
     * @param supplier
     * @return
     */
    int insert(Supplier supplier);

    /**
     * 添加文件下载日志
     * @param downSupplierFileReq
     * @return
     */
    int addSupplierFileDownLog(DownSupplierFileReq downSupplierFileReq);

    /**
     * 供应集团列表查询
     * @param querySupplierReqVO
     * @return
     */
    List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);
}

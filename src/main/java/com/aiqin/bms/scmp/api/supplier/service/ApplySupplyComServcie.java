package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.excel.SupplierImportResp;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplyComDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplyCompanyReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.CancelApplySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComListRespVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @功能说明: 申请供货单位
 * @author: wangxu
 * @date: 2018/12/3 0003 16:40
 */
public interface ApplySupplyComServcie {
    /**
     * 编码，数据设置
     * @param applySupplyCompanyReqVO
     * @return
     */
    HttpResponse<Integer> saveApply(ApplySupplyCompanyReqVO applySupplyCompanyReqVO);

    /**
     * 对象复制，数据注入
     * @param applySupplyCompanyReqVO
     * @return
     */
    int updateApply(ApplySupplyCompanyReqVO applySupplyCompanyReqVO);

    /**
     * 调用DAO层修改数据
     * @param applySupplyCompany
     * @return
     */
    int update(ApplySupplyCompany applySupplyCompany);

    /**
     * 调用dao层插入数据
     * @param applySupplyCompanyReqDTO
     * @return
     */
    Long insert(ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO);

    /**
     * 分页获取数据
     * @param queryApplySupplyComReqVO
     * @return
     */
    BasePage<ApplySupplyComListRespVO> getApplyList(QueryApplySupplyComReqVO queryApplySupplyComReqVO);

    /**
     * 非前端请求新增方法
     * @param applySupplyComDTO
     * @return
     */
    String insideSaveApply(ApplySupplyComDTO applySupplyComDTO);

    /**
     * 根据id获取申请详情
     * @param applyCode
     * @return
     */
    ApplySupplyComDetailRespVO getApplySupplyComDetail(String applyCode);

    /**
     * 撤销申请表
     * @param cancelApplySupplyComReqVO
     * @return
     */
    int cancelApply(CancelApplySupplyComReqVO cancelApplySupplyComReqVO);

    /**
     * 供货单位申请流程
     * @param applySupplyCompanyReqDTO
     */
    void workFlow(ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO);

    /**
     * 跟着供应商审批流程一起审批操作
     * @param applySupplyCompany
     * @param workFlowCallbackVO
     * @return
     */
    String insideWorkFlowCallback(ApplySupplyCompany applySupplyCompany, WorkFlowCallbackVO workFlowCallbackVO);

    /**
     * 查询列表
     * @param querySupplierReqVO
     * @return
     */
    List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);
    /**
     * 保存导入
     * @author NullPointException
     * @date 2019/7/16
     * @param file
     * @return java.util.List<com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyReqVO>
     */
    SupplierImportResp dealImport(MultipartFile file);
    /**
     * 修改导入
     * @author NullPointException
     * @date 2019/7/16
     * @param file
     * @return java.util.List<com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyReqVO>
     */
    SupplierImportResp dealImport2(MultipartFile file);
}

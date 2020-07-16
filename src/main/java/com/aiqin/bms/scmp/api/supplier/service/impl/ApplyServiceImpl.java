package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.service.AllocationService;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.DetailApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.RequsetParamReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.CancelApplySupplierReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.CancelApplySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.applycontract.ApplyContractViewResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplierDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComAcctInfo2RespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComDetailRespVO;
import com.aiqin.bms.scmp.api.supplier.service.*;
import com.aiqin.bms.scmp.api.util.PageUtil;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className ApplyServiceImpl
 * @date 2019/4/8 09:35

 */
@Service
public class ApplyServiceImpl implements ApplyService {

    @Autowired
    private ApplySupplyComService applySupplyComService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ApplySupplyComAcctService applySupplyComAcctService;
    @Autowired
    private ApplySupplierService applySupplierService;
    @Autowired
    private ApplyContractService applyContractService;
    @Autowired
    private AllocationService allocationService;


    /**
     * 供应商申请列表
     *
     * @param querySupplierReqVO 查询vo
     * @return
     */
    @Override
    public BasePage<ApplyListRespVo> selectApplyList(QueryApplyReqVo querySupplierReqVO) {
        PageHelper.startPage(querySupplierReqVO.getPageNo(), querySupplierReqVO.getPageSize());
        List<ApplyListRespVo> list = Lists.newArrayList();
        switch (querySupplierReqVO.getItemCode()){
//            "功能项 1:供应商 2:供应商集团 3:账户 4:合同"
            case "1":
                list = applySupplyComService.queryApplyList(querySupplierReqVO);
                break;
            case "2":
                list = supplierService.queryApplyList(querySupplierReqVO);
                break;
            case "3":
                list = applySupplyComAcctService.queryApplyList(querySupplierReqVO);
                break;
            case "4":
                list = applyContractService.queryApplyList(querySupplierReqVO);
                break;
            default:
                throw new GroundRuntimeException("请选择功能项");
        }
        return PageUtil.getPageList(querySupplierReqVO.getPageNo(),list);
    }

    @Override
    public HttpResponse detail(DetailApplyReqVo applyReqVo) {
        HttpResponse httpResponse = null;
        switch (applyReqVo.getItemCode()){
//            "功能项 1:供应商 2:供应商集团 3:账户 4:合同"
            case "1":
                ApplySupplyComDetailRespVO applySupplyComDetail = applySupplyComService.getApplySupplyComDetail(applyReqVo.getApplyCode());
                httpResponse = HttpResponse.success(applySupplyComDetail);
                break;
            case "2":
                ApplySupplierDetailRespVO applySupplierDetail = applySupplierService.getApplySupplierDetail(applyReqVo.getApplyCode());
                httpResponse = HttpResponse.success(applySupplierDetail);
                break;
            case "3":
                ApplySupplyComAcctInfo2RespVO applySupplyComAcctInfo2RespVO = applySupplyComAcctService.getApplySupplyCompanyAccountById(applyReqVo.getId());
                httpResponse = HttpResponse.success(applySupplyComAcctInfo2RespVO);
                break;
            case "4":
                ApplyContractViewResVo applyContractDetail = applyContractService.findApplyContractDetail(applyReqVo.getId());
                httpResponse = HttpResponse.success(applyContractDetail);
                break;
            default:
                throw new GroundRuntimeException("请选择功能项");
        }
        return httpResponse;
    }

    @Override
    public void cancel(DetailApplyReqVo applyReqVo) {
        switch (applyReqVo.getItemCode()){
//            "功能项 1:供应商 2:供应商集团 3:账户 4:合同"
            case "1":
                CancelApplySupplyComReqVO cancelApplySupplyComReqVO = new CancelApplySupplyComReqVO();
                cancelApplySupplyComReqVO.setId(applyReqVo.getId());
                applySupplyComService.cancelApply(cancelApplySupplyComReqVO);
                break;
            case "2":
                CancelApplySupplierReqVO reqVO = new CancelApplySupplierReqVO();
                reqVO.setId(applyReqVo.getId());
                applySupplierService.cancelApply(reqVO);
                break;
            case "3":
               applySupplyComAcctService.cancelById(applyReqVo.getId());
                break;
            case "4":
                applyContractService.updateStatusApplyContract(applyReqVo.getId());
                break;
            default:
                throw new GroundRuntimeException("请选择功能项");
        }
    }

    @Override
    public DetailRequestRespVo getRequsetParam(RequsetParamReqVo requsetParamReqVo) {
        DetailRequestRespVo respVo;
        switch (requsetParamReqVo.getItemCode()){
            case "1":
                respVo = applySupplyComService.getInfoByForm(requsetParamReqVo.getFormNo());
                break;
            case "2":
                respVo = applySupplierService.getInfoByForm(requsetParamReqVo.getFormNo());
                break;
            case "3":
                respVo = applySupplyComAcctService.getInfoByForm(requsetParamReqVo.getFormNo());
                break;
            case "4":
                respVo = applyContractService.getInfoByForm(requsetParamReqVo.getFormNo());
                break;
            case "5":
                Long idByFormNo = allocationService.getIdByFormNo(requsetParamReqVo.getFormNo());
                respVo = new DetailRequestRespVo();
                respVo.setId(idByFormNo);
            break;
            default:
                throw new GroundRuntimeException("请选择功能项");
        }
        return respVo;
    }
}

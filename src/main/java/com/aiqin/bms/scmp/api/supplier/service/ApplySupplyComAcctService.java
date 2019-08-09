package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.pojo.SupplyCompanyAccount;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplyComAcctDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplyCompanyAcctReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplyCompanyAccountDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.ApplySupplyCompanyAcctReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QuerySupplierComAcctReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.DetailRequestRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComAcctInfo2RespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.QueryApplySupplierComAcctRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.QuerySupplierComAcctRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.SupplyComAcctMainRespVO;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;

import java.util.List;

/**
 * @功能说明:供货单位账户申请
 * @author: wangxu
 * @date: 2018/12/3 0003 16:44
 */
public interface ApplySupplyComAcctService{
    /**
     * 编码，复制对象,AOP
     * @param applySupplyCompanyAcctReqVO
     * @return
     */
    Long saveApply(ApplySupplyCompanyAcctReqVO applySupplyCompanyAcctReqVO);
    /**
     * 调用DAO层新增数据
     * @param applySupplyCompanyAcctReqDTO
     * @return
     */
    Long insert(ApplySupplyCompanyAcctReqDTO applySupplyCompanyAcctReqDTO);
    /**
     * 其他service调用新增
     * @param applySupplyComAcctDTO
     * @return
     */
    Long insideSaveApply(ApplySupplyComAcctDTO applySupplyComAcctDTO);
    /**
     * 供货的单位账户审批流
     * @author zth
     * @date 2019/1/16
     * @param applySupplyCompanyAcctReqDTO
     * @return void
     */
    void workFlow(ApplySupplyCompanyAcctReqDTO applySupplyCompanyAcctReqDTO);

    /**
     * 修改供货单位账户
     * @author zth
     * @date 2018/12/5
     * @param applySupplyCompanyAcctReq
     * @return int
     */
    int update(ApplySupplyCompanyAcctReqVO applySupplyCompanyAcctReq);
    /**
     * 修改申请表数据
     * @author zth
     * @date 2018/12/6
     * @param s
     * @return int
     */
    int updateApplyData(ApplySupplyCompanyAcctReqDTO s);
    /**
     * 更新正式表的数据
     * @author zth
     * @date 2018/12/7
     * @param s
     * @return int
     */
    int updateSupplyData(SupplyCompanyAccountDTO s);
    /**
     * 更新正式表的数据状态
     * @author zth
     * @date 2018/12/11
     * @param s
     * @return int
     */
    int updateSupplyStatus(SupplyCompanyAccountDTO s);
    /**
     * 逻辑删除正式表数据
     * @author zth
     * @date 2018/12/7
     * @param s
     * @return int
     */
    int deleteSupplyData(ApplySupplyCompanyAcctReqDTO s);
    /**
     * 条件查询申请列表数据
     * @author zth
     * @date 2018/12/7
     * @param vo
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    BasePage<QueryApplySupplierComAcctRespVo> selectApplyListByQueryVO(QueryApplySupplierComAcctReqVo vo);
    /**
     * 条件查询正式列表数据
     * @author zth
     * @date 2018/12/7
     * @param vo
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    BasePage<QuerySupplierComAcctRespVo> selectSupplyListByQueryVO(QuerySupplierComAcctReqVo vo);

    /**
     * 根据供应商名称查询账户信息
     * @param supplierCode
     * @return
     */
    List<QuerySupplierComAcctRespVo> selectSupplierComAcctBySupplierCode(String supplierCode);
    /**
     * 通过id查找申请供货单位账户详情
     * @author zth
     * @date 2018/12/10
     * @param id
     * @return com.aiqin.mgs.scmp.api.domain.response.scmp.ApplySupplyComAcctInfo2RespVO
     */
    ApplySupplyComAcctInfo2RespVO getApplySupplyCompanyAccountById(Long id);
    /**
     * 通过id查找供货单位账户详情
     * @author zth
     * @date 2018/12/10
     * @param id
     * @return com.aiqin.mgs.scmp.api.domain.response.scmp.ApplySupplyComAcctInfo2RespVO
     */
    SupplyComAcctMainRespVO getSupplyCompanyAccountById(Long id);
    /**
     * 通过id取消申请供货单位账户
     * @author zth
     * @date 2018/12/10
     * @param id
     * @return int
     */
    int cancelById(Long id);
    /**
     * 撤销申请表的数据
     * @author zth
     * @date 2018/12/11
     * @param s
     * @return int
     */
    int cancelApplyData(ApplySupplyCompanyAcctReqDTO s);
    /**
     * 撤销正式表的数据修改
     * @author zth
     * @date 2018/12/11
     * @param s1
     * @return int
     */
    int cancelSupplyData(SupplyCompanyAccountDTO s1);

    /**
     * 通过供货单位编码集合查询供货单位账户信息
     * @author zth
     * @date 2019/1/14
     * @param codes 编码集合
     * @return com.aiqin.mgs.scmp.api.domain.response.scmp.SupplyComAcctMainRespVO
     */
    List<SupplyCompanyAccount> getSupplyCompanyAccountByCompanyCode(List<String> codes);

    /**
     * 供货单位账号审核流程
     * @param applySupplyCompanyAccount
     * @param workFlowCallbackVO
     * @return
     */
    String insideWorkFlowCallback(ApplySupplyCompanyAccount applySupplyCompanyAccount, WorkFlowCallbackVO workFlowCallbackVO);

    /**
     * 查询供货单位账户列表
     * @param querySupplierReqVO
     * @return
     */
    List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);


    /**
     *
     * 功能描述: 返回详情接口请求参数
     *
     * @param formNo
     * @return
     * @auther knight.xie
     * @date 2019/8/9 14:41
     */
    DetailRequestRespVo getInfoByForm(String formNo);
}

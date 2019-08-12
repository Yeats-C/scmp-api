package com.aiqin.bms.scmp.api.supplier.dao.supplier;

import com.aiqin.bms.scmp.api.supplier.domain.pojo.ApplySupplyCompany;
import com.aiqin.bms.scmp.api.supplier.domain.request.QueryApplySupplyListComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.request.apply.QueryApplyReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplyComDetailDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.ApplySupplyCompanyReqDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.dto.SupplyThreeIdDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.supplier.vo.QueryApplySupplyComReqVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.ApplySupplyComApplyListRespVO;
import com.aiqin.bms.scmp.api.supplier.domain.response.apply.ApplyListRespVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.supplier.ApplySupplyComListRespVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2018/11/30 0030 15:41
 */
public interface ApplySupplyCompanyDao {
    /**
     * 新增供货单位申请
     * @param applySupplyCompanyReqDTO
     * @return
     */
    Long insertApply(ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO);

    /**
     * 修改供货单位申请
     * @param applySupplyCompanyReqDTO
     * @return
     */
    int updateApply(ApplySupplyCompanyReqDTO applySupplyCompanyReqDTO);

    /**
     * 获取供货单位申请列表
     * @param queryApplySupplyComReqVO
     * @return
     */
    List<ApplySupplyComListRespVO> getApplyList(QueryApplySupplyComReqVO queryApplySupplyComReqVO);

    /**
     * 获取供货单位申请详情
     * @param applyCode
     * @return
     */
    ApplySupplyComDetailDTO getApplySupplyComDetail(String applyCode);


    /**
     * 根据供货单位编码获取对应三张申请表id
     * @param supplyComCode
     * @return
     */
    SupplyThreeIdDTO getThreeApplyIdBySupplyCode(String supplyComCode);

    /**
     * 根据供货单位申请id获取结算和发货申请id
     * @param applyId
     * @return
     */
    SupplyThreeIdDTO getApplyIdsById(Long applyId);

    int updateAndCode(@Param("name") String name, @Param("code") String code);

    /**
     * 根据名称编码校验名称是否重复
     * @param map
     * @return
     */
    int checkName(Map<String, Object> map);

    /**
     * 根据申请供应商编码获取申请供货单位信息
     * @param applySupplierCode
     * @return
     */
    ApplySupplyCompany getApplySupplyCompany(String applySupplierCode);

    /**
     *  根据流程表单编码获取申请数据
     * @param formNo
     * @return
     */
    ApplySupplyCompany getApplySupplyComByFormNo(String formNo);

    /**
     * 查询集合
     * @param querySupplierReqVO
     * @return
     */
    List<ApplyListRespVo> queryApplyList(QueryApplyReqVo querySupplierReqVO);

    /**
     * 查询集合
     * @param queryApplySupplyComReqVO
     * @return
     */
    List<ApplySupplyComApplyListRespVO> applyList(QueryApplySupplyListComReqVO queryApplySupplyComReqVO);
}

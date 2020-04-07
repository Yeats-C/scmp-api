package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.service.BaseService;
import com.aiqin.bms.scmp.api.product.domain.EnumReqVo;
import com.aiqin.bms.scmp.api.product.domain.pojo.Allocation;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProduct;
import com.aiqin.bms.scmp.api.product.domain.pojo.AllocationProductBatch;
import com.aiqin.bms.scmp.api.product.domain.pojo.StockBatch;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationImportSkuReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.AllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.allocation.QueryAllocationReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.AllocationResVo;
import com.aiqin.bms.scmp.api.product.domain.response.allocation.QueryAllocationResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.allocation.AllocationItemRespVo;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;

import java.util.List;


/**
 * 描述:
 *
 * @Author: Kt.w
 * @Date: 2019/1/9
 * @Version 1.0
 * @since 1.0
 */
public interface AllocationService extends BaseService {


    /**
     * 列表展示以及搜索
     * @param vo
     * @return
     */
    BasePage<QueryAllocationResVo> getList(QueryAllocationReqVo vo);


    /**
     * 转化保存实体
     * @param vo
     * @return
     */
    Long save(AllocationReqVo vo);

    /**
     * 撤销调拨单转化实体
     * @param id
     * @return
     */
    int  revocation(Long id);

    /**
     * 查看调拨单详情
     * @param id
     * @return
     */
    AllocationResVo view(Long id);


    /**
     * 保存调拨单主体
     * @param record
     * @return
     */
    Long insertSelective(Allocation record);

    /**
     * 批量插入调拨单sku
     * @param record
     * @return
     */
    int saveList(List<AllocationProduct> record);

    /**
     * 批量插入调拨单sku批次
     * @param record
     * @return
     */
    int saveListBatch(List<AllocationProductBatch> record);
    /**
     * 有选择的更新调拨单实体
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Allocation record);


    /**
     * 查询调拨状态
     *
     * @return
     */
    List<EnumReqVo> getAllocationStatus();

    /**
     * 审批状态更新
     * @param status
     * @param formNo
     * @return
     */
    int updateSubmit(Byte status, String formNo);


    /**
     * 审核回调接口
     *
     * @param vo1
     * @return
     * @author zth
     * @date 2019/1/15
     */
    String nativeWorkFlowCallback(WorkFlowCallbackVO vo1);

    /**
     * 添加是导入sku
     * @param reqVo
     * @return
     */
    List<AllocationItemRespVo> importAllocationSku(AllocationImportSkuReqVo reqVo);

    /**
     *
     * 功能描述: 根据formNo获取主键ID
     *
     * @param formNo
     * @return
     * @auther knight.xie
     * @date 2019/8/8 19:38
     */
    Long getIdByFormNo(String formNo);

    /**
     *  根据批次号和sku编码查询对应库存数量
     * @param skuCode
     * @param batchCode
     * @return
     */
    StockBatch getNumberByBatchAndSkuCode(String skuCode, String batchCode);
}

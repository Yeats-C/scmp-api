package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSku;
import com.aiqin.bms.scmp.api.product.domain.pojo.ApplyProductSkuInspReport;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReport;
import com.aiqin.bms.scmp.api.product.domain.pojo.ProductSkuInspReportDraft;
import com.aiqin.bms.scmp.api.product.domain.request.sku.QueryProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.sku.SaveProductSkuInspReportReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.sku.ProductSkuInspReportRespVo;

import java.util.List;

/**
 * @功能说明:
 * @author: wangxu
 * @date: 2019/3/13 0013 16:30
 */
public interface ProductSkuInspReportService {
    int saveApplyList(List<ApplyProductSku> applyProductSkus);

    int insertApplyList(List<ApplyProductSkuInspReport> applyProductSkuInspReports);

    int saveList(String skuCode, String applyCode);

    int insertList(List<ProductSkuInspReport> productSkuInspReports);

    int insertDraftList(List<ProductSkuInspReportDraft> productSkuInspReportDrafts);

    /**
     * 获取临时数据
     * @param skuCode
     * @return
     */
    List<ProductSkuInspReportRespVo> getDraftList(String skuCode);

    /**
     * 删除临时表数据
     * @param skuCodes
     * @return
     */
    Integer deleteDrafts(List<String> skuCodes);

    /**
     *
     * 功能描述: 获取正式表数据
     *
     * @param reportReqVo
     * @return
     * @auther knight.xie
     * @date 2019/7/2 17:49
     */
    List<ProductSkuInspReportRespVo> getList(QueryProductSkuInspReportReqVo reportReqVo);


    /**
     *
     * 功能描述: 批量保存 质检报告保存接口 (只增加不减少)
     *
     * @param reportReqVo
     * @return
     * @auther knight.xie
     * @date 2019/7/3 17:43
     */
    int saveProductSkuInspReports(SaveProductSkuInspReportReqVo reportReqVo);

    /**
     *
     * 功能描述: 根据Id删除正式表数据
     *
     * @param id
     * @return
     * @auther knight.xie
     * @date 2019/7/4 10:25
     */
    int deleteById(Long id);

    /**
     *
     * 功能描述: 根据SkuCode删除正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/4 10:28
     */
    int deleteBySkuCode(String skuCode);


    /**
     *
     * 功能描述: 单个保存质检报告
     *
     * @param reportReqVo
     * @return
     * @auther knight.xie
     * @date 2019/7/4 10:29
     */
    int saveProductSkuInspReport(ProductSkuInspReport reportReqVo);

    /**
     *
     * 功能描述: 获取申请数据
     *
     * @param skuCode
     * @param applyCode
     * @return
     * @auther knight.xie
     * @date 2019/7/6 23:21
     */
    List<ProductSkuInspReportRespVo> getApply(String skuCode, String applyCode);

    /**
     *
     * 功能描述: 获取正式表数据
     *
     * @param skuCode
     * @return
     * @auther knight.xie
     * @date 2019/7/8 17:22
     */
    List<ProductSkuInspReportRespVo> getListBySkuCode(String skuCode);

}

package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.scrap.QueryScrapReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.scrap.ScrapReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.scrap.QueryScrapResVo;
import com.aiqin.bms.scmp.api.product.domain.response.scrap.ScrapResVo;

/**
 * @Classname: ScrapService
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
public interface ScrapService {
    /**
     * 报废列表搜索
     * @param vo 列表搜索实体
     * @return  列表返回实体
     */
    BasePage<QueryScrapResVo> getList(QueryScrapReqVo vo);


    /**
     * 新增报废单转化实体
     * @param vo
     * @return
     */
    Long save(ScrapReqVo vo);

    /**
     * 查询报废单详情
     * @param id
     * @return
     */
    ScrapResVo view(Long id);

    /**
     * 撤销报废单
     * @param id
     * @return
     */
     int  revocation(Long id);
}

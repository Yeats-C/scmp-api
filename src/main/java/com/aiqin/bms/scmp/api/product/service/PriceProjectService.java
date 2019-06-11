package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.PriceProject;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.AddPriceProjectReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.QueryPriceProjectReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.basicprice.UpdatePriceProjectReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.PriceProjectRespVo;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.PriceProjetGroupType;
import com.aiqin.bms.scmp.api.product.domain.response.basicprice.QueryPriceProjectRespVo;

import java.util.List;

/**
 * @author knight.xie
 * @version 1.0
 * @className PriceProjectService
 * @date 2019/4/19 12:30
 * @description TODO
 */
public interface PriceProjectService  {

    /**
     * 查询List分页
     * @param reqVo
     * @return
     */
    BasePage<QueryPriceProjectRespVo> getList(QueryPriceProjectReqVo reqVo);


    /**
     * 查询所有启用
     * @return
     */
    List<QueryPriceProjectRespVo> getAll();


    /**
     * 获取价格项目列表信息,按照价格类型分组
     * @return
     */
    PriceProjetGroupType queryProjectGroupPriceType();


    /**
     * 根据Id查询
     * @param id
     * @return
     */
    PriceProjectRespVo view(Long id);

    /**
     * 新增
     * @param addVo
     * @return
     */
    int add(AddPriceProjectReqVo addVo);

    /**
     * 插入
     * @param priceProject
     * @return
     */
    Integer insertSelective(PriceProject priceProject);

    /**
     * 修改
     * @param addVo
     * @return
     */
    int update(UpdatePriceProjectReqVo addVo);

    /**
     * 修改
     * @param priceProject
     * @return
     */
    Integer updateByPrimaryKeySelective(PriceProject priceProject);
}

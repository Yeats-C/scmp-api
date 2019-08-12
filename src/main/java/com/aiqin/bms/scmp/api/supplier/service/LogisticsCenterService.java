package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.bms.scmp.api.base.AreaBasic;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterAreaDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.dto.LogisticsCenterDTO;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.LogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.QueryLogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.request.logisticscenter.vo.UpdateLogisticsCenterReqVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.LogisticsCenterResVo;
import com.aiqin.bms.scmp.api.supplier.domain.response.logisticscenter.QueryLogisticsCenterResVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 描述:物流中心service层
 *
 * @Author: Kt.w
 * @Date: 2018/12/25
 * @Version 1.0
 * @since 1.0
 */
public interface LogisticsCenterService {

    /**
     *  列表展示以及搜索
     * @param vo
     * @return
     */
    BasePage<QueryLogisticsCenterResVo> getLogisticsCenterList(QueryLogisticsCenterReqVo vo);

    /**
     * 请求保存转化实体
     * @param logisticsCenterReqVo
     * @return
     */
    HttpResponse<Integer> saveLogisticsCenter(LogisticsCenterReqVo logisticsCenterReqVo);

    /**
     * 通过id返回物流中心实体
     * @param id
     * @return
     */
    LogisticsCenterResVo getLogisticsCenter(Long id);


    /**
     * 请求修改转化实体
     * @param updateLogisticsCenterReqVo
     * @return
     */
    HttpResponse<Integer> updateLogisticsCenter(UpdateLogisticsCenterReqVo updateLogisticsCenterReqVo);

    /**
     * 保存物流中心主体
     * @param record
     * @return
     */
    int insertSelective(LogisticsCenterDTO record);



    /**
     * 批量插入物流中心服务范围
     * @param logisticsCenterAreaDTOS
     * @return
     */
    int saveList(List<LogisticsCenterAreaDTO> logisticsCenterAreaDTOS);


    /**
     * 更新物流中心主体
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(LogisticsCenterDTO record);

    /**
     * 批量更新物流中心服务范围
     * @param logisticsCenterAreaDTOS
     * @return
     */
    int updateList(List<LogisticsCenterAreaDTO> logisticsCenterAreaDTOS);


    /**
     * 物流中心API
     * @return
     */
    List<LogisticsCenterResVo>getLogisticsCenterAPI(String companyCode);


    /**
     * 查询所有省份
     * @return
     */
    HttpResponse getProvinceList();

    /**
     * 根据id往下查
     * @param provinceId
     * @return
     */
    HttpResponse getCityList(String provinceId);
    /**
     * 获取省市区
     * @author NullPointException
     * @date 2019/7/9
     * @param
     * @return com.aiqin.ground.util.protocol.http.HttpResponse<java.util.List<com.aiqin.bms.scmp.api.base.AreaBasic>>
     */
    HttpResponse<List<AreaBasic>> getAreaInfo();
    /**
     * 通过名称匹配仓
     * @author NullPointException
     * @date 2019/7/18
     * @param warehouseList
     * @return java.util.List<com.aiqin.bms.scmp.api.supplier.domain.pojo.LogisticsCenter>
     */
    Map<String,LogisticsCenterDTO> selectByNameList(Set<String> warehouseList, String companyCode);
}

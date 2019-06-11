package com.aiqin.bms.scmp.api.supplier.service;

import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * @功能说明:省市县service
 * @author: wangxu
 * @date: 2018/12/24 0024 14:32
 */
public interface AreaBasicInfoService {
    /**
     * 查询所有省份
     * @return
     */
    HttpResponse getProvinceList();

    /**
     * 根据省id查询对应的市和区
     * @param provinceId
     * @return
     */
    HttpResponse getCityList(String provinceId);

    /**
     * 根据区域等级获取区域列表
     * @param typeId
     * @return
     */
    HttpResponse getAreaListByType(Integer typeId);

    HttpResponse getTreeList();
}

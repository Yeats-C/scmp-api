package com.aiqin.bms.scmp.api.supplier.service.impl;

import com.aiqin.bms.scmp.api.base.AreaBasic;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.supplier.service.AreaBasicInfoService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.mgs.control.component.service.AreaBasicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @功能说明:省市县service实现
 * @author: wangxu
 * @date: 2018/12/24 0024 14:33
 */
@Service
public class AreaBasicInfoServiceImpl implements AreaBasicInfoService {
    @Resource
    private AreaBasicService areaBasicService;

    @Override
    public HttpResponse getProvinceList() {
        try {
            //查询所有省份，固定值传3
            HttpResponse result = areaBasicService.areaTypeInfo(3);
            if (result != null && result.getData()!=null){
                List<AreaBasic> areaBasicList = (List<AreaBasic>)result.getData();
                return HttpResponse.success(areaBasicList);
            }
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        } catch (GroundRuntimeException e){
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @Override
    public HttpResponse getCityList(String provinceId) {
        try {
            HttpResponse result = areaBasicService.selectAreaList(provinceId);
            if (result != null && result.getData()!=null){
                List<AreaBasic> areaBasicList = (List<AreaBasic>)result.getData();
                return HttpResponse.success(areaBasicList);
            }
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        } catch (GroundRuntimeException e) {
            return HttpResponse.failure(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @Override
    public HttpResponse getAreaListByType(Integer typeId) {
        try {
            HttpResponse result = areaBasicService.areaTypeInfo(typeId);
            if (result != null && result.getData()!=null){
                List<AreaBasic> areaBasicList = (List<AreaBasic>)result.getData();
                return HttpResponse.success(areaBasicList);
            }
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        } catch (GroundRuntimeException e) {
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }

    @Override
    public HttpResponse<List<AreaBasic>> getTreeList() {
        try {
            HttpResponse result = areaBasicService.selectAreaOneTree();
            if (result != null && result.getData()!=null){
                List<AreaBasic> areaBasicList = (List<AreaBasic>)result.getData();
                return HttpResponse.success(areaBasicList);
            }
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        } catch (Exception e) {
            throw new BizException(ResultCode.NO_HAVE_INFO_ERROR);
        }
    }
}

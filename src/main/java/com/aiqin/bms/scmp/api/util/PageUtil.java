package com.aiqin.bms.scmp.api.util;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.PageReq;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtil {
    /**
     * 分页
     * @param page  第几页 从 1开始
     * @param list
     * @return
     */
    public static Map<String, Object> startPage(int page, List list) {
        Map<String, Object> resultMap = new HashMap<>();
        PageInfo<Object> pageInfo = new PageInfo<>(list);
        resultMap.put("rows", pageInfo.getList());
        resultMap.put("records", pageInfo.getTotal());
        resultMap.put("total", pageInfo.getPages());
        resultMap.put("page", page);
        return resultMap;

    }

    public static BasePage getPageList(int page, List list){
        BasePage basePage = new BasePage();
        PageInfo<Object> pageInfo = new PageInfo<>(list);
        basePage.setTotalCount(pageInfo.getTotal());
        basePage.setDataList(pageInfo.getList());
        basePage.setPageNo(page);
        basePage.setPageSize(pageInfo.getPageSize());
        basePage.setPages(pageInfo.getPages());
        return basePage;
    }
    public static BasePage getPageList(int page,int pageSize,int total,List list){
        BasePage basePage = new BasePage();
        basePage.setTotalCount((long)total);
        basePage.setDataList(list);
        basePage.setPageNo(page);
        basePage.setPageSize(pageSize);
        return basePage;
    }
    public static BasePage myPage(List<Long> longs, PageReq reqVO){
        if(CollectionUtils.isEmpty(longs)){
            return PageUtil.getPageList(reqVO.getPageNo(), Lists.newArrayList());
        }
        int total = longs.size();
        if (total > reqVO.getPageSize()*reqVO.getPageNo()) {
            longs = longs.subList((reqVO.getPageNo() - 1) * reqVO.getPageSize(), reqVO.getPageNo() * reqVO.getPageSize());
        }else {
            longs = longs.subList((reqVO.getPageNo() - 1) * reqVO.getPageSize(), total);
        }
        return null;
    }
}

package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementWmsOutReq;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementWmsReq;
import com.aiqin.bms.scmp.api.product.domain.request.movement.QueryMovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.QueryMovementResVo;
import com.aiqin.ground.util.protocol.http.HttpResponse;

/**
 * @Classname: MovementService
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
public interface MovementService {
    /**
     * 移库列表搜索
     * @param vo 列表搜索实体
     * @return  列表返回实体
     */
    BasePage<QueryMovementResVo> getList(QueryMovementReqVo vo);


    /**
     * 新增移库单转化实体
     * @param vo
     * @return
     */
    Long save(MovementReqVo vo);

    /**
     * 查询移库单详情
     * @param id
     * @return
     */
    MovementResVo view(Long id);

    /**
     * 撤销移库单
     * @param id
     * @return
     */
     int  revocation(Long id);

    HttpResponse movementWmsEcho(MovementWmsReq request);

    HttpResponse movementWmsOutEcho(MovementWmsOutReq request);
}

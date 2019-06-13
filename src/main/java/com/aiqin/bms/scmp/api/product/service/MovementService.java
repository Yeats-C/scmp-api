package com.aiqin.bms.scmp.api.product.service;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.product.domain.pojo.Movement;
import com.aiqin.bms.scmp.api.product.domain.pojo.MovementProduct;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.movement.QueryMovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.QueryMovementResVo;
import com.aiqin.bms.scmp.api.workflow.vo.request.WorkFlowCallbackVO;

import java.util.List;

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
    int save(MovementReqVo vo);

    /**
     * 有选择的添加移库单主体
     * @param record
     * @return
     */
    int insertSelective(Movement record);

    /**
     * 批量新增移库sku
     * @param list
     * @return
     */
    int saveList(List<MovementProduct> list);

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

    /**
     * 提交审批流
     * @param movementCode
     */
     void workFlow(String movementCode);


    /**
     * 审批回调接口
     * @param vo
     * @return
     */
    String workFlowCallback(WorkFlowCallbackVO vo);


    /**
     * 移库生成出库单
     * @param resVo
     * @return
     */
    String create(MovementResVo resVo);
}

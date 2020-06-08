package com.aiqin.bms.scmp.api.product.web.movement;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.constant.Global;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementWmsReq;
import com.aiqin.bms.scmp.api.product.domain.request.movement.QueryMovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.QueryMovementResVo;
import com.aiqin.bms.scmp.api.product.service.MovementService;
import com.aiqin.bms.scmp.api.purchase.domain.request.callback.TransfersRequest;
import com.aiqin.bms.scmp.api.purchase.web.GoodsRejectController;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname: MovementController
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@RestController
@Api(description = "移库管理")
@RequestMapping("/product/movement")
@Slf4j
public class MovementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRejectController.class);

    @Autowired
    private MovementService movementService;
    /**
     * 移库列表详情
     * @return
     */
    @ApiOperation("移库列表详情")
    @PostMapping("/list")
    public HttpResponse<BasePage<QueryMovementResVo>> getList(@RequestBody QueryMovementReqVo vo) {
        try {
            return HttpResponse.success(movementService.getList(vo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 移库单的新增
     * @return
     */
    @ApiOperation("移库添加")
    @PostMapping("/save")
    public HttpResponse<Long> save(@RequestBody @Validated MovementReqVo vo) {
        try {
            return HttpResponse.success(movementService.save(vo));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 查看移库单详情
     * @return
     */
    @ApiOperation("移库列表详情")
    @GetMapping("/view")
    public HttpResponse<MovementResVo> view(Long id) {
        try {
            return HttpResponse.success(movementService.view(id));
        } catch (Exception e) {
            log.error(Global.ERROR, e);
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    /**
     * 撤销移库单
     * @param id
     * @return
     */
    @ApiOperation("撤销移库单" )
    @GetMapping("/revocation")
    public HttpResponse<Integer> revocation(Long id){
        try {
            return  HttpResponse.success(movementService.revocation(id));
        } catch (GroundRuntimeException e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API,52,e.getMessage()));
        } catch ( Exception e){
            return HttpResponse.failure(ResultCode.MOVEMENT_RECOVER);
        }
    }

    /**
     *
     * @param request
     * @return
     */
    @PostMapping("/wms/transfers")
    @ApiOperation(value = "移库wms推送回调")
    public HttpResponse movementWmsEcho(@RequestBody MovementWmsReq request) {
        LOGGER.info("移库wms回调,request:{}", request.toString());
        return movementService.movementWmsEcho(request);
    }
}

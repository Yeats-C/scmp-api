package com.aiqin.bms.scmp.api.product.web.movement;

import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.product.domain.request.movement.MovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.movement.QueryMovementReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.MovementResVo;
import com.aiqin.bms.scmp.api.product.domain.response.movement.QueryMovementResVo;
import com.aiqin.bms.scmp.api.product.service.MovementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MovementController {

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
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 移库单的新增
     * @return
     */
    @ApiOperation("移库添加")
    @PostMapping("/save")
    public HttpResponse<Long> save(@RequestBody MovementReqVo vo) {
        try {
            return HttpResponse.success(movementService.save(vo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
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
        }catch ( Exception e){
            return HttpResponse.failure(ResultCode.MOVEMENT_RECOVER);}
    }
}

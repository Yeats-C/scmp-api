package com.aiqin.bms.scmp.api.product.web.scrap;

import com.aiqin.bms.scmp.api.base.BasePage;
import com.aiqin.bms.scmp.api.base.ResultCode;
import com.aiqin.bms.scmp.api.common.BizException;
import com.aiqin.bms.scmp.api.product.domain.request.scrap.QueryScrapReqVo;
import com.aiqin.bms.scmp.api.product.domain.request.scrap.ScrapReqVo;
import com.aiqin.bms.scmp.api.product.domain.response.scrap.QueryScrapResVo;
import com.aiqin.bms.scmp.api.product.domain.response.scrap.ScrapResVo;
import com.aiqin.bms.scmp.api.product.service.ScrapService;
import com.aiqin.ground.util.exception.GroundRuntimeException;
import com.aiqin.ground.util.protocol.MessageId;
import com.aiqin.ground.util.protocol.Project;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Classname: ScrapController
 * 描述:
 * @Author: Kt.w
 * @Date: 2019/4/1
 * @Version 1.0
 * @Since 1.0
 */
@RestController
@Api(description = "报废管理")
@RequestMapping("/product/scrap")
public class ScrapController {

    @Autowired
    private ScrapService ScrapService;
    /**
     * 报废列表详情
     * @return
     */
    @ApiOperation("列表详情")
    @PostMapping("/list")
    public HttpResponse<BasePage<QueryScrapResVo>> getList(@RequestBody QueryScrapReqVo vo) {
        try {
            return HttpResponse.success(ScrapService.getList(vo));
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 报废单的新增
     * @return
     */
    @ApiOperation("报废添加")
    @PostMapping("/save")
    public HttpResponse<Long> save(@RequestBody @Validated ScrapReqVo vo) {
        try {
            return HttpResponse.success(ScrapService.save(vo));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 查看报废单详情
     * @return
     */
    @ApiOperation("报废列表详情")
    @GetMapping("/view")
    public HttpResponse<ScrapResVo> view(Long id) {
        try {
            return HttpResponse.success(ScrapService.view(id));
        } catch (BizException e) {
            return HttpResponse.failure(e.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
        }
    }


    /**
     * 撤销报废单
     * @param id
     * @return
     */
    @ApiOperation("撤销报废单" )
    @GetMapping("/revocation")
    public HttpResponse<Integer> revocation(Long id) {
        try {
            return  HttpResponse.success(ScrapService.revocation(id));
        } catch (GroundRuntimeException e) {
            return HttpResponse.failure(MessageId.create(Project.PRODUCT_API,52,e.getMessage()));
        } catch ( Exception e){
            return HttpResponse.failure(ResultCode.MOVEMENT_RECOVER);
        }
    }
}

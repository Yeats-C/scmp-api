package com.aiqin.bms.scmp.api.product.web;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author knight.xie
 * @version 1.0
 * @className SupervisoryWarehouseOrderController
 * @date 2019/6/29 15:26
 * @description TODO
 */

@RestController
@Api(tags = "监管仓开单接口")
@RequestMapping("/supervisory/warehouse")
@Slf4j
public class SupervisoryWarehouseOrderController {
//    @Autowired
//    private BaseService baseService;
//
//    @GetMapping("/getCurrentUserName")
//    @ApiOperation(value = "获取当前用户")
//    public HttpResponse<String> getCurrentUserName(){
//        try {
//            return HttpResponse.success(baseService.getUser().getPersonName());
//        } catch (BizException e) {
//            return HttpResponse.failure(e.getMessageId());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return HttpResponse.failure(ResultCode.SYSTEM_ERROR);
//        }
//    }
}

package com.aiqin.bms.scmp.api.form.web;

import com.aiqin.ground.util.protocol.http.HttpResponse;
import com.aiqin.platform.flows.client.service.FormUserPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/form/user/position")
@Api(tags = "审批流-人员组织")
public class FormUserPositionController {

    @Resource
    private FormUserPositionService formUserPositionService;

    @GetMapping("/")
    @ApiOperation("获取人员组织列表")
    HttpResponse selectByCompanyCode(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "user_name", required = false) String userName,
                                     @RequestParam(value = "position_code", required = false) String positionCode,
                                     @RequestParam(value = "position_name", required = false) String positionName,
                                     @RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "rows", defaultValue = "50") Integer rows) {
        return formUserPositionService.getUserPositions(name, userName, positionCode, positionName, page, rows);
    }

}

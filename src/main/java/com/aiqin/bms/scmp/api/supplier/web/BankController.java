package com.aiqin.bms.scmp.api.supplier.web;

import com.aiqin.bms.scmp.api.supplier.service.BankService;
import com.aiqin.ground.util.protocol.http.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述: 银行接口
 *
 * @Author: Kt.w
 * @Date: 2018/12/29
 * @Version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/bank")
@Api(description = "银行")
public class BankController {

    @Autowired
    private BankService bankService;
    @GetMapping("/getBank")
    @ApiOperation("获取银行信息")
    public HttpResponse getBankList(@RequestParam(required = false)String bank_name){

             return bankService.getBankList(bank_name);
    }
}

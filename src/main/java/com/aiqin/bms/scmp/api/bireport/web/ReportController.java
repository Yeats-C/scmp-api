package com.aiqin.bms.scmp.api.bireport.web;

import com.aiqin.bms.scmp.api.bireport.service.ReportService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ch
 * @description 报表
 */
@RestController
@Api(tags = "报表接口")
@RequestMapping("/replenishment")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

}

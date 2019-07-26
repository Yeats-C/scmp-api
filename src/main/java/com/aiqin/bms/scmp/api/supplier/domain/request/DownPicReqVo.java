package com.aiqin.bms.scmp.api.supplier.domain.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author knight.xie
 * @version 1.0
 * @className DownPicReqVo
 * @date 2019/7/26 13:57
 */
@ApiModel
@Data
public class DownPicReqVo {
    private String filePath;
}

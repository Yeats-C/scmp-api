package com.aiqin.bms.scmp.api.supplier.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 描述: 枚举类型返回实体
 *
 * @Author: Kt.w
 * @Date: 2019/1/10
 * @Version 1.0
 * @since 1.0
 */
@Data
@ApiModel("枚举类型返回实体")
public class EnumReqVo {
    /**
     * 类型编码
     */
    private byte code;

    /**
     * 类型名称
     */
    private String name;

}

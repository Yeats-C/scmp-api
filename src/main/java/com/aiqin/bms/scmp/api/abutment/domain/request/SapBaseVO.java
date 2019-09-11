package com.aiqin.bms.scmp.api.abutment.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author sunx
 * @description
 * @date 2019-08-01
 */
@Data
public class SapBaseVO {
    /**
     * 消息类型 S  成功  E 失败
     */
    @JsonProperty("MSGTY")
    private String code;

    /**
     * 消息文本
     */
    @JsonProperty("MSAGE")
    private String msg;
}

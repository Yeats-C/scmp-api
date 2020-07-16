package com.aiqin.bms.scmp.api.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * @author: niuhuan
 * @createDate: 2020/6/20
 * @since: JDK 1.8
 * @Description:
 */
public class BaseController {
    /**
     * 默认参数校验处理方法
     *
     * @param bindingResult
     */
    public void checkParameters(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    sb.append("[ ").append(fieldError.getField()).append(" : ").append(fieldError.getDefaultMessage()).append(" ]; ");
                }
            }
            throw new ParameterCheckException(sb.toString());



        }

    }

}

package com.aiqin.bms.scmp.api.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/8/28.
 */
//@Component
@Configuration
/*@EnableConfigurationProperties({JcoProperties.class})*/
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
    // @Resource
    // UrlInterceptor urlInterceptor;
    @Resource
    AuthenticationInterceptor authenticationInterceptor;

    /*@Resource
    JcoProperties jcoProperties;*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/returnGoods/record/return");
    }

    /*@Bean
    public JcoClient getJcoClient() {
        return new JcoClient(jcoProperties);
    }*/
}
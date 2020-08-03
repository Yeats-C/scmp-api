package com.aiqin.bms.scmp.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(
                        "/chart/**",
                        "/favicon.ico",
                        "/static/**",
                        "/index.html",
                        "/order/callback/**",
                        "/product/inbound/workFlowCallBack",
                        "/product/outbound/workFlowCallBack",
                        "/product/allocation/workFlowCallBack",
                        "/asset/info/detail/no_controller/*",
                        "/ding/**",
                        "/synchronization/stock",
                        "/probe.status.go",
                        "/product/salearea/area/sale",
                        "/order/aiqin/**",
                        "/returnGoods/record/return",
                        "/returnGoods/recordDL/return",
                        "/returnGoods/receipt",
                        "/excel/import",
                        "/stock/synchro/batch",
                        "/LogisticsCenter/getLogisticsCenterList",
                        "/sku/info/import/wms",
                        "/supplier/company/import/wms",
                        "/order/wms/sale",
                        "/order/outbound/code/sale",
                        "/dl/**")
                .excludePathPatterns("/workFlow/workFlowCallBack/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/swagger-ui.htm/**");
        super.addInterceptors(registry);
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper()));
        super.configureMessageConverters(converters);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ResponseBodyMapper builder = new ResponseBodyMapper();
//        ObjectMapper build = builder.build();
//        build.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return builder.build();
    }


}

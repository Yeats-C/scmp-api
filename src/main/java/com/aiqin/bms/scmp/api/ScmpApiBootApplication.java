package com.aiqin.bms.scmp.api;

import com.aiqin.ground.spring.boot.core.annotations.GroundBoot;
import com.aiqin.ground.spring.boot.core.annotations.GroundDataSource;
import com.aiqin.ground.spring.boot.core.annotations.RedisCache;
import com.aiqin.mgs.control.client.annotations.ControlClient;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 * <p>
 * Date: 23/08/2018
 * Time: 10:16
 *
 * @author heroin.nee@gmail.com
 */
@Configuration
@ComponentScan(basePackages = {"com.aiqin.bms","com.aiqin.mgs","com.aiqin.platform"})
@GroundBoot
@GroundDataSource
@EnableTransactionManagement
@ControlClient
@MapperScan(basePackages = {"com.aiqin.bms.scmp.api.*.dao",
        "com.aiqin.bms.scmp.api.*.mapper"})
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@EnableAsync(proxyTargetClass = true)
@EnableSwagger2
@EnableScheduling
@RedisCache
public class ScmpApiBootApplication extends SpringBootServletInitializer {

    private static Logger LOGGER = LoggerFactory.getLogger(ScmpApiBootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ScmpApiBootApplication.class, args);
        LOGGER.info("============= SpringBoot Start Success =============");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ScmpApiBootApplication.class);
    }
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}

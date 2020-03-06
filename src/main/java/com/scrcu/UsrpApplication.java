package com.scrcu;

import com.scrcu.config.druid.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import({DynamicDataSourceRegister.class})
@ServletComponentScan(basePackages = "com.scrcu")
@MapperScan(value="com.scrcu.*.mapper")
@EnableTransactionManagement
public class UsrpApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(UsrpApplication.class, args);
    }

    /**
     * 描述：使用外部tomcat服务器部署项目
     * @param builder
     * @return
     */
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(UsrpApplication.class);
    }*/
}

package com.scrcu.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 描述： mybatis-plus配置
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/27 10:20
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.scrcu.**.mapper*")
public class MybatisPlusConfiguration {

    /**
     * 描述： mybatis-plus 性能分析拦截器,性能优化
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/27 10:20
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // SQL执行性能分析,开发环境使用,maxTime指的是sql最大执行时长
        performanceInterceptor.setMaxTime(100000);
        // SQL是否格式化,默认false
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * 描述： mybatis-plus分页插件,实现物理分页
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/27 10:20
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        return page;
    }
}

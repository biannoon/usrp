package com.scrcu.config.druid;

import java.lang.annotation.*;

/**
 * 描述: 动态数据源自定义注解，作用于类或者方法上(default:"")
 * @创建人: jiyuanbo
 * @创建时间: 2019-8-22 下午12:30:33
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceTarget {

	DataSourceType name();
}

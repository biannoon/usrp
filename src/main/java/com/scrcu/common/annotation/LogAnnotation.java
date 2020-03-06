package com.scrcu.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author pengjuntao
 * @Date 2019/10/8 8:41
 * @Version 1.0
 * @function
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    String[] id() default "";//操作主键
    String operType() default "";//操作类型
    String operation() default "";//描述
}

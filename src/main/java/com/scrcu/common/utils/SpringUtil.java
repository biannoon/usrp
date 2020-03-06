package com.scrcu.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * spring容器工具类：用于普通类获取spring容器中的bean
 * @Author pengjuntao
 * @Date 2019/9/26 14:21
 * @Version 1.0
 **/
@Component
@Order(1)
public class SpringUtil implements ApplicationContextAware {

    private static Logger logger = Logger.getLogger(SpringUtil.class.getName());
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null)
            SpringUtil.applicationContext = applicationContext;
        logger.info("-----------ApplicationContext配置成功--------------");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * 通过name获取bean
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过bean类型获取bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return  getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name和bean类型获取bean
     * @param clazz
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz, String name){
        return getApplicationContext().getBean(name,clazz);
    }

}


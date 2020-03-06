package com.scrcu.config.ehcache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 描述： ehcache配置类，该类的目的就是将ehcache的管理器暴露给spring上下文容器
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/28 10:12
 */
@Configuration
// 标注启动了缓存
@EnableCaching
public class CacheConfiguration {

    /**
     * 描述： ehcache管理器
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean(name= "appCacheManager")
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
        return new EhCacheCacheManager(bean.getObject());
    }

    /**
     * 描述： 据shared(共享设置),Spring分别通过CacheManager.create()
     * 或new CacheManager()方式来创建一个ehcache基地.
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }
}

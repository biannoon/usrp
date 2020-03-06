package com.scrcu.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/** 
 * 描述: 初始化数据源、提供执行动态切换数据源的工具类
 * @创建人: jiyuanbo
 * @创建时间: 2019-8-22 下午04:20:45
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//指定默认数据源(springboot2.0默认数据源是hikari如何想使用其他数据源可以自己配置)
	private static final String DATASOURCE_TYPE_DEFAULT = "com.mysql.cj.jdbc.Driver";
	// 默认数据源
	private DataSource defaultDataSource;
	// 从数据源
	private static Map<String,DataSource> userDataSources = new HashMap<>();

	/**
	 * 描述: 加载多数据源配置
	 * @param env
	 * @创建人: jiyuanbo
	 * @创建时间: 2019-8-22 下午04:38:20
	 */
	public void setEnvironment(Environment env) {
		// TODO Auto-generated method stub
		initDefaultDataSource(env);
        initUserDataSources(env);
	}
    /**
     * 描述: 初始化默认数据源（主数据源）的配置信息
     * @param env
     * @创建人: jiyuanbo
     * @创建时间: 2019-8-22 下午04:38:20
     */
	private void initDefaultDataSource(Environment env) {
        logger.info("读取主数据源配置");
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", env.getProperty("spring.datasource.druid.master.driver-class-name"));
        dsMap.put("url", env.getProperty("spring.datasource.druid.master.url"));
        dsMap.put("username", env.getProperty("spring.datasource.druid.master.username"));
        dsMap.put("password", env.getProperty("spring.datasource.druid.master.password"));
        dsMap.put("filters", env.getProperty("spring.datasource.druid.master.filters"));
        dsMap.put("maxActive", env.getProperty("spring.datasource.druid.master.maxActive"));
        dsMap.put("initialSize", env.getProperty("spring.datasource.druid.master.initialSize"));
        dsMap.put("maxWait", env.getProperty("spring.datasource.druid.master.maxWait"));
        dsMap.put("minIdle", env.getProperty("spring.datasource.druid.master.minIdle"));
        dsMap.put("timeBetweenEvictionRunsMillis",
                env.getProperty("spring.datasource.druid.master.timeBetweenEvictionRunsMillis"));
        dsMap.put("minEvictableIdleTimeMillis",
                env.getProperty("spring.datasource.druid.master.minEvictableIdleTimeMillis"));
        dsMap.put("validationQuery", env.getProperty("spring.datasource.druid.master.validationQuery"));
        dsMap.put("testWhileIdle", env.getProperty("spring.datasource.druid.master.testWhileIdle"));
        dsMap.put("testOnBorrow", env.getProperty("spring.datasource.druid.master.testOnBorrow"));
        dsMap.put("testOnReturn", env.getProperty("spring.datasource.druid.master.testOnReturn"));
        dsMap.put("poolPreparedStatements",
                env.getProperty("spring.datasource.druid.master.poolPreparedStatements"));
        dsMap.put("maxOpenPreparedStatements",
                env.getProperty("spring.datasource.druid.master.maxOpenPreparedStatements"));
        defaultDataSource = buildDataSource(dsMap);
    }
    /**
     * 描述: 初始化非主数据源的配置信息
     * @param env
     * @创建人: jiyuanbo
     * @创建时间: 2019-8-22 下午04:38:20
     */
	private void initUserDataSources(Environment env) {
        logger.info("循环读取非主数据源配置");
        // 读取配置文件获取更多数据源
        String dsPrefixs = env.getProperty("spring.datasource.druid.names");
        if (null !=dsPrefixs && !"".equals(dsPrefixs)) {
            for (String dsPrefix : dsPrefixs.split(",")) {
                // 多个数据源
                Map<String, Object> dsMap = new HashMap<>();
                dsMap.put("driver", env.getProperty("spring.datasource.druid." + dsPrefix + ".driver-class-name"));
                dsMap.put("url", env.getProperty("spring.datasource.druid." + dsPrefix + ".url"));
                dsMap.put("username", env.getProperty("spring.datasource.druid." + dsPrefix + ".username"));
                dsMap.put("password", env.getProperty("spring.datasource.druid." + dsPrefix + ".password"));
                dsMap.put("filters", env.getProperty("spring.datasource.druid." + dsPrefix + ".filters"));
                dsMap.put("maxActive", env.getProperty("spring.datasource.druid." + dsPrefix + ".maxActive"));
                dsMap.put("initialSize", env.getProperty("spring.datasource.druid." + dsPrefix + ".initialSize"));
                dsMap.put("maxWait", env.getProperty("spring.datasource.druid." + dsPrefix + ".maxWait"));
                dsMap.put("minIdle", env.getProperty("spring.datasource.druid." + dsPrefix + ".minIdle"));
                dsMap.put("timeBetweenEvictionRunsMillis",
                        env.getProperty("spring.datasource.druid." + dsPrefix + ".timeBetweenEvictionRunsMillis"));
                dsMap.put("minEvictableIdleTimeMillis",
                        env.getProperty("spring.datasource.druid." + dsPrefix + ".minEvictableIdleTimeMillis"));
                dsMap.put("validationQuery", env.getProperty("spring.datasource.druid." + dsPrefix + ".validationQuery"));
                dsMap.put("testWhileIdle", env.getProperty("spring.datasource.druid." + dsPrefix + ".testWhileIdle"));
                dsMap.put("testOnBorrow", env.getProperty("spring.datasource.druid." + dsPrefix + ".testOnBorrow"));
                dsMap.put("testOnReturn", env.getProperty("spring.datasource.druid." + dsPrefix + ".testOnReturn"));
                dsMap.put("poolPreparedStatements",
                        env.getProperty("spring.datasource.druid." + dsPrefix + ".poolPreparedStatements"));
                dsMap.put("maxOpenPreparedStatements",
                        env.getProperty("spring.datasource.druid." + dsPrefix + ".maxOpenPreparedStatements"));
                DataSource ds = buildDataSource(dsMap);
                userDataSources.put(dsPrefix, ds);
            }
        }
    }
	/**
     * 描述: 创建DataSource
     * @param dsMap
     * @创建人: jiyuanbo
     * @创建时间: 2019-8-22 下午05:28:58
     */
    @SuppressWarnings("unchecked")
    public DataSource buildDataSource(Map<String, Object> dsMap) {
        try {
            String type = (String) dsMap.get("type");
            if (type == null) {
                // 默认DataSource
                type = DATASOURCE_TYPE_DEFAULT;
            }
            Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName(type);
            String dataSource = DynamicDataSourceContextHolder.getDataSource();
            System.out.println(dataSource);
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(dsMap.get("driver").toString());
            druidDataSource.setUrl(dsMap.get("url").toString());
            druidDataSource.setUsername(dsMap.get("username").toString());
            druidDataSource.setPassword(dsMap.get("password").toString());
            druidDataSource.setFilters(dsMap.get("filters").toString());
            druidDataSource.setMaxActive(Integer.parseInt(dsMap.get("maxActive").toString()));
            druidDataSource.setInitialSize(Integer.parseInt(dsMap.get("initialSize").toString()));
            druidDataSource.setMaxWait(Integer.parseInt(dsMap.get("maxWait").toString()));
            druidDataSource.setMinIdle(Integer.parseInt(dsMap.get("minIdle").toString()));
            druidDataSource.setTimeBetweenEvictionRunsMillis(
                    Long.parseLong(dsMap.get("timeBetweenEvictionRunsMillis").toString()));
            druidDataSource.setMinEvictableIdleTimeMillis(
                    Long.parseLong(dsMap.get("minEvictableIdleTimeMillis").toString()));
            druidDataSource.setValidationQuery(dsMap.get("validationQuery").toString());
            druidDataSource.setTestWhileIdle(Boolean.parseBoolean(dsMap.get("testWhileIdle").toString()));
            druidDataSource.setTestOnBorrow(Boolean.parseBoolean(dsMap.get("testOnBorrow").toString()));
            druidDataSource.setTestOnReturn(Boolean.parseBoolean(dsMap.get("testOnReturn").toString()));
            druidDataSource.setPoolPreparedStatements(
                    Boolean.parseBoolean(dsMap.get("poolPreparedStatements").toString()));
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(
                    Integer.parseInt(dsMap.get("maxOpenPreparedStatements").toString()));
            druidDataSource.init();
            return druidDataSource;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 描述: 重写registerBeanDefinitions方法
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     * @创建人: jiyuanbo
     * @创建时间: 2019-8-22 下午05:28:58
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, 
    		BeanDefinitionRegistry beanDefinitionRegistry) {
		// TODO Auto-generated method stub
		Map<Object, Object> dataSourceTargets = new HashMap<>();
        //将主数据源添加到更多数据源中
        dataSourceTargets.put("master", this.defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("master");
        //添加其他数据源
        dataSourceTargets.putAll(userDataSources);
        for (String key : userDataSources.keySet()) {
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }
        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", dataSourceTargets);
        //注册 - BeanDefinitionRegistry
        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
        logger.info("Dynamic DataSource Registry");
	}
}

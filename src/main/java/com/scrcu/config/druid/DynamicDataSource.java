package com.scrcu.config.druid;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 描述: 动态切换数据源
 * @创建人: jiyuanbo
 * @创建时间: 2019-8-22 上午10:53:27
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 描述： 动态切换数据源,AbstractRoutingDataSource(每执行一次数据库,动态获取DataSource)
     * @return
     * @创建人: jiyuanbo
     * @创建时间: 2019-8-22 上午10:55:06
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSource();
    }
}

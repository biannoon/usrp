package com.scrcu.config.druid;

/**
 * 描述： TODO
 *
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/17 15:33
 */
public enum DataSourceType {

    Master("master"),
    Slave("slave"),
    Test("test");

    DataSourceType(String dbName) {
        this.dbName = dbName;
    }

    private String dbName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}

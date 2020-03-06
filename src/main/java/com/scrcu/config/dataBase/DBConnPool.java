package com.scrcu.config.dataBase;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.scrcu.common.utils.SpringUtil;
import com.scrcu.sys.entity.SysDatabase;
import com.scrcu.sys.service.SysDatabaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 描述： 数据库连接池管理
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/18 10:35
 */
@Component
public class DBConnPool {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Map<String, DruidDataSource> map = new HashMap<>();
    @Autowired
    private SysDatabaseService sysDatabaseService =
            (SysDatabaseService) SpringUtil.getBean("sysDatabaseServiceImpl");

    private static class SingletonHolder {
        private static final DBConnPool INSTANCE = new DBConnPool();
    }

    public static final DBConnPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private DBConnPool() {
        try {
            List<SysDatabase> sysDatabases = sysDatabaseService.findList();
            if (sysDatabases.size() > 0) {
                this.DBConnPool(sysDatabases);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void DBConnPool(List<SysDatabase> sysDatabases) throws Exception {
        logger.info("初始化数据库连接池被调用了");
        for (SysDatabase sdb : sysDatabases) {
            Properties prop = new Properties();
            if("MYSQL".equalsIgnoreCase(sdb.getDbTyp())){
                prop.setProperty("driver","com.mysql.cj.jdbc.Driver");
                prop.setProperty("url","jdbc:mysql://"+sdb.getDbIp()+":"+sdb.getDbPort()+"/"+sdb.getDbNm());
                prop.setProperty("connectionProperties","characterEncoding=UTF-8;useSSL=true;serverTimezone=GMT+8");
            } else if ("ORACLE".equalsIgnoreCase(sdb.getDbTyp())){
                prop.setProperty("driver","oracle.jdbc.driver.OracleDriver");
                prop.setProperty("url","jdbc:oracle:thin:@"+sdb.getDbIp()+":"+sdb.getDbPort()+":"+sdb.getDbNm());
            } else if ("DB2".equalsIgnoreCase(sdb.getDbTyp())){
                prop.setProperty("driver","com.ibm.db2.jcc.DB2Driver");
                prop.setProperty("url","jdbc:db2://"+sdb.getDbIp()+":"+sdb.getDbPort()+"/"+sdb.getDbNm());
            } else if("HIVE".equalsIgnoreCase(sdb.getDbTyp())){
                prop.setProperty("driver","org.apache.hive.jdbc.HiveDriver");
                prop.setProperty("url","jdbc:hive2://"+sdb.getDbIp()+":"+sdb.getDbPort()+"/"+sdb.getDbNm());
            }  else if("TD".equalsIgnoreCase(sdb.getDbTyp())){
                prop.setProperty("driver","com.teradata.jdbc.TeraDriver");
                String url = "jdbc:teradata://"+sdb.getDbIp();
                if (!StringUtils.isEmpty(sdb.getDbPort())) {
                    url = url + ":" + sdb.getDbPort();
                }
                url = url + "/CLIENT_CHARSET=UTF8,TMODE=TERA,CHARSET=UTF8";
                if (!StringUtils.isEmpty(sdb.getDbNm())) {
                    url = url + ",DATABASE=" + sdb.getDbNm();
                }
                prop.setProperty("url", url);
            } else {
                logger.error("数据库连接池不支持" + sdb.getDbTyp() + "类型的连接！");
                break;
            }
            prop.setProperty("username",sdb.getDbUserNm());
            prop.setProperty("password",sdb.getDbUserPwd());
            // 连接池公共基础属性
            prop.setProperty("filters","stat");
            prop.setProperty("maxActive","20");
            prop.setProperty("initialSize","1");
            prop.setProperty("maxWait","60000");
            prop.setProperty("minIdle","1");
            prop.setProperty("timeBetweenEvictionRunsMillis","60000");
            prop.setProperty("minEvictableIdleTimeMillis","30000");
            prop.setProperty("testWhileIdle","true");
            prop.setProperty("testOnBorrow","false");
            prop.setProperty("testOnReturn","false");
            prop.setProperty("poolPreparedStatements","false");
            prop.setProperty("maxPoolPreparedStatementPerConnectionSize","200");
            prop.setProperty("removeAbandoned","true");
            try {
                DruidDataSource druidDataSource = (DruidDataSource) DruidDataSourceFactory
                        .createDataSource(prop);
                map.put(sdb.getId(), druidDataSource);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("初始化数据库连接池失败");
                throw new Exception(e.getMessage());
            }
        }
    }

    /**
     * 返回druid数据库连接
     * @return
     * @throws SQLException
     */
    public DruidPooledConnection getConnection(String dbId) throws SQLException {
        DruidDataSource druidDataSource = map.get(dbId);
        return druidDataSource.getConnection();
    }

    public void removeDataSource(String dbId) {
        DruidDataSource druidDataSource = map.get(dbId);
        druidDataSource.close();
    }

}

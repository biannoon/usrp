package com.scrcu.config.dataBase;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.scrcu.common.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * 描述： 数据库使用工具类
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/18 16:33
 */
public class DBUtils {

    protected static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

    /**
     * 描述： 数据库连接方法
     * @param dbId
     * @return Connection
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static Connection getConn(String dbId) {
        DBConnPool dbConnPool = DBConnPool.getInstance();
        DruidPooledConnection conn = null;
        try {
            conn = dbConnPool.getConnection(dbId);
        } catch (SQLException e) {
            e.printStackTrace();
            dbConnPool.removeDataSource(dbId);
            logger.error("数据库连接池已关闭！");
        }
        return conn;
    }

    /**
     * 描述： 数据库连接关闭方法
     * @param connection
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static void closeConn(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new BaseException("数据库connection关闭异常！", e);
        }

    }

    /**
     * 描述： 数据库编译关闭方法
     * @param statement
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static void closeStm(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new BaseException("数据库Statement关闭异常！", e);
        }
    }

    /**
     * 描述： 数据库预编译关闭方法
     * @param preparedStatement
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static void closePsts(PreparedStatement preparedStatement) {
        if (preparedStatement == null) {
            return;
        }
        try {
            if (!preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new BaseException("数据库PreparedStatement关闭异常！", e);
        }
    }

    /**
     * 描述： 数据库结果集关闭方法
     * @param connection
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static void closeRes(ResultSet resultSet) {
        if (resultSet == null) {
            return;
        }
        try {
            if (!resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new BaseException("数据库ResultSet关闭异常！", e);
        }
    }

    /**
     * 描述： 数据库连接整体关闭无结果集
     * @param connection
     * @param statement
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static void close(Connection connection, Statement statement) {
        closeStm(statement);
        closeConn(connection);
    }

    /**
     * 描述： 数据库连接整体关闭无结果集
     * @param connection
     * @param preparedStatement
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static void close(Connection connection, PreparedStatement preparedStatement) {
        closeStm(preparedStatement);
        closeConn(connection);
    }

    /**
     * 描述： 数据库连接整体关闭
     * @param connection
     * @param statement
     * @param resultSet
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        closeRes(resultSet);
        closeStm(statement);
        closeConn(connection);
    }

    /**
     * 描述： 数据库连接整体关闭
     * @param connection
     * @param preparedStatement
     * @param resultSet
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 16:33
     */
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        closeRes(resultSet);
        closeStm(preparedStatement);
        closeConn(connection);
    }
}

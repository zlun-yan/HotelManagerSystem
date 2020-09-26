package com.csu.dbUtil;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtils {
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource("c3p0-config.xml");

    /**
     * 获取数据库连接
     * @return 返回数据库连接
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * 关闭打开的资源
     * @param rs 打开的结果集
     * @param stmt  打开的PreparedStatement
     * @param connection  打开的数据库连接
     * @throws SQLException
     */
    public static void close(ResultSet rs, Statement stmt, Connection connection) throws SQLException {
        //关闭rs
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (connection != null) connection.close();
    }

    public static String getDateString() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = ft.format(new Date());

        return dateStr;
    }
}

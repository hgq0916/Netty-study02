package com.mashibing.netty.study.reflection.jdbc000;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hugangquan
 * @date 2021/11/13 17:23
 */
public class JdbcUtil {


    public static Connection getConnection(){

        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_study?tinyInt1isBit=true&useUnicode=true" +
                    "&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true" +
                    "&serverTimezone=Asia/Shanghai", "root", "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;

    }

}

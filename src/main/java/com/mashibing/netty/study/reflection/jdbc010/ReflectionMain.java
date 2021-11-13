package com.mashibing.netty.study.reflection.jdbc010;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hugangquan
 * @date 2021/11/13 17:33
 */
public class ReflectionMain {

    public static void main(String[] args) throws Exception{
        Connection connection = JdbcUtil.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("select * from user limit 200000");

        ResultSet resultSet = preparedStatement.executeQuery();

        List<UserEntity> userEntities = new ArrayList<UserEntity>();

        long t1 = System.currentTimeMillis();

        while (resultSet.next()){
            UserEntity userEntity = UserEntity_Helper.getUserEntity(resultSet);

            if(userEntity != null){
                userEntities.add(userEntity);
            }
        }

        long t2 = System.currentTimeMillis();

        System.out.println("耗时:"+(t2-t1)+"毫秒");


    }

}

package com.mashibing.netty.study.reflection.jdbc020;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ReflectionMain.class);

    public static void main(String[] args) throws Exception{
        Connection connection = JdbcUtil.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("select * from user limit 200000");

        ResultSet resultSet = preparedStatement.executeQuery();

        List<UserEntity> userEntities = new ArrayList<UserEntity>();

        long t1 = System.currentTimeMillis();

        AbstractEntityHelper entityHelper = EntityHelperFactory.getEntityHelper(UserEntity.class);

        if(entityHelper == null){
            logger.error("找不到对应的helper:"+UserEntity.class.getName());
            return;
        }

        while (resultSet.next()){
            UserEntity userEntity = (UserEntity) entityHelper.getEntity(resultSet);
            if(userEntity != null){
                userEntities.add(userEntity);
            }
        }

        long t2 = System.currentTimeMillis();

        System.out.println("耗时:"+(t2-t1)+"毫秒");


    }

}

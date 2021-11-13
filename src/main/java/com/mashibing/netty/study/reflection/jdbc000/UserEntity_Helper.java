package com.mashibing.netty.study.reflection.jdbc000;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author hugangquan
 * @date 2021/11/13 17:32
 */
public class UserEntity_Helper {


    public static UserEntity getUserEntity(ResultSet rs) throws Exception {

        if(rs == null){
            return null;
        }

        UserEntity userEntity = new UserEntity();

        int id = rs.getInt("id");

        String name = rs.getString("user_name");

        int age = rs.getInt("age");

        String phone = rs.getString("phone");

        userEntity.setId(id);
        userEntity.setName(name);
        userEntity.setAge(age);
        userEntity.setPhone(phone);

        return userEntity;
    }

}

package com.mashibing.netty.study.reflection.jdbc020;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

/**
 * 使用反射加注解的方式动态构建entity
 * @author hugangquan
 * @date 2021/11/13 17:32
 */
public class Entity_Helper {

    private static final Logger logger = LoggerFactory.getLogger(Entity_Helper.class);


    public static  <TEntity> TEntity getUserEntity(ResultSet rs,Class<TEntity> clazz) throws Exception {

        if(rs == null || clazz == null){
            return null;
        }

        TEntity tEntity = clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();

        if(fields == null ||fields.length == 0){
            return tEntity;
        }

        for(Field field :fields){
            Column annotation = field.getAnnotation(Column.class);

            if(annotation == null){
                continue;
            }

            String columnName = annotation.name();

            Object value = rs.getObject(columnName);

            if(value == null){
                continue;
            }

            String fieldName = field.getName();
            String setMethodName = "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1,fieldName.length());

            Method method = UserEntity.class.getMethod(setMethodName,field.getType());

            if(method == null){
                logger.error("找不到对应的set方法:"+UserEntity.class.getName()+"."+fieldName);
            }

            method.invoke(tEntity,value);
        }

        return tEntity;

    }

}

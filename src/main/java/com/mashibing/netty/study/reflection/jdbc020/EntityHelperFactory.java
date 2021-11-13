package com.mashibing.netty.study.reflection.jdbc020;

import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用反射加注解的方式动态构建entity
 * @author hugangquan
 * @date 2021/11/13 17:32
 */
public class EntityHelperFactory {

    private static final Map<Class<?>,AbstractEntityHelper> entityHelperMap = new HashMap<Class<?>, AbstractEntityHelper>();

    private static final Logger logger = LoggerFactory.getLogger(EntityHelperFactory.class);


    public static  <TEntity> AbstractEntityHelper getEntityHelper(Class<TEntity> clazz) throws Exception {

        if(clazz == null){
            return null;
        }

        if(entityHelperMap.containsKey(clazz)){
            return entityHelperMap.get(clazz);
        }

        String helperClazzName = clazz.getSimpleName()+"_Helper";

        ClassPool classPool = ClassPool.getDefault();
        //添加classpath
        classPool.appendSystemPath();

        classPool.importPackage(ResultSet.class.getName());
        classPool.importPackage(clazz.getName());

        //获取AbstractEntityHelper类的CtClass对象
        CtClass abstractEntityHelperClass = classPool.getCtClass(AbstractEntityHelper.class.getName());
        //创建字节码对象,AbstractEntityHelper作为父类
        CtClass ctClass = classPool.makeClass(helperClazzName, abstractEntityHelperClass);

        //创建构建方法
        CtConstructor ctConstructor = new CtConstructor(new CtClass[0],ctClass);
        ctConstructor.setBody("{}");
        ctClass.addConstructor(ctConstructor);

        StringBuffer methodContent = new StringBuffer();

        methodContent.append("public Object getEntity( java.sql.ResultSet rs ){\n");

        methodContent.append("if(rs == null){\n return null;\n}\n");

        methodContent.append(clazz.getName())
                .append(" entity = new ")
                .append(" ")
                .append(clazz.getName())
                .append("();\n");

       Field[] declaredFields = clazz.getDeclaredFields();

        if(declaredFields != null && declaredFields.length!=0){
            for(Field field :declaredFields){
                Column annotation = field.getAnnotation(Column.class);
                if(annotation == null){
                    continue;
                }

                String fieldName = field.getName();
                String columnName = annotation.name();

                if(field.getType().equals(Integer.class)){

                    methodContent.append("int ").append(columnName).append("= rs.getInt(\""+columnName+"\");\n");
                    methodContent.append("Integer num = Integer.valueOf(").append(columnName).append(");\n");


                    methodContent.append("entity.")
                            .append("set")
                            .append(fieldName.substring(0,1).toUpperCase())
                            .append(fieldName.substring(1,fieldName.length()))
                            .append("(")
                            .append("num")
                            .append(");\n");

                }else if(field.getType().equals(String.class)){

                    methodContent.append("String ")
                            .append(fieldName)
                            .append("= rs.getString(\""+columnName+"\");\n");

                    methodContent.append("if(").append(fieldName).append(" != null").append("){\n");
                    methodContent.append("entity.")
                            .append("set")
                            .append(fieldName.substring(0,1).toUpperCase())
                            .append(fieldName.substring(1,fieldName.length()))
                            .append("(")
                            .append(fieldName)
                            .append(");\n");
                    methodContent.append("}\n");


                }
            }
        }

        methodContent.append("return entity;\n");
        methodContent.append("}\n");

        CtMethod ctMethod = CtMethod.make(methodContent.toString(),ctClass);
        ctClass.addMethod(ctMethod);

        String filePath = EntityHelperFactory.class.getClassLoader().getResource("").getFile();
        filePath = URLDecoder.decode(filePath,"utf-8");
        ctClass.writeFile(filePath);

        Class aClass = ctClass.toClass();
        AbstractEntityHelper helper = (AbstractEntityHelper)aClass .newInstance();

        entityHelperMap.put(clazz,helper);

        return helper;
    }

}

package com.mashibing.netty.study.reflection.jdbc020;

import java.sql.ResultSet;

/**
 * 抽象的entity助手
 * @author hugangquan
 * @date 2021/11/13 18:07
 */
public abstract class AbstractEntityHelper {

    public abstract Object getEntity(ResultSet rs);

}

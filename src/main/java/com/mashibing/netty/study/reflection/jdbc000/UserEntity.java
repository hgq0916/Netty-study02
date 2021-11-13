package com.mashibing.netty.study.reflection.jdbc000;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hugangquan
 * @date 2021/11/13 17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Integer id;

    private String name;

    private Integer age;

    private String  phone;

}

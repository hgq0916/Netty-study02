package com.mashibing.netty.study.reflection.jdbc020;

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

    @Column(name="id")
    private Integer id;

    @Column(name="user_name")
    private String name;

    @Column(name="age")
    private Integer age;

    @Column(name="phone")
    private String  phone;

}

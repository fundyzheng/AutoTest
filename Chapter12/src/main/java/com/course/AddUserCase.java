package com.course;

import lombok.Data;

/**
 * Created by Administrator on 2018/7/28.
 */
@Data
public class AddUserCase {
    private int id;
    private String userName;
    private String password;
    private String sex;
    private int age;
    private String permission;
    private String isDelete;
    private String expected;
}

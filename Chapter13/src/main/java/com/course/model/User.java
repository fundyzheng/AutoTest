package com.course.model;

import lombok.Data;

/**
 * Created by Administrator on 2018/8/19.
 */
@Data
public class User {
    private int id;
    private String userName;
    private int age;
    private String sex;
    private String permission;
    private String isDelete;
    private String password;
}

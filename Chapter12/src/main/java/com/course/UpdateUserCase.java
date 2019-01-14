package com.course;

import lombok.Data;

/**
 * Created by Administrator on 2018/7/28.
 */
@Data
public class UpdateUserCase {
    private int id;
    private int userId;
    private String userName;
    private String sex;
    private int age;
    private String permission;
    private String isDelete;
    private String expected;
}

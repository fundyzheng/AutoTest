package com.course;

import lombok.Data;

/**
 * Created by Administrator on 2018/7/28.
 */
@Data
public class GetUserListCase {
    private int id;
    private String userName;
    private int age;
    private String sex;
    private String expected;
}

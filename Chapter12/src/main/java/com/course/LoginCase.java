package com.course;

import lombok.Data;

/**
 * Created by Administrator on 2018/7/28.
 */
@Data
public class LoginCase {
    private  int id;
    private String userName;
    private String password;
    private String expected;

}

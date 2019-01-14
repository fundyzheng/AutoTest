package com.course;

import lombok.Data;

/**
 * Created by Administrator on 2018/7/28.
 */
@Data
public class User {
    private int id;
    private String userName;
    private int age;
    private String password;
    private String sex;
    private String permission;
    private String isDelete;
    @Override
    public String toString(){
        return (
            "{id:"+id+","+
            "name:"+userName+","+
            "age:"+age+","+
            "password:"+password+","+
            "sex:"+sex+","+
            "permission:"+permission+","+
            "isDelete:"+isDelete+"}"

                );
    }


}

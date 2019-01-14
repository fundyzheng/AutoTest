package com.course.utils;

import com.course.InterfaceName;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2018/7/28.
 * 根据传入的InterfaceName的值拼接对应的Uri，最终返回拼接后的uri
 */
public class ConfigFile {
    private  static ResourceBundle bundle=ResourceBundle.getBundle("application", Locale.CHINA);

    public static String getUri(InterfaceName interfaceName){
        String address=bundle.getString("test.uri");
        String  resultUri;//最终的测试地址
        String  uri="";
        if (interfaceName==InterfaceName.ADDUSERINFO) {
            uri = bundle.getString("addUser.uri");
        }
        if (interfaceName==InterfaceName.GETUSERINFO){
            uri=bundle.getString("getUserInfo.uri");
        }
        if (interfaceName==InterfaceName.GETUSERLIST){
            uri=bundle.getString("getUserList.uri");
        }
        if (interfaceName==InterfaceName.LOGIN){
            uri=bundle.getString("login.uri");
        }
        if (interfaceName==InterfaceName.UPDATEUSERINFO){
            uri=bundle.getString("updateUserInfo.uri");
        }
        resultUri=address+uri;
        return resultUri;

    }
}

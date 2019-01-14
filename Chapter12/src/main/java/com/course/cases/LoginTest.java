package com.course.cases;

import com.course.InterfaceName;
import com.course.LoginCase;
import com.course.config.TestConfig;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Administrator on 2018/7/28.
 */
public class LoginTest {
    @BeforeTest(groups = "loginTrue",description = "测试准备工作，获取httpclient等")
    public void beforeTest(){
        TestConfig.getUserInfoUrl= ConfigFile.getUri(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl=ConfigFile.getUri(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl=ConfigFile.getUri(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl=ConfigFile.getUri(InterfaceName.UPDATEUSERINFO);
        TestConfig.addUserUrl=ConfigFile.getUri(InterfaceName.ADDUSERINFO);

        TestConfig.defaultHttpClient=new DefaultHttpClient();

    }
    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession sqlSession= DatabaseUtil.getSqlSession();
        LoginCase loginCase=sqlSession.selectOne("loginTrue",3);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        //发送请求，获取结果
        String result=getResult(loginCase);
        //验证结果
        Assert.assertEquals(result,loginCase.getExpected());

    }


    @Test(groups = "loginFasle",description = "用户登录失败接口测试")
    public void loginFasle() throws IOException {
        SqlSession sqlSession= DatabaseUtil.getSqlSession();
        LoginCase loginCase=sqlSession.selectOne("loginTrue",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        //发送请求，获取结果
        String result=getResult(loginCase);
        //验证结果
        Assert.assertEquals(result,loginCase.getExpected());

    }
    private String getResult(LoginCase loginCase) throws IOException {
        HttpPost httpost=new HttpPost(TestConfig.loginUrl);
        JSONObject param=new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());

        httpost.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        httpost.setEntity(entity);
        String result;
        HttpResponse response=TestConfig.defaultHttpClient.execute(httpost);
        result= EntityUtils.toString(response.getEntity(),"utf-8");
       // result= EntityUtils.toString(TestConfig.defaultHttpClient.execute(httpost).getEntity(),"utf-8");
        TestConfig.store=TestConfig.defaultHttpClient.getCookieStore();
        return  result;
    }
}

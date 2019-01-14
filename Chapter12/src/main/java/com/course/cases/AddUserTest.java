package com.course.cases;

import com.course.AddUserCase;
import com.course.User;
import com.course.config.TestConfig;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Administrator on 2018/7/31.
 */
public class AddUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser() throws IOException, InterruptedException {
        SqlSession sqlSession= DatabaseUtil.getSqlSession();
       AddUserCase addUserCase= sqlSession.selectOne("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

         //向接口发请求，获取结果
        String result=getResult(addUserCase );

        //验证返回结果
        Thread.sleep(3000);
        User user=sqlSession.selectOne("addUser",addUserCase);
        System.out.println(user.toString());

        Assert.assertEquals(result,addUserCase.getExpected());


    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.addUserUrl);
        JSONObject param=new JSONObject();
        param.put("id",addUserCase.getId());
        param.put("userName",addUserCase.getUserName());
        param.put("age",addUserCase.getAge());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());

        //设置头信息
        post.setHeader("content-type","application/json");

        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;//存放返回结果
        HttpResponse respons=TestConfig.defaultHttpClient.execute(post);
        result= EntityUtils.toString(respons.getEntity(),"utf-8");

        System.out.println(result);

        return  result;
    }
}

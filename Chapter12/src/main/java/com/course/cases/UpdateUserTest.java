package com.course.cases;

import com.course.UpdateUserCase;
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
public class UpdateUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息")
    public void updateUserTest() throws IOException {
        SqlSession sqlSession=DatabaseUtil.getSqlSession();
        UpdateUserCase updateUserCase= sqlSession.selectOne("updateUser",1);
        System.out.println(updateUserCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);

        //发送请求
        int result=getResult(updateUserCase);

        //验证
        User user=sqlSession.selectOne(updateUserCase.getExpected(),updateUserCase);

        Assert.assertNotNull(user);//查到的结果不为空
        Assert.assertNotNull(result);
    }



    @Test(dependsOnGroups = "loginTrue",description = "删除用户信息")
    public void deletUserTest() throws IOException {
        SqlSession sqlSession=DatabaseUtil.getSqlSession();
        UpdateUserCase updateUserCase= sqlSession.selectOne("updateUser",2);
        System.out.println(updateUserCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);


        //发送请求
        int result=getResult(updateUserCase);

        //验证
        User user=sqlSession.selectOne(updateUserCase.getExpected(),updateUserCase);

        Assert.assertNotNull(user);//查到的结果不为空
        Assert.assertNotNull(result);
    }

    private int getResult(UpdateUserCase updateUserCase) throws IOException {
        HttpPost post=new HttpPost(TestConfig.updateUserInfoUrl);
        JSONObject param=new JSONObject();
        param.put("id",updateUserCase.getUserId());//容易写成getId()
        param.put("userName",updateUserCase.getUserName());
        param.put("age",updateUserCase.getAge());
        param.put("sex",updateUserCase.getSex());
        param.put("isDelete",updateUserCase.getIsDelete());
        param.put("permission",updateUserCase.getPermission());

        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");

        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);

        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        String result;

        result= EntityUtils.toString(response.getEntity(),"utf-8");
        return Integer.parseInt(result);
    }

}

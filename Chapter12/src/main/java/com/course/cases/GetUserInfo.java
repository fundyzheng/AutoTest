package com.course.cases;

import com.course.GetUserInfoCase;
import com.course.User;
import com.course.config.TestConfig;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */

public class GetUserInfo {
    @Test(dependsOnGroups = "loginTrue",description = "测试获取id为1的用户信息")
    public void getUserInfoTest() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfo = sqlSession.selectOne("getUserInfoCase", 1);
        System.out.println(getUserInfo.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        //发送请求，获取到实际结果
        JSONArray resultjson =getJsonResult(getUserInfo);

        //验证
        //预期结果部分
        User user=sqlSession.selectOne(getUserInfo.getExpected(),getUserInfo);//在数据库中查找用户信息
        List userList=new ArrayList();
        userList.add(user);
        //把userList放到List中才能转换成JSONArray

        JSONArray jsonArray=new JSONArray(userList);
        //实际结果和预期结果进行比较
         JSONArray resultjson1=new JSONArray(resultjson.getString(0)); //
        Assert.assertEquals(resultjson1.toString(),jsonArray.toString());
    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfo) throws IOException {
        HttpPost post=new HttpPost( TestConfig.getUserInfoUrl);

        JSONObject param=new JSONObject();
        param.put("id",getUserInfo.getUserId());


        post.setHeader("content-type","application/json");
        StringEntity entity=new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response=TestConfig.defaultHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity(),"utf-8");

        List resultList= Arrays.asList(result);
        JSONArray array=new JSONArray(resultList);

        return  array;
    }


}

package com.plivo.api.tests;

import com.plivo.api.Common.Common;
import com.plivo.api.properties.TestConstants;
import com.plivo.api.util.RestUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;


public class SlackTest {
    public static String channelName;
    public static String newChannelName;
    public static String channelId;

    @BeforeClass
    public void preSetUp(){
        channelName="shruti_qa_"+Common.randomAlphaNumeric(8);
        newChannelName = Common.randomAlphaNumeric(8);
    }

    /*
    Delete the channel created
     */

    @AfterClass
    public void cleanUp(){

    }


    @Test(priority = 1)
    public void AddErrorTest() throws Exception {
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, String> queryParams = new HashMap<String, String>();
        headers.put("Content-Type", TestConstants.applicationJSON);
        queryParams.put("token",TestConstants.tokenVal);
        queryParams.put("name",channelName);
        String url = TestConstants.slackURL+"create";
        Response response = RestUtil.postWithQuery(url,headers,queryParams);
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);

        String restStr = response.getBody().asString();

        JsonPath jsonPath = new JsonPath(restStr);
        String name = jsonPath.get("channel.name");
        channelId  = jsonPath.get("channel.id");
                Assert.assertEquals(name,channelName);
    }


    @Test(priority = 2)
    public void joinChannel(){
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, String> queryParams = new HashMap<String, String>();
        headers.put("Content-Type", TestConstants.applicationJSON);
        queryParams.put("token",TestConstants.tokenVal);
        queryParams.put("name",channelName);
        String url = TestConstants.slackURL+"join";
        Response response = RestUtil.postWithQuery(url,headers,queryParams);
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);

        String restStr = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(restStr);
        String name = jsonPath.get("channel.name");
        Assert.assertEquals(name,channelName);
    }

    @Test(priority = 3)
    public void renameChannel(){
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, String> queryParams = new HashMap<String, String>();
        headers.put("Content-Type", TestConstants.applicationJSON);
        queryParams.put("token",TestConstants.tokenVal);
        queryParams.put("channel",channelId);
        queryParams.put("name",newChannelName);
        String url = TestConstants.slackURL+"rename";
        Response response = RestUtil.postWithQuery(url,headers,queryParams);
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);

        String restStr = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(restStr);
        String name = jsonPath.get("channel.name");
        Assert.assertEquals(name,newChannelName);
    }

    /**
     * listing all the channels and valicating if the ablve channel is changed successfully
     */

    @Test(priority = 4)
    public void listChannelsAndValidate(){
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, String> queryParams = new HashMap<String, String>();
        headers.put("Content-Type", TestConstants.applicationJSON);
        queryParams.put("token",TestConstants.tokenVal);
        String url = TestConstants.slackURL+"list";
        Response response = RestUtil.getByQueryParams(queryParams,url,headers);
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);

        String restStr = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(restStr);
        List<String> ids = jsonPath.get("channels.id");
        List<String> names= jsonPath.get("channels.name");
        for (int i=0;i<ids.size();i++){
            if(ids.get(i).equalsIgnoreCase(channelId)){
                System.out.println("\n Name is : " + names.get(i));
                Assert.assertEquals(names.get(i),newChannelName);
            }
        }

}

    @Test(priority=5)
    public void archiveChannel(){
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, String> queryParams = new HashMap<String, String>();
        headers.put("Content-Type", TestConstants.applicationJSON);
        queryParams.put("token",TestConstants.tokenVal);
        queryParams.put("channel",channelId);
        String url = TestConstants.slackURL+"archive";
        Response response = RestUtil.postWithQuery(url,headers,queryParams);
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);

    }


    @Test(priority = 6)
    public void validateAcrchivedChannel(){
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, String> queryParams = new HashMap<String, String>();
        headers.put("Content-Type", TestConstants.applicationJSON);
        queryParams.put("token",TestConstants.tokenVal);
        String url = TestConstants.slackURL+"list";
        Response response = RestUtil.getByQueryParams(queryParams,url,headers);
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);

        String restStr = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(restStr);
        List<String> ids = jsonPath.get("channels.id");
        List<Boolean> is_archived= jsonPath.get("channels.is_archived");
        for (int i=0;i<ids.size();i++){
            if(ids.get(i).equalsIgnoreCase(channelId)){
                System.out.println("\n Name is : " + is_archived.get(i));
                Assert.assertTrue(is_archived.get(i));
            }
        }

    }


}

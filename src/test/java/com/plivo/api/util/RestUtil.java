package com.plivo.api.util;


import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;


public class RestUtil {

    public static Response postWithQuery(String url,Map<String,?> headers, Map<String, String> queryParams){
        Response response;
            response = RestAssured.given().urlEncodingEnabled(false).headers(headers).log().all().queryParams(queryParams).post(url);

        return response;
    }



    public static Response get(String url, Map<String, ?> headers) {
        Response response;
        response = RestAssured.given().urlEncodingEnabled(false).headers(headers).log().all().get(url);
        return response;
    }

    public static Response getByQueryParams(Map<String, String> queryParams, String path, Map<String, ?> headers) {
        Response response;
        response = RestAssured.given().urlEncodingEnabled(false).headers(headers).log().all().queryParams(queryParams).get(path);
        return response;
    }


}

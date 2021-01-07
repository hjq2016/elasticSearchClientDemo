package com.hjq.clientDemo.start;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author hjq
 * @Date 2020/12/30
 */
@Slf4j
public class FirstConnect {

    private static RestClient restClient;


    public static void main(String[] args) throws IOException {
        restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();
        FirstConnect firstConnect = new FirstConnect();
//        firstConnect.getAll();
//        firstConnect.putString();
        firstConnect.getAll();
        restClient.close();


    }

    private void putString() {
        for (int i = 0; i < 10; i++) {
            Request request = new Request("PUT", "/hjq/_doc/" + i);
            request.addParameter("pretty", "true");
            Map<String, String> map = new HashMap<>();
            map.put("name", "austin" + i);
            request.setJsonEntity(JSONObject.toJSONString(map));
            log.info(response(request));
        }
    }

    private void getAll() {
        Request request = new Request("GET", "/hjq/_search");
        request.addParameter("pretty", "true");
        Map<String, Object> map = new HashMap<>();
        map.put("from", "0");
        map.put("size", "3");
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
//        map2.put("name", "austin0");
//        map1.put("match", map2);
//        map.put("query", map1);
        log.info(JSONObject.toJSONString(map));
        request.setJsonEntity(JSONObject.toJSONString(map));
        log.info(response(request));
    }

    private String response(Request request) {
        try {
            Response response = restClient.performRequest(request);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}

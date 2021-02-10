package com.example.demo.service;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class HttpClientService {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    // 如果是不带参数的get请求
    public String doGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        // 请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        return response.getStatusLine().getStatusCode() == 200 ?
                EntityUtils.toString(response.getEntity(), "UTF-8") : null;
    }

    // 如果是带参数的get请求
    public String doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (map != null) {
            // 遍历map请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return doGet(uriBuilder.build().toString());
    }

    // 如果是带参数的post请求
    public String doPost(String url, Map<String, Object> map) throws Exception {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            httpPost = new HttpPost(url);
            // 设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
                httpPost.setEntity(entity);
            }
            // 请求
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "utf-8");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    // 如果是不带参数的post请求
    public String doPost(String url) throws Exception {
        return this.doPost(url, null);
    }

}
package com.fh.shop.api.utils;


import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    public  static  String postMsg(String url, Map<String,String> header, Map<String ,String> params ){
        CloseableHttpClient client =null;
        HttpPost httpPost =null;
        CloseableHttpResponse execute =null;
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        String string ="";
        try {
        //打开浏览器
         client = HttpClientBuilder.create().build();
        //输入url路径
         httpPost = new HttpPost(url);

        //判断头信息
        if(null != header  && header.size()>0){
            Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
            //循环map
            while(iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                httpPost.addHeader(key,value);
            }
        }
        //判断参数信息
        if(null != params  && params.size()>0){
            List<BasicNameValuePair> list = new ArrayList<>();
            Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                list.add(basicNameValuePair);
            }
            urlEncodedFormEntity = new UrlEncodedFormEntity(list, "utf-8");

            httpPost.setEntity(urlEncodedFormEntity);
        }
        //发送请求
         execute = client.execute(httpPost);

            string=EntityUtils.toString(execute.getEntity(), "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            if(execute!=null){
                try {
                    execute.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=httpPost){
                httpPost.releaseConnection();
            }
            if(null != client){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    return string;
    }




}

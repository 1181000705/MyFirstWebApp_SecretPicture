package com.example.appw.TwoFragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Map;

public class postutils {
    public static String HttpPostForm(String url, Map<String,String> params) throws IOException{
        HttpClient client = HttpClients.createDefault();
        StringBuilder requestBodyBuilder = new StringBuilder();
        HttpPost post = new HttpPost(url);
        //设置请求头部
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        post.setHeader("Connection", "keep-alive");
        //设置请求内容username=夏时雨&piccode=134567
        for (Map.Entry<String,String> entry:params.entrySet()){
            requestBodyBuilder.append(entry.getKey());
            requestBodyBuilder.append("=");
            requestBodyBuilder.append(entry.getValue());
            requestBodyBuilder.append("&");
        }
        requestBodyBuilder.deleteCharAt(requestBodyBuilder.length() - 1);
        String param = requestBodyBuilder.toString();
        post.setEntity(new StringEntity(param));
        HttpResponse response = client.execute(post);
        byte[] content = new byte[2048];
        response.getEntity().getContent().read(content);
        return new String(content);
    }
}

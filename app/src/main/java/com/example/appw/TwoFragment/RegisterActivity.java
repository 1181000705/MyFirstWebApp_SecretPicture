package com.example.appw.TwoFragment;

import android.app.Activity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {
    private String piccode;
    private String username;
    private String sessionid;
    private int userid;
    private String url = "http://172.20.161.108:8080/doRegister";
    Map<String,String> params = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            initView();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    private void initView() throws IOException {
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        piccode = bundle.getString("piccode");
//        username=Data.user_name[Data.getuserwhere()];
//        sessionid=bundle.getString("sessionid");//从twoframent来的
//        params.put("username",username);
//        params.put("piccode",piccode);
//        params.put("sessionid",sessionid);
//        String resp = postutils.HttpPostForm(url,params);
//        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//    }
}

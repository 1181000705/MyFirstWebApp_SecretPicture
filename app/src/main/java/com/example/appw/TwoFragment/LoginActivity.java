package com.example.appw.TwoFragment;

import android.app.Activity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    private String piccode;
    private String username;
    private String sessionid;
    private String url = "http://172.20.161.108:8080/doLogin";
    Map<String,String> params = new HashMap<>();


    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
//        try {
//            init();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    private void init() throws IOException {
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        piccode = bundle.getString("piccode");
//        username= Data.user_name[Data.getuserwhere()];
//        sessionid = bundle.getString("sessionid");
//        params.put("username",username);
//        params.put("piccode",piccode);
//        params.put("sessionid",sessionid);
//        String resp = postutils.HttpPostForm(url,params);
//        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//
//    }


}

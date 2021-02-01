package com.example.appw.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appw.Data;
import com.example.appw.MainActivity;
import com.example.appw.R;
import com.google.android.material.textfield.TextInputEditText;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edt_user;
    TextInputEditText edt_pwd;
    Button btn_login;
    Button bbsign;
    public String User="0";
    public String Pwd="0";
    AVLoadingIndicatorView avLoadingIndicatorView;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x1:
                    Toast.makeText(LoginActivity.this, "gogo", Toast.LENGTH_SHORT).show();
                    break;
                case 0x2:
                    MainActivity.norlogin=true;
                    Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    //设置要回传的数据
                    Bundle bundle = new Bundle();
                    //EXTRA_GOODSLIST
                    bundle.putString("user",User );
                    bundle.putString("pwd",Pwd);
                    intent.putExtras(bundle);
                    //设置结果码标识当前Activity，回传数据。不管多早调用这句代码，这句代码在当前Activity销毁时才会执行，即此Activity销毁时才会回传数据。请求码和结果码不必相同。
                    setResult(1,intent);
                    finish();
                    break;
                case 0x3:
                    edt_pwd.setError("无此用户名或密码错误");
                    avLoadingIndicatorView.hide();
                    break;
                case 0x5:
                    Toast.makeText(LoginActivity.this, "无网络连接超时", Toast.LENGTH_SHORT).show();
                    avLoadingIndicatorView.hide();
                    break;
                default:
                    Toast.makeText(LoginActivity.this, "不知道什么东西", Toast.LENGTH_SHORT).show();
                    avLoadingIndicatorView.hide();
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        avLoadingIndicatorView= (AVLoadingIndicatorView) findViewById(R.id.avi);
        avLoadingIndicatorView.setIndicator("BallSpinFadeLoaderIndicator");
        avLoadingIndicatorView.hide();


        edt_user = findViewById(R.id.edt_user);
        edt_pwd = findViewById(R.id.edt_pwd);
        edt_pwd.setInputType(129);


        btn_login =findViewById(R.id.btn_loginn);
        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                User = edt_user.getText().toString().trim();
                Pwd = edt_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(User) && TextUtils.isEmpty(Pwd)) {
                    Toast.makeText(LoginActivity.this, "user or pwd not null", Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        //Toast.makeText(MainActivity.this, "gogogo", Toast.LENGTH_SHORT).show();
                        LoginRequest(User,Pwd);
                    }
                };
                if ("1".equals(User) && "123456".equals(Pwd)) {
                    Data.Userid="1";finish();
                }else if ("2".equals(User) && "123456".equals(Pwd)) {
                    Data.Userid="2";finish();
                }else if ("3".equals(User) && "123456".equals(Pwd)) {
                    Data.Userid="3";finish();
                }else if ("4".equals(User) && "123456".equals(Pwd)) {
                    Data.Userid="4";finish();
                }else if (Data.newuser.equals(User) && Data.newpwd.equals(Pwd)) {
                    Data.Userid="5";finish();
                }else thread.start();
                avLoadingIndicatorView.show();
//                if ("威威喵".equals(user) && "123456".equals(pwd)) {
//                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                } else
//                if (!"威威喵".equals(user)) {
//                    edt_user.setError("用户名错误");
//                }
//                else if (!"123456".equals(pwd)) {
//                    edt_pwd.setError("密码错误");
//                }
            }
        });
        bbsign=findViewById(R.id.signup);
        bbsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(LoginActivity.this, signupActivity.class);
                startActivity(intents);
            }
        });
    }
    public static String pubURL = "http://m30n528474.qicp.vip/MyFirstWebApp/LoginServlet";
//    public static String pubURL = "http://129.211.54.146:80/";
    public  void LoginRequest(final String accountNumber, final String password) {

        //请求地址
       // String url = "http://29om310889.zicp.vip/MyFirstWebApp/LoginServlet";    //注①
//        String url = "http://127.0.0.1:8080/";
        String url = LoginActivity.pubURL;
        String tag = "Login";    //注②

        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);

        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");  //注③
                            String result = jsonObject.getString("Result");  //注④
                            if (result.equals("success")) {  //注⑤
                                //做自己的登录成功操作，如页面跳转
                                Message message=Message.obtain();
                                message.what=0x2;
                                message.obj=jsonObject;
                                handler.sendMessage(message);
                            } else {
                                //做自己的登录失败操作，如Toast提示
                                Message message=Message.obtain();
                                message.what=0x3;
                                handler.sendMessage(message);
                            }
                        } catch (JSONException e) {
                            //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                            Message message=Message.obtain();
                            message.what=0x5;
                            handler.sendMessage(message);
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                avLoadingIndicatorView.hide();
                Toast.makeText(LoginActivity.this, "登陆超时，请之后重试", Toast.LENGTH_SHORT).show();
                Log.e("TAGtimerunout", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccountNumber", accountNumber);  //注⑥
                params.put("Password", password);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy(20*1000, 0, 1.0f));
        //将请求添加到队列中
        requestQueue.add(request);
    }
}

package com.example.appw.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appw.Data;
import com.example.appw.R;
import com.google.android.material.textfield.TextInputEditText;
import com.wang.avi.AVLoadingIndicatorView;

public class signupActivity extends AppCompatActivity {
    AVLoadingIndicatorView avLoadingIndicatorView;
    TextInputEditText edt_phone;
    TextInputEditText edt_yanzheng;
    TextInputEditText edt_pwdd;
    TextInputEditText edt_username;
    Button btn_commit;
    Button bbsign;
    public String username="0";
    public String phone="0";
    public String yanzheng="0";
    public String pwdd="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        avLoadingIndicatorView= (AVLoadingIndicatorView) findViewById(R.id.avi11);
        avLoadingIndicatorView.setIndicator("BallSpinFadeLoaderIndicator");
        avLoadingIndicatorView.hide();

        edt_username = findViewById(R.id.edt_username);
        edt_phone = findViewById(R.id.edt_phone);
        edt_yanzheng = findViewById(R.id.edt_yanzheng);
        edt_pwdd=findViewById(R.id.edt_pwdd);
        edt_pwdd.setInputType(129);


        btn_commit =findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username = edt_username.getText().toString().trim();
                phone = edt_phone.getText().toString().trim();
                yanzheng = edt_yanzheng.getText().toString().trim();
                pwdd = edt_pwdd.getText().toString().trim();
                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(yanzheng)&&TextUtils.isEmpty(pwdd)) {
                    Toast.makeText(signupActivity.this, "user or pwd or yanzheng not null", Toast.LENGTH_SHORT).show();
                    return;
                }
                Data.newuser=username;
                Data.newpwd=pwdd;
                Data.Userid="5";
                finish();
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

    }
}

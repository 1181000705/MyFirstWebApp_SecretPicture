package com.example.appw.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appw.R;
import com.google.android.material.textfield.TextInputEditText;
import com.wang.avi.AVLoadingIndicatorView;

public class modifyActivity extends AppCompatActivity {
    AVLoadingIndicatorView avLoadingIndicatorView;
    TextInputEditText edt_pwd1;
    TextInputEditText edt_pwd2;
    TextInputEditText edt_pwd3;
    Button btn_modify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        avLoadingIndicatorView= (AVLoadingIndicatorView) findViewById(R.id.avi11);
        avLoadingIndicatorView.setIndicator("BallSpinFadeLoaderIndicator");
        avLoadingIndicatorView.hide();
        edt_pwd1=findViewById(R.id.edt_pwd1);
        edt_pwd2=findViewById(R.id.edt_pwd2);
        edt_pwd3=findViewById(R.id.edt_pwd3);
        edt_pwd1.setInputType(129);
        edt_pwd2.setInputType(129);
        edt_pwd3.setInputType(129);
        btn_modify=findViewById(R.id.btn_reg1);
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

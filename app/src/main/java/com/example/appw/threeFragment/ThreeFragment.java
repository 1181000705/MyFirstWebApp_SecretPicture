package com.example.appw.threeFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appw.Data;
import com.example.appw.Login.LoginActivity;
import com.example.appw.Login.modifyActivity;
import com.example.appw.MainActivity;
import com.example.appw.R;

public class ThreeFragment extends Fragment {


    de.hdodenhof.circleimageview.CircleImageView UserPicture;
    TextView user_name;
    TextView user_val;


    @Override
    public void onResume() {
        super.onResume();
        UserPicture.setImageResource(Data.userpic[Data.getuserwhere()]);//头像
        user_name.setText(Data.user_name[Data.getuserwhere()]);//名字
        user_val.setText(Data.user_val[Data.getuserwhere()]);//电话号码
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_three, container, false);

        UserPicture=view.findViewById(R.id.h_head);//头像的位置
        user_name=view.findViewById(R.id.user_name);//名字的位置
        user_val=view.findViewById(R.id.user_val);//电话号码的位置

        UserPicture.setImageResource(Data.userpic[Data.getuserwhere()]);
        user_name.setText(Data.user_name[Data.getuserwhere()]);
        user_val.setText(Data.user_val[Data.getuserwhere()]);
        Button button=view.findViewById(R.id.btn_login);//退出登录的按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.norlogin){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);//点击后进入登录界面
                    UserPicture.setImageResource(Data.userpic[Data.getuserpic(Integer.valueOf(Data.Userid))]);
                }
                else {

                }

            }
        });

        LinearLayout infomation = view.findViewById(R.id.infomarion);//我的资料
        infomation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), informationActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout record = view.findViewById(R.id.records);//使用记录
        record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), recordActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout modify = view.findViewById(R.id.modify);//修改密码
        modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), modifyActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout instructions = view.findViewById(R.id.instructions);//使用说明
        instructions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), instructionsActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }

}

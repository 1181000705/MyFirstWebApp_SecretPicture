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
        UserPicture.setImageResource(Data.userpic[Data.getuserwhere()]);
        user_name.setText(Data.user_name[Data.getuserwhere()]);
        user_val.setText(Data.user_val[Data.getuserwhere()]);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_three, container, false);

//        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ItemView);
//        isShowBottomLine = ta.getBoolean(R.styleable.ItemView_show_bottom_line, true);//得到是否显示底部下划线属性
//        isShowLeftIcon = ta.getBoolean(R.styleable.ItemView_show_left_icon, true);//得到是否显示左侧图标属性标识
//        isShowRightArrow = ta.getBoolean(R.styleable.ItemView_show_right_arrow, true);//得到是否显示右侧图标属性标识
//        leftIcon.setBackground(ta.getDrawable(R.styleable.ItemView_left_icon));//设置左侧图标
//        leftTitle.setText(ta.getString(R.styleable.ItemView_left_text));//设置左侧标题文字
//        leftIcon.setVisibility(isShowLeftIcon ? View.VISIBLE : View.INVISIBLE);//设置左侧箭头图标是否显示 rightDesc.setText(ta.getString(R.styleable.ItemView_right_text));//设置右侧文字描述
//        bottomLine.setVisibility(isShowBottomLine ? View.VISIBLE : View.INVISIBLE);//设置底部图标是否显示
//        rightArrow.setVisibility(isShowRightArrow ? View.VISIBLE : View.INVISIBLE);//设置右侧箭头图标是否显示
        UserPicture=view.findViewById(R.id.h_head);
        user_name=view.findViewById(R.id.user_name);
        user_val=view.findViewById(R.id.user_val);
        UserPicture.setImageResource(Data.userpic[Data.getuserwhere()]);
        user_name.setText(Data.user_name[Data.getuserwhere()]);
        user_val.setText(Data.user_val[Data.getuserwhere()]);
        Button button=view.findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.norlogin){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    UserPicture.setImageResource(Data.userpic[Data.getuserpic(Integer.valueOf(Data.Userid))]);
                }
                else {

                }

            }
        });

        LinearLayout infomation = view.findViewById(R.id.infomarion);
        infomation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), informationActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout record = view.findViewById(R.id.records);
        record.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), recordActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout modify = view.findViewById(R.id.modify);
        modify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), modifyActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout instructions = view.findViewById(R.id.instructions);
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

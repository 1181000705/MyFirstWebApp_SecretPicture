package com.example.appw.threeFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.appw.Data;
import com.example.appw.R;

public class informationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        TextView Userid=findViewById(R.id.information_id);
        Userid.setText(Data.Userid);
    }
}

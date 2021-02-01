package com.example.appw;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class FingerDialog extends Dialog implements View.OnClickListener {
    //    style引用style样式
    Button dialogbtn;
    Button dialogbtns;
    LinearLayout finger;
    public FingerDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        finger=findViewById(R.id.finger);
        finger.setOnClickListener(this);
    }

    public FingerDialog(Context context, int width, int height, View layout, int style, int s) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        dialogbtns=findViewById(R.id.dialogbtns);
        dialogbtns.setOnClickListener(this);
    }
    private OnCenterItemClickListener listener;


    @Override
    public void onClick(View v) {
        dismiss();
        listener.OnCenterItemClick(this,v);

    }

    public interface OnCenterItemClickListener {
        void OnCenterItemClick(FingerDialog dialog, View view);
    }
    //很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    private OnCenterItemClickListeners listeners;
    String sid;
    public interface OnCenterItemClickListeners {
        void OnCenterItemClicks(FingerDialog dialog, View view, String sID);
    }
    //很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListeners(OnCenterItemClickListeners listeners) {
        this.listeners = listeners;
    }

}
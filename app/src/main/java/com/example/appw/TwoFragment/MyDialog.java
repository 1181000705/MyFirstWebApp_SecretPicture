package com.example.appw.TwoFragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.appw.Adapter.RecyclerLineAdapter;
import com.example.appw.Data;
import com.example.appw.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;

public class MyDialog extends Dialog implements View.OnClickListener,RecyclerLineAdapter.OnItemClickListener {
    //    style引用style样式
    Button dialogbtn;
    Button dialogbtns;
    Handler handler1=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            swipeRefreshLayout.setRefreshing(true);
        }
    };
    Handler handler2=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            swipeRefreshLayout.setRefreshing(false);
//            swipeRefreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();

        }
    };
    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        id=findViewById(R.id.userid);

        startyear=findViewById(R.id.strayyear);
        startmonth=findViewById(R.id.startmonth);
        startday=findViewById(R.id.startday);

        endyear=findViewById(R.id.endyear);
        endmonth=findViewById(R.id.endmonth);
        endday=findViewById(R.id.endday);

        dialogbtn=findViewById(R.id.dialogbtn);
        dialogbtn.setOnClickListener(this);
    }

    public MyDialog(Context context, int width, int height, View layout, int style,int s) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        subjectid=findViewById(R.id.sid);

        dialogbtns=findViewById(R.id.dialogbtns);
        dialogbtns.setOnClickListener(this);
    }
    ArrayList<String> names=new ArrayList<>();
    ArrayList<String> manager=new ArrayList<>();
    ArrayList<String> decs=new ArrayList<>();
    RecyclerView lineRecycler;
    List<Map<String, Object>> lineData = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerLineAdapter adapter;
    View vv;
    public MyDialog(Context context, int width, int height, View layout, int style,String s) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        vv=layout;
//+++++++++++++++++
        test();
        addlineDate();
        lineRecycler=findViewById(R.id.dialog_line_recy_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        // 设置为线性布局
        lineRecycler.setLayoutManager(layoutManager);
        // 设置适配器
        adapter=new RecyclerLineAdapter(getContext(), lineData);
        lineRecycler.setAdapter(adapter);
        lineRecycler.setOnClickListener(this);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
//                GetRequest(Data.Userid);
                lineData.clear();
                test();
                addlineDate();
                adapter.notifyDataSetChanged();
                //lineRecycler.setAdapter(new RecyclerLineAdapter(getContext(), lineData));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

        //为下拉刷新，设置一组颜色
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.Mainolor));

        //设置触发刷新的距离
        swipeRefreshLayout.setDistanceToTriggerSync(200);

        //设置滑动的距离
        //swipeRefreshLayout.setSlingshotDistance(800);
//
//        swipeRefreshLayout.setRefreshing(true);
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                while (lineData.size()==0);
////                Log
//                Message m1=Message.obtain();
//                handler2.sendMessage(m1);
//            }
//        };
//
////    开始刷新，false 取消刷新
//        swipeRefreshLayout.setRefreshing(true);

//        //判断是否正在刷新
//        swipeRefreshLayout.isRefreshing();
        adapter.setOnItemClickListener(this);

//        lineData.clear();

        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();

                Message message=Message.obtain();
                handler1.sendMessage(message);


                while (Data.Handwarenames.size()==0)try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("TAgG",Data.Handwarenames.toString());
                lineData.clear();
                test();
                addlineDate();
//                adapter.notifyDataSetChanged();
                Log.e("TAgG",lineData.toString());
                Message m1=Message.obtain();
                handler2.sendMessage(m1);
            }
        };
        thread.start();
        //++++++++++++++++++++=

    }
    @Override
    public void onClick(int position) {
//        Toast.makeText(getContext(),"您点击了"+position+"行",Toast.LENGTH_SHORT).show();
        dismiss();
        listenerchoose.OnCenterItemClickchoose(this,vv);
    }
    @Override
    public void onLongClick(int position) {
//        Toast.makeText(getContext(),"您长按点击了"+position+"行",Toast.LENGTH_SHORT).show();
        dismiss();
    }
    int[] pics = {
            R.mipmap.mydoor,
            R.mipmap.myhome,
            R.mipmap.mylab,
            R.mipmap.myclassroom,
            R.mipmap.parking,
            R.mipmap.door2
    };
    void test (){
        names.clear();
        decs.clear();
        for (int i=0;i<Data.Handwarenames.size();i++) {
            names.add(Data.Handwarenames.get(i));
            decs.add("");
        }

    }
    private int picid(int i)
    {
        String id= Data.HandID.get(i);
        if (id.equals("1")) return 0;
        if (id.equals("2")) return 1;
        if (id.equals("3")) return 2;
        if (id.equals("4")) return 3;
        if (id.equals("5")) return 4;
        return 5;

    }
    private void addlineDate(){

        Map<String, Object> map = null;
        for (int i = 0; i < names.size(); i++) {
            map = new HashMap<>();
            map.put("pic", pics[picid(i)]);

            map.put("name", names.get(i));

            map.put("desc", decs.get(i));
            lineData.add(map);
        }
    }
    private OnCenterItemClickListener listener;

    String userid;
    EditText subjectid;
    EditText id;
    EditText startyear;
    EditText startmonth;
    EditText startday;
    EditText endyear;
    EditText endmonth;
    EditText endday;

    String starttime;
    String endtime;


    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()){
            case R.id.dialogbtn:
                userid=id.getText().toString().trim();
                starttime=startyear.getText().toString().trim()+"/"+startmonth.getText().toString().trim()+"/"+startday.getText().toString().trim();
                endtime=endyear.getText().toString().trim()+"/"+endmonth.getText().toString().trim()+"/"+endday.getText().toString().trim();

                listener.OnCenterItemClick(this,v,userid,starttime,endtime);
                break;
            case R.id.dialogbtns:
                sid=subjectid.getText().toString().trim();
                listeners.OnCenterItemClicks(this,v,sid);
                break;
            case R.id.dialog_line_recy_view:
                listenerchoose.OnCenterItemClickchoose(this,v);
            default:
                break;
        }

    }

    public interface OnCenterItemClickListener {
        void OnCenterItemClick(MyDialog dialog, View view,String userID,String start,String end);
    }
    //很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    private OnCenterItemClickListeners listeners;
    String sid;
    public interface OnCenterItemClickListeners {
        void OnCenterItemClicks(MyDialog dialog, View view,String sID);
    }
    //很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListeners(OnCenterItemClickListeners listeners) {
        this.listeners = listeners;
    }

    private OnCenterItemClickListenerchoose listenerchoose;

    public interface OnCenterItemClickListenerchoose {
        void OnCenterItemClickchoose(MyDialog dialog, View view);
    }
    //很明显我们要在这里面写个接口，然后添加一个方法
    public void setOnCenterItemClickListenerchoose(OnCenterItemClickListenerchoose listeners) {
        this.listenerchoose = listeners;
    }
}
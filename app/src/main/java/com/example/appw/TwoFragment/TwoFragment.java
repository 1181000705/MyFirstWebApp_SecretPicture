package com.example.appw.TwoFragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appw.Adapter.RecyclerLineAdapter;
import com.example.appw.Data;
import com.example.appw.MainActivity;
import com.example.appw.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoFragment extends Fragment implements MyDialog.OnCenterItemClickListeners {
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
    int[] pics = {
            R.mipmap.mydoor,
            R.mipmap.myhome,
            R.mipmap.mylab,
            R.mipmap.myclassroom,
            R.mipmap.parking,
            R.mipmap.door2
    };

//    ArrayList<String> names=new ArrayList<>();
    ArrayList<String> manager=new ArrayList<>();
    ArrayList<String> decs=new ArrayList<>();
    RecyclerView lineRecycler;
    List<Map<String, Object>> lineData = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerLineAdapter adapter;
    String User="unLogin";
    String Pwd="non";
    MyDialog sMyDialog;
    Button adds;
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
        for (int i = 0; i < Data.Handwarenames.size(); i++) {
            map = new HashMap<>();
            map.put("pic", pics[picid(i)]);
            map.put("name", Data.Handwarenames.get(i));
            map.put("desc", decs.get(i));
            lineData.add(map);

        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x1:
                    Toast.makeText(getContext(), "gogo", Toast.LENGTH_SHORT).show();
                    break;
                case 0x2:
                    Toast.makeText(getContext(), "成功", Toast.LENGTH_SHORT).show();
                    break;
                case 0x3:
                    Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
                    break;
                case 0x5:
                    Toast.makeText(getContext(), "超时", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getContext(), "不知道什么东西", Toast.LENGTH_SHORT).show();
            }

        }
    };

    void test (){
        Data.Handwarenames.clear();
        decs.clear();
        Data.Handwarenames.add("房间1202");
        decs.add("森家民宿已入住"+"|"+"入住时间：2020.4.11-2020.4.13");
        Data.Handwarenames.add("家");
        decs.add("户主"+"|"+"使用时间：2020.1.1-2099.1.1");
        Data.Handwarenames.add("实验室L101");
        decs.add("成员"+"|"+"项目时间：2020.4.7-2021.1.1");
        Data.Handwarenames.add("刘念家");
        decs.add("客人"+"|"+"开门时间：2020.4.7-2020.4.9");
        Data.Handwarenames.add("停车场");
        decs.add("停车收费"+"|"+"停车时间：2020.4.7.6:30-2020.4.7.10:00");
    }
    @Nullable

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_two, container, false);

        lineRecycler=(RecyclerView)view.findViewById(R.id.line_recy_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        // 设置为线性布局
        lineRecycler.setLayoutManager(layoutManager);
        // 设置适配器
         adapter=new RecyclerLineAdapter(getContext(), lineData);
        lineRecycler.setAdapter(adapter);

        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                GetRequest(Data.Userid);
                lineData.clear();
//                test();
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
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.Mainolor));

        //设置触发刷新的距离
        swipeRefreshLayout.setDistanceToTriggerSync(200);

        //设置滑动的距离
        swipeRefreshLayout.setSlingshotDistance(800);
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
        adapter.setOnItemClickListener(new RecyclerLineAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(),"您点击了"+position+"行",Toast.LENGTH_SHORT).show();
                if (manager.get(position).equals("yes")){
                    Intent intent = new Intent(getContext(), useActivity.class);
                    intent.putExtra("name",lineData.get(position).get("name").toString());
                    intent.putExtra("Position",String.valueOf(position));
                    startActivity(intent);
                }
            }
            @Override
            public void onLongClick(int position) {
                Toast.makeText(getContext(),"您长按点击了"+position+"行",Toast.LENGTH_SHORT).show();
            }
        });

        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();

                Message message=Message.obtain();
                handler1.sendMessage(message);

                GetRequest(Data.Userid);
                while (Data.Handwarenames.size()==0)try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("TAgG",Data.Handwarenames.toString());
                lineData.clear();
                addlineDate();
//                adapter.notifyDataSetChanged();
                Log.e("TAgG",lineData.toString());
                Message m1=Message.obtain();
                handler2.sendMessage(m1);
            }
        };
        thread.start();

        View vview = getLayoutInflater().inflate(R.layout.dialogs, null);
        sMyDialog = new MyDialog(getContext(), 0, 0, vview, R.style.DialogTheme,1);
        sMyDialog.setOnCenterItemClickListeners((MyDialog.OnCenterItemClickListeners) this);

        adds=view.findViewById(R.id.adds);
        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sMyDialog.setCancelable(true);
                sMyDialog.show();
            }
        });

//        test();
//        addlineDate();
//        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                if (Data.Handwarenames.size()==0)
                {
                    Message message=Message.obtain();
                    handler1.sendMessage(message);
                    Data.Handwarenames.clear();
                    GetRequest(Data.Userid);
                    while (Data.Handwarenames.size()==0)try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("TAgG",Data.Handwarenames.toString());
                    lineData.clear();
                    addlineDate();
//                adapter.notifyDataSetChanged();
                    Log.e("TAgG",lineData.toString());
                    Message m1=Message.obtain();
                    handler2.sendMessage(m1);
                }

            }
        };
        thread.start();
    }

    @Override
    public void OnCenterItemClicks(MyDialog dialog, View view, String sID) {
        switch (view.getId()){
            case R.id.dialogbtns:
                Toast.makeText(getContext(),sID,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    //Fragment中的onAttach方法
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        User = ((MainActivity) activity).getUser();
        Pwd=((MainActivity) activity).getPwd();
    }
//通过强转成宿主activity，就可以获取到传递过来的数据
public  void GetRequest(final String UserID) {
    //请求地址
    String url =
            "http://129.28.162.46:8000/mine/get/";
//            "http://192.168.12.52:8084/MyFirstWebApp/record";
           // " http://127.0.0.1:8080/MyFirstWebApp/LoginServlet";    //注①
    String tag = "Get";    //注②

    //取得请求队列
    RequestQueue requestQueue = Volley.newRequestQueue(getContext());

    //防止重复请求，所以先取消tag标识的请求队列
    requestQueue.cancelAll(tag);

    //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
    final StringRequest request = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Data.Handwarenames.clear();
                        manager.clear();
                        decs.clear();
                        Data.HandwareId.clear();
                        Data.HandID.clear();
                        JSONArray jsonArray = (JSONArray) new JSONArray(new String(response.getBytes("ISO-8859-1"),"UTF-8"));  //注③
                        Log.e("handware__yes",response );
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject json=jsonArray.getJSONObject(i);
                            Log.e("handware__yes",json.toString() );
                            Data.HandwareId.add(json.getString("handwareId"));
                            Data.HandID.add(json.getString("handwareId"));
                            Data.Handwarenames.add(json.getString("firmName"));
                            manager.add(json.getString("isManager"));
                            String manager=json.getString("isManager").equals("yes")?"管理":"使用";
                            decs.add(manager+"|"+json.getString("start")+"-"+json.getString("end"));
                        }
                    } catch (JSONException e) {
                        //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                        Message message=Message.obtain();
                        message.what=0x5;
                        handler.sendMessage(message);
                        Log.e("TAG", e.getMessage(), e);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
            Log.e("TAG", error.getMessage(), error);
        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> params = new HashMap<>();
            params.put("UserID", UserID);  //注⑥
//            params.put("Password", password);
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

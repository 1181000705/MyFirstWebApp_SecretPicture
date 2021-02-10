package com.example.appw.TwoFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appw.Data;
import com.example.appw.R;
import com.example.appw.threeFragment.RecyclerLineAdapterRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.example.myfirstwebapp.R;

public class objrecordActivity extends AppCompatActivity {

    List<Map<String, Object>> rcdData = new ArrayList<>();
    RecyclerView lineRecycler;
    RecyclerLineAdapterRecord adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<String> userid=new ArrayList<>();
    ArrayList<String> names=new ArrayList<>();
    ArrayList<String> decs=new ArrayList<>();
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
            Log.e("updata&&&&&&&&&&&",rcdData.toString());
            adapter.notifyDataSetChanged();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objrecord);

//        test();

        lineRecycler=(RecyclerView)findViewById(R.id.rcd_recy_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        // 设置为线性布局
        lineRecycler.setLayoutManager(layoutManager);
        // 设置适配器
        adapter=new RecyclerLineAdapterRecord(this, rcdData);
        lineRecycler.setAdapter(adapter);
        swipeRefreshLayout=findViewById(R.id.rcd_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rcdData.clear();
                ObjRecordRequest(Data.Userid,Data.HandID.get(Integer.valueOf(useActivity.Positon)));
//                test();
                addlineDate();
                adapter.notifyDataSetChanged();
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
        //swipeRefreshLayout.setSlingshotDistance(400);
//        swipeRefreshLayout.setRefreshing(true);

        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();

                Message message=Message.obtain();
                handler1.sendMessage(message);

                ObjRecordRequest(Data.Userid,Data.HandID.get(Integer.valueOf(useActivity.Positon)));
                while (decs.size()==0)try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("#####TAgG",names.toString());
                rcdData.clear();
                addlineDate();
                Log.e("######~~~~~TAgG",rcdData.toString());
                Message m1=Message.obtain();
                handler2.sendMessage(m1);
            }
        };
        thread.start();
    }

    private void addlineDate(){

        Map<String, Object> map = null;
        for (int i = decs.size()-1; i >=0; i--) {
            map = new HashMap<>();
            map.put("pic", Data.userpic[Data.getuserpic(Integer.valueOf(userid.get(i)))]);
            map.put("name", names.get(i));
            map.put("desc", decs.get(i));
            rcdData.add(map);
        }
    }
    void test(){
        Map<String,Object> map=new HashMap<>();
        map.put("pic", R.drawable.pflash);
        map.put("name","光认证成功");
        map.put("desc","刘念家/2020-04-04 22:55");
        rcdData.add(map);
        map=new HashMap<>();
        map.put("pic", R.drawable.pflash);
        map.put("name","光认证成功");
        map.put("desc","刘念家/2020-04-04 23:55");
        rcdData.add(map);

        map=new HashMap<>();
        map.put("pic", R.drawable.pflash);
        map.put("name","光认证成功");
        map.put("desc","刘念家/2020-04-05 00:55");
        rcdData.add(map);
    }

    public  void ObjRecordRequest(final String UserID,final String ObjID) {
        //请求地址
        String url = "http://129.28.162.46:8000/mine/devicerecord/";
//        String url = "http://192.168.12.52:8084/MyFirstWebApp/record";
        // " http://127.0.0.1:8080/MyFirstWebApp/LoginServlet";    //注①
        String tag = "devicerecord";    //注②

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
                            names.clear();
                            decs.clear();
                            userid.clear();
                            JSONArray jsonArray = (JSONArray) new JSONArray(new String(response.getBytes("ISO-8859-1"),"UTF-8"));  //注③
                            Log.e("yesTAG=========",response );
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject json=jsonArray.getJSONObject(i);
                                Log.e("yesTAG=========",json.toString() );
                                userid.add(json.getString("userId"));
                                names.add(json.getString("userName"));
                                decs.add(json.getString("time"));
                            }

                        } catch (JSONException e) {
                            //做自己的请求异常操作，如Toast提示（“无网络连接”等）
//                            Message message=Message.obtain();
//                            message.what=0x5;
//                            handler.sendMessage(message);
                            Toast.makeText(getApplicationContext(),"json出错",Toast.LENGTH_SHORT).show();
                            Log.e("noJSON!!!!", e.getMessage(), e);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                Log.e("noResponse@@@@@@@@@", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserID", UserID);  //注⑥
                params.put("ObjID", ObjID);
//                params.put("Password", password);
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

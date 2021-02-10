package com.example.appw.TwoFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.example.appw.Adapter.RecyclerLineAdapterDetele;
import com.example.appw.Data;
import com.example.appw.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class useActivity extends AppCompatActivity implements MyDialog.OnCenterItemClickListener {
    List<Map<String, Object>> ObjData = new ArrayList<>();
    RecyclerView lineRecycler;
    RecyclerLineAdapterDetele adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String Objname;
    Button AddUser;
    Button Addquery;
    ArrayList<String> userid=new ArrayList<>();

    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;
    MyDialog mMyDialog;
    List<Integer> isIN=new ArrayList<>();
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.e("TAG", msg.obj.toString());
            switch (msg.what){
                case 0x0:
                    AddObjDate((JSONObject) msg.obj);
                    break;
                case 0x1:
                    Toast.makeText(useActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case 0x2:
                    Toast.makeText(useActivity.this, "网络故障", Toast.LENGTH_SHORT).show();
            }

        }
    };
    Handler handler2=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            swipeRefreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();
        }
    };
    void test(){
        Map<String,Object> map=new HashMap<>();
        map.put("pic", R.mipmap.yalingsmall);
        map.put("name","易亚玲");
        map.put("desc","作客时间：2020.4.4-2020.4.6");
        ObjData.add(map);
        map=new HashMap<>();
        map.put("pic", R.mipmap.jiuning);
        map.put("name","张久宁");
        map.put("desc","作客时间：2020.4.1-2020.4.3");
        ObjData.add(map);

        map=new HashMap<>();
        map.put("pic", R.mipmap.liunianbig);
        map.put("name","刘念");
        map.put("desc","作客时间：2020.4.1-2020.4.3");
        ObjData.add(map);
    }
    public static String Positon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obj);
        Intent intent=getIntent();
        //用getXxxExtra()取出对应类型的数据。取出String只需要指定key
        Objname=intent.getStringExtra("name");
        //取出int要指定key，还要设置默认值，当intent中没有该key对应的value时，返回设置的默认值
        Positon=intent.getStringExtra("Position");

        //发送请求
        //用于测试，先注释掉。
        UserReq();
        ObjData.clear();
//        test();

        lineRecycler=(RecyclerView)findViewById(R.id.obj_recy_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        // 设置为线性布局
        lineRecycler.setLayoutManager(layoutManager);
        // 设置适配器
        adapter=new RecyclerLineAdapterDetele(this, ObjData);
        lineRecycler.setAdapter(adapter);

        swipeRefreshLayout=findViewById(R.id.obj_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                ObjData.clear();
                UserRequest(Data.Userid,Data.HandID.get(Integer.valueOf(Positon)));
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
        //swipeRefreshLayout.setSlingshotDistance(400);
        swipeRefreshLayout.setRefreshing(true);
        adapter.setOnItemClickListener(new RecyclerLineAdapterDetele.OnItemClickListener(){
            @Override
            public void onClick(View v, int position) {
                switch (v.getId()){
                    case R.id.btn_recy_item_1_detele:
                        Toast.makeText(getApplicationContext(),"你点击了删除按钮"+(position+1),Toast.LENGTH_SHORT).show();
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                DeleteUserRequest(Data.Userid,userid.get(position),Data.HandID.get(Integer.valueOf(Positon)));
                            }
                        };
                        thread.start();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onLongClick(int position) {

            }
        });

        Thread thread = new Thread(){
            @Override
            public void run() {
                while (ObjData.size()==0);
                Message m1=Message.obtain();
                handler2.sendMessage(m1);
            }
        };
        thread.start();
        View view = getLayoutInflater().inflate(R.layout.dialog, null);
        mMyDialog = new MyDialog(this, 0, 0, view, R.style.DialogTheme);
        mMyDialog.setOnCenterItemClickListener((MyDialog.OnCenterItemClickListener) this);
//        dialogbtn=findViewById(view.);
//        dialogbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"dialog确认",Toast.LENGTH_SHORT).show();
//            }
//        });
        AddUser=findViewById(R.id.btn_adduser);
        AddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMyDialog.setCancelable(true);
                mMyDialog.show();
            }
        });

        Addquery=findViewById(R.id.btn_querocrds);
        Addquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), objrecordActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    public void OnCenterItemClick(MyDialog dialog, View view,String userID,String start,String end) {
        switch (view.getId()){
            case R.id.dialogbtn:
                Toast.makeText(getApplicationContext(),userID+start+end,Toast.LENGTH_SHORT).show();
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        Log.e("dialog ok!", Data.Userid+userID+Data.HandID.get(Integer.valueOf(Positon))+start+end);
                        AddUserRequest(Data.Userid,userID,Data.HandID.get(Integer.valueOf(Positon)),start,end);
                    }
                };
                thread.start();

                break;
            default:
                break;
        }
    }
    private void UserReq()
    {
        Thread thread = new Thread(){
            @Override
            public void run() {
                UserRequest(Data.Userid,Data.HandID.get(Integer.valueOf(Positon)));
            }
        };
        thread.start();
//        ObjRequest("a");
    }
    public  void DeleteUserRequest(final String UserID,final String DeteleUserID,final String ObjID) {
        //请求地址
        String url = "http://129.28.162.46:8000/mine/deleteuser/";
//        String url = "http://192.168.12.52:8084/MyFirstWebApp/record";
        // " http://127.0.0.1:8080/MyFirstWebApp/LoginServlet";    //注①
        String tag = "DeleteUserRecord";    //注②
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);
        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
                Log.e("noResponse in delete", error.getMessage(), error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserID1", UserID);  //注⑥
                params.put("UserID2", DeteleUserID);
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
    public  void AddUserRequest(final String UserID,final String AddUserID,final String ObjID,final String Start
            ,final String End) {
        //请求地址
        String url = "http://129.28.162.46:8000/mine/adduser/";
//        String url = "http://192.168.12.52:8084/MyFirstWebApp/record";
        // " http://127.0.0.1:8080/MyFirstWebApp/LoginServlet";    //注①
        String tag = "AddUserRecord";    //注②
        //取得请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //防止重复请求，所以先取消tag标识的请求队列
        requestQueue.cancelAll(tag);
        //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
        final StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
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
                params.put("UserID1", UserID);  //注⑥
                params.put("UserID2", AddUserID);
                params.put("ObjID", ObjID);
                params.put("Start", Start);
                params.put("End", End);
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

    public void UserRequest (final String UserID,final String HandID) {
        //请求地址
        String url = "http://129.28.162.46:8000/mine/useralready/";    //注①
        String tag = "UserQuery";    //注②
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
                            JSONArray jsonArray = (JSONArray) new JSONArray(new String(response.getBytes("ISO-8859-1"),"UTF-8"));  //注③
                            Log.e("handware__yes",response );
                            Map<String,Object> map=new HashMap<>();
                            userid.clear();
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject json=jsonArray.getJSONObject(i);
                                userid.add(json.getString("userId"));
                                map.put("pic", Data.userpic[Data.getuserpic(Integer.valueOf(userid.get(i)))]);
                                map.put("name",json.getString("userName"));
                                map.put("desc","使用时间:"+json.getString("start")+"-"
                                        +json.getString("end"));
                                ObjData.add(map);
                                map=new HashMap<>();
                                Log.e("handware__yes",json.toString() );
                                }
                            Message m1=Message.obtain();
                            handler2.sendMessage(m1);
                        } catch (JSONException e) {
                            //做自己的请求异常操作，如Toast提示（“无网络连接”等）
                            Message message=Message.obtain();
                            message.what=0x2;
//                            handler.sendMessage(message);
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
                params.put("ObjID", HandID);
                return params;
            }
        };
        //设置Tag标签
        request.setTag(tag);
        //将请求添加到队列中
        requestQueue.add(request);
    }

    private void AddObjDate(JSONObject jsonObject) {

        Map<String,Object> map=new HashMap<>();
        try {
        if (!(jsonObject.getString("User1").equals("null"))){
            map.put("pic", R.drawable.me);
            map.put("name",jsonObject.getString("User1"));
            map.put("desc",jsonObject.getString("Time1"));
            ObjData.add(map);
            map=new HashMap<>();
        }
            if (!(jsonObject.getString("User2").equals("null"))){
                map.put("pic", R.drawable.a5);
                map.put("name",jsonObject.getString("User2"));
                map.put("desc",jsonObject.getString("Time2"));
                ObjData.add(map);
                map=new HashMap<>();
            }
            if (!(jsonObject.getString("User3").equals("null"))){
                map.put("pic", R.drawable.me);
                map.put("name",jsonObject.getString("User3"));
                map.put("desc",jsonObject.getString("Time3"));
                ObjData.add(map);
                map=new HashMap<>();
            }
            if (!(jsonObject.getString("User4").equals("null"))){
                map.put("pic", R.drawable.me);
                map.put("name",jsonObject.getString("User4"));
                map.put("desc",jsonObject.getString("Time4"));
                ObjData.add(map);
                map=new HashMap<>();
            }
            if (!(jsonObject.getString("User5").equals("null"))){
                map.put("pic", R.drawable.me);
                map.put("name",jsonObject.getString("User5"));
                map.put("desc",jsonObject.getString("Time5"));
                ObjData.add(map);
            }
            if (ObjData.size()==0){
                map=new HashMap<>();
                map.put("pic", R.drawable.me);
                map.put("name","ALL");
                map.put("desc","ALL");
                ObjData.add(map);
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }

    }


}


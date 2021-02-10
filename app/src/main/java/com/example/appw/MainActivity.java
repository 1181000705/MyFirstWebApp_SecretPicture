package com.example.appw;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.appw.Adapter.FragmentAdapter;
import com.example.appw.OneFragment.OneFragment;
import com.example.appw.TwoFragment.TwoFragment;
import com.example.appw.threeFragment.ThreeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

/**
     * Created by luo on 2019/7/3.
     */

    public class MainActivity extends AppCompatActivity{
        public static  boolean norlogin=true;
        //String User="unLogin";
        //String Pwd="non";
        BottomNavigationView bnView;
        ViewPager viewPager;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            initOneFragment();//新加的

            bnView = findViewById(R.id.nav_view);//下三标
            viewPager = findViewById(R.id.view_pager);
            List<Fragment> fragments = new ArrayList<>();
            fragments.add(new OneFragment());
            Fragment a = new OneFragment();
            fragments.add(new TwoFragment());
            fragments.add(new ThreeFragment());

            FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            //BottomNavigationView 点击事件监听
             bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int menuId = menuItem.getItemId();
          //  跳转指定页面：Fragment
            switch (menuId) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);break;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);break;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);break;
            }
            return false;
            }
             });

             // ViewPager 滑动事件监听
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }
                @Override
                public void onPageSelected(int i) {
                    //将滑动到的页面对应的 menu 设置为选中状态
                    bnView.getMenu().getItem(i).setChecked(true);
                }
                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });

        }

    /**
     * 初始化内容Fragment
     *
     * @return void
     */
    public void initOneFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        OneFragment mFragment = OneFragment.newInstance();
        transaction.replace(R.id.view_pager, mFragment, mFragment.getClass().getSimpleName());
        transaction.commit();
    }

//        //要获取的值  就是这个参数的值
//        @Override
//        public void SendMessageValue(String user,String pwd) {
//            User=user;
//            Pwd=pwd;
//        }
//        public String getUser(){
//            return User;
//        }
//        public String getPwd(){
//            return Pwd;
//        }
//
//        public void LoginRequest(final String accountNumber, final String password) {
//            //请求地址
//            String url = "http://localhost:8080/MyFirstWebApp/LoginServlet";    //注①
//            String tag = "Login";    //注②
//
//            //取得请求队列
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//            //防止重复请求，所以先取消tag标识的请求队列
//            requestQueue.cancelAll(tag);
//
//            //创建StringRequest，定义字符串请求的请求方式为POST(省略第一个参数会默认为GET方式)
//            final StringRequest request = new StringRequest(Request.Method.POST, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");  //注③
//                                String result = jsonObject.getString("Result");  //注④
//                                if (result.equals("success")) {  //注⑤
//                                    //做自己的登录成功操作，如页面跳转
//                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    //做自己的登录失败操作，如Toast提示
//                                    Toast.makeText(MainActivity.this, "密码错误或者没有用户", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                //做自己的请求异常操作，如Toast提示（“无网络连接”等）
//                                Log.e("TAG", e.getMessage(), e);
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    //做自己的响应错误操作，如Toast提示（“请稍后重试”等）
//                    Toast.makeText(MainActivity.this, "响应超时", Toast.LENGTH_SHORT).show();
//                    Log.e("TAG", error.getMessage(), error);
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("AccountNumber", accountNumber);  //注⑥
//                    params.put("Password", password);
//                    return params;
//                }
//            };
//
//            //设置Tag标签
//            request.setTag(tag);
//
//            //将请求添加到队列中
//            requestQueue.add(request);
//        }


    }

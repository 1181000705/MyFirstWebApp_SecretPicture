package com.example.appw.TwoFragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.ArrayMap;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.appw.Adapter.RecyclerLineAdapterDetele;
import com.example.appw.R;
import com.example.appw.view.ThumbnailsWindowsx;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ShowPictureActivity extends Activity implements ThumbnailsWindowsx.OnSelectedListener{
    Map<String, Bitmap> ObjData = new ArrayMap<>();
    RecyclerView lineRecycler;
    RecyclerLineAdapterDetele adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    private ThumbnailsWindowsx thumbnailsWindowsx;
    private Context mContext;
    private static ArrayList<String> paths = new ArrayList<String>();

    Handler handler2=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            swipeRefreshLayout.setRefreshing(false);//滑动刷新
            adapter.notifyDataSetChanged();//通知适配器更新
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_two);//这里用到ThumWindow
        thumbnailsWindowsx = new ThumbnailsWindowsx(mContext);//定义一个上传的菜单
        FileReq();//先去文件夹拉取图片
        ObjData.clear();

        //这里配置的布局
        lineRecycler=(RecyclerView)findViewById(R.id.obj_recy_view);//单独一个个图片
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        // 设置为线性布局
        lineRecycler.setLayoutManager(layoutManager);
        // 设置适配器
        adapter=new RecyclerLineAdapterDetele(this, ObjData);
        lineRecycler.setAdapter(adapter);

        swipeRefreshLayout=findViewById(R.id.obj_refresh);//用户列表

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ObjData.clear();
                FileRequest();
                //UserRequest(Data.Userid,Data.HandID.get(Integer.valueOf(Positon)));
                adapter.notifyDataSetChanged();//通知更新
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
        swipeRefreshLayout.setSlingshotDistance(400);
        swipeRefreshLayout.setRefreshing(true);

        //点击删除按钮
        adapter.setOnItemClickListener(new RecyclerLineAdapterDetele.OnItemClickListener(){
            @Override
            public void onClick(View v, int position) {
                switch (v.getId()){
                    case R.id.btn_recy_item_1_detele://item的删除按钮
                        Toast.makeText(getApplicationContext(),"你点击了删除按钮"+(position+1),Toast.LENGTH_SHORT).show();
                        Thread thread = new Thread(){
                            @Override
                            public void run() {
                                DeleteFileRequest(position);
                                //  DeleteUserRequest(Data.Userid,userid.get(position),Data.HandID.get(Integer.valueOf(Positon)));
                            }
                        };
                        thread.start();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                        break;
                }

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
    }//oncreat

    private void FileReq()
    {
        Thread thread = new Thread(){
            @Override
            public void run() {
                FileRequest();
                //UserRequest(Data.Userid,Data.HandID.get(Integer.valueOf(Positon)));
            }
        };
        thread.start();
    }

    public void DeleteFileRequest(int fileposition){
        File file = new File(paths.get(fileposition));
        file.delete();
    }

    //获取文件路径的列表
    private static ArrayList<String> imagePath(File file) {
        ArrayList<String> list = new ArrayList<String>();

        File[] files = file.listFiles();
        for (File f : files) {
            list.add(f.getAbsolutePath());//list里面是文件路径
        }
        Collections.sort(list);
        return list;
    }

    //扫描文件里的图片显示出来
    private Map<String,Bitmap> buildThum() throws FileNotFoundException {
        File baseFile = new File("/sdcard/cropimage/");//存放图片的文件位置
        Map<String,Bitmap> maps = new TreeMap<String, Bitmap>();
        if (baseFile != null && baseFile.exists()) {
            paths = imagePath(baseFile);

            if (!paths.isEmpty()) {
                for (int i = 0; i < paths.size(); i++) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    Bitmap bitmap =BitmapFactory.decodeFile(paths.get(i),options);
                    options.inJustDecodeBounds = false;
                    int be = options.outHeight;
//					 if (be <= 0) {
//						 be = 10;
//					 }
                    options.inSampleSize = be;
                    bitmap = BitmapFactory.decodeFile(paths.get(i),options);
                    maps.put(paths.get(i), bitmap);
                }
            }
        }
        return maps;
    }


    public void FileRequest(){
        Map<String,Bitmap> maps = new TreeMap<>();
        try{
            maps = buildThum();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        Iterator<String> it = maps.keySet().iterator();
        int i = 0;
        while (it.hasNext()){
            String path = (String) it.next();
            Bitmap bm = maps.get(path);

            ImageView image = new ImageView(mContext);
            image.setImageBitmap(bm);//显示图片
            image.setId(i++);
            lineRecycler.addView(image);//这里注意下
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thumbnailsWindowsx.showPopupMenu(image);//image就是按钮
                }
            });
        }
        //     lineRecycler.addView()//原本的目的应该是显示整个布局，这里不需要了
    }


    @Override
    public void OnSelected(MenuItem v, int position) {
        switch (position) {
            case 0:
                //上传按钮被点击
                upload();
                break;
            default:
                break;
        }
    }

    //上传
    private void upload(){

    }
}

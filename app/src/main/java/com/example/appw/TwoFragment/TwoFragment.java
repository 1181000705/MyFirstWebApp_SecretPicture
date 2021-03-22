package com.example.appw.TwoFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.appw.Adapter.RecyclerLineAdapterDetele;
import com.example.appw.R;
import com.example.appw.view.ThumbnailsWindowsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TwoFragment extends Fragment implements ThumbnailsWindowsx.OnSelectedListener {
    Map<String, Bitmap> ObjData = new TreeMap<>();
    RecyclerView lineRecycler;
    RecyclerLineAdapterDetele adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    private ThumbnailsWindowsx thumbnailsWindowsx;
   // private Context mContext;
    private String piccode;
    private String sessionid = "fake_sessionid";
    private static ArrayList<String> paths = new ArrayList<String>();

    Handler handler2=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            swipeRefreshLayout.setRefreshing(false);//首先进来不刷新
            adapter.notifyDataSetChanged();//通知适配器更新
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_two,container,false);
        thumbnailsWindowsx = new ThumbnailsWindowsx(getContext());//定义一个上传的菜单
        FileRequest();//先去文件夹拉取图片
//        ObjData.clear();

        //++++++++这里接收二维码扫后的跳转
        if (getArguments()!=null) {
            //获取上一个碎片传递来的数据
            sessionid = getArguments().getString("sessionid");
            System.out.println(sessionid+"略略略");
        }


        //这里配置的布局
        lineRecycler=(RecyclerView)view.findViewById(R.id.obj_recy_view);//单独一个个图片
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        // 设置为线性布局
        lineRecycler.setLayoutManager(layoutManager);
        // 设置适配器
        adapter=new RecyclerLineAdapterDetele(getContext(), ObjData);//这里面要有文件
        lineRecycler.setAdapter(adapter);

        swipeRefreshLayout=view.findViewById(R.id.obj_refresh);//用户列表

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ObjData.clear();
                FileRequest();
                adapter.notifyDataSetChanged();//通知更新刷新后到达这里

                new Handler().postDelayed(new Runnable() {//handler创建多线程
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);//不会自动下拉刷新
                        }
                    }
                }, 1000);//1s后调用Runnable对象刷新1s
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
            public void onClick(View v, int position) throws IOException {
                switch (v.getId()){
                    case R.id.btn_recy_item_1_detele://item的删除按钮
                        Toast.makeText(getContext(),"你点击了删除按钮"+(position+1),Toast.LENGTH_SHORT).show();
                        DeleteFileRequest(position);
//                        Thread thread = new Thread(){
//                            @Override
//                            public void run() {
//                                DeleteFileRequest(position);
//                            }
//                        };
//                        thread.start();
                        break;
                    default:
                        Toast.makeText(getContext(),"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
                        thumbnailsWindowsx.showPopupMenu(v);//image就是按钮弹出注册登录菜单
                        piccode = PicCodeGenerator(paths.get(position));//这里生成密象码
                        break;
                }

            }
        });

//        while (ObjData.size()==0)try {
//            sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Message m1=Message.obtain();
        handler2.sendMessage(m1);
//        Thread thread = new Thread(){
//            @Override
//            public void run() {
//                while (ObjData.size()==0)try {
//                    sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Message m1=Message.obtain();
//                handler2.sendMessage(m1);
//            }
//        };
//        thread.start();
        return view;
    }//oncreat

    //生成密象码
    public String PicCodeGenerator(String picturePath) throws IOException{
        File file = new File(picturePath);
        int length = (int)file.length();
        MessageDigest md = null;
        byte[] bt = picturePath.getBytes();

        byte[] fileStream = new byte[length];
        String code;
        FileInputStream in = new FileInputStream(file);
        in.read(fileStream);
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(bt);
            code = bytes2Hex(md.digest());//转化为十六进制
        }catch (NoSuchAlgorithmException e){
            System.out.println("啦啦啦啦啦");
            return null;
        }
        return code;
    }

    private String bytes2Hex(byte[] bts){
        String des = "";
        String tmp = null;
        for (int i = 0;i < bts.length;i++){
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length()==1){
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
    //删除文件
    public void DeleteFileRequest(int fileposition){
        File file = new File(paths.get(fileposition));
        file.delete();
    }

    //获取文件路径的列表
    private static ArrayList<String> imagePath(File file) {
        ArrayList<String> list = new ArrayList<String>();

        File[] files = file.listFiles();//files==null file不是null

        if (files!=null) {
            for (File f : files) {
                //System.out.println("files不为空,files的大小为"+files.length);
                list.add(f.getAbsolutePath());//list里面是文件路径
            }
        }else {
            System.out.println("files为空");
        }

        Collections.sort(list);
        return list;
    }

    //扫描文件里的图片显示出来
    private Map<String,Bitmap> buildThum() throws FileNotFoundException {
        File sdCard = Environment.getExternalStorageDirectory();
        String folder =  "/sdcard/cropimage/";
        File baseFile = new File(folder);//存放图片的文件位置
        //baseFile 存在且不为空
        Map<String,Bitmap> maps = new TreeMap<String, Bitmap>();
        if (baseFile != null && baseFile.exists()) {
            paths = imagePath(baseFile);//进入了 path应该是个list
            if (!paths.isEmpty()) {
                //System.out.println("paths的长度为"+paths.size());
                for (int i = 0; i < paths.size(); i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
                    //System.out.println("bitmap的值为"+bitmap);
                    maps.put(paths.get(i), bitmap);
                }
            }
        }else {
            System.out.println("文件找不到啦啦啦");
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
        //拷贝
        Set<String> kks=maps.keySet();
        Iterator<String> iterator = kks.iterator();
        while (iterator.hasNext()){
            String ttemp = iterator.next();
            this.ObjData.put(ttemp,maps.get(ttemp));
        }
    }


    @Override
    public void OnSelected(MenuItem v, int position) {
        switch (position) {
            case 0:
                //注册按钮被点击
                Intent intent = new Intent(getContext(),RegisterActivity.class);
                intent.putExtra("piccode",piccode);
                intent.putExtra("sessionid",sessionid);
                startActivity(intent);
                //register();
                break;
            case 1:
                //登录按钮被点击
                Intent intent1 = new Intent(getContext(), LoginActivity.class);
                intent1.putExtra("piccode",piccode);
                intent1.putExtra("sesssion",sessionid);
                startActivity(intent1);
                //login();
                break;
            default:
                break;
        }
    }

}

package com.example.appw.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appw.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class RecyclerLineAdapterDetele extends RecyclerView.Adapter<RecyclerLineAdapterDetele.ViewHolder> implements View.OnClickListener{

    Context context;
    Map<String, Bitmap> data;
    OnItemClickListener mOnItemClickListener;

    public RecyclerLineAdapterDetele(Context context, Map<String, Bitmap> data) {

        this.data = data;
        this.context = context;

    }
    public interface OnItemClickListener{
        void onClick(View v, int position) throws IOException;//这里给出的position
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detele, viewGroup, false);
        return new ViewHolder(view);//只加载一次view，其他时间都是复用
    }


    @Override
    public void onClick(View v) {
//        int position = (int) v.getTag();      //getTag()获取数据
//        if (mOnItemClickListener != null) {
//            switch (v.getId()){
//                case R.id.original:
//                    mOnItemClickListener.onClick(v, position);
//                    break;
//                default:
//                    mOnItemClickListener.onClick(v, position);
//                    break;
//            }
//        }
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        List<String> list = new ArrayList<>();//里面是图片路径
        Set<String> ks=data.keySet();
        Iterator<String> iterator = ks.iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next();
            list.add(temp);
        }
//        Iterator<String> it = data.keySet().iterator();//得到的是路径
//        int ii = 0;
//        while (it.hasNext()){
//            String path = (String) it.next();
//            Bitmap bm = data.get(path);
//
//            ImageView image = new ImageView(context);
//            image.setImageBitmap(bm);//显示图片
//          //  image.setId(ii++);
//            lineRecycler.addView(image);//这里注意下
//            image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    thumbnailsWindowsx.showPopupMenu(image);//image就是按钮
//                }
//            });
//        }
        //System.out.println("这里是解析到了"+list.size()+"个文件");
        holder.img.setImageBitmap(data.get(list.get(i)));
//        Glide.with(context).load(data.get(list.get(i))).into(holder.img);//加载图片
//        holder.name.setText(data.get(i).get("name").toString());
//        holder.desc.setText(data.get(i).get("desc").toString());
        holder.img.setTag(i);//他有标号0 1 2
        holder.delete.setTag(i);
//        holder.name.setTag(i);
//        holder.desc.setTag(i);

        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mOnItemClickListener.onClick(v,i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mOnItemClickListener.onClick(v,i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override

    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
//        TextView name;
//        TextView desc;
        Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_recy_item_1_pic);
//            name = itemView.findViewById(R.id.tv_recy_item_1_name);
//            desc = itemView.findViewById(R.id.tv_recy_item_1_desc);
            delete=itemView.findViewById(R.id.btn_recy_item_1_detele);

            delete.setOnClickListener( RecyclerLineAdapterDetele.this);
        }
    }


}

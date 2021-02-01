package com.example.appw.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appw.R;

import java.util.List;
import java.util.Map;


public class RecyclerLineAdapterDetele extends RecyclerView.Adapter<RecyclerLineAdapterDetele.ViewHolder> implements View.OnClickListener{

    Context context;
    List<Map<String, Object>> data;
    OnItemClickListener mOnItemClickListener;

    public RecyclerLineAdapterDetele(Context context, List<Map<String, Object>> data) {

        this.data = data;
        this.context = context;

    }
    public interface OnItemClickListener{
        void onClick(View v, int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detele, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.obj_recy_view:
                    mOnItemClickListener.onClick(v, position);
                    break;
                default:
                    mOnItemClickListener.onClick(v, position);
                    break;
            }
        }
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {

        Glide.with(context).load(data.get(i).get("pic")).into(holder.img);
        holder.name.setText(data.get(i).get("name").toString());
        holder.desc.setText(data.get(i).get("desc").toString());

        holder.itemView.setTag(i);
        holder.delete.setTag(i);
        holder.name.setTag(i);
        holder.desc.setTag(i);

        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v,i);
                }
            });
            holder.delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v,i);

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
        TextView name;
        TextView desc;
        Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_recy_item_1_pic);
            name = itemView.findViewById(R.id.tv_recy_item_1_name);
            desc = itemView.findViewById(R.id.tv_recy_item_1_desc);
            delete=itemView.findViewById(R.id.btn_recy_item_1_detele);

            delete.setOnClickListener( RecyclerLineAdapterDetele.this);
            itemView.setOnClickListener(RecyclerLineAdapterDetele.this);
        }
    }


}

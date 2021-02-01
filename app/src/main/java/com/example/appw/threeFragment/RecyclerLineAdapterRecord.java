package com.example.appw.threeFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appw.R;

import java.util.List;
import java.util.Map;


public class RecyclerLineAdapterRecord extends RecyclerView.Adapter<RecyclerLineAdapterRecord.ViewHolder> {

    Context context;
    List<Map<String, Object>> data;
    OnItemClickListener mOnItemClickListener;

    public RecyclerLineAdapterRecord(Context context, List<Map<String, Object>> data) {

        this.data = data;
        this.context = context;

    }
    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {

        Glide.with(context).load(data.get(i).get("pic")).into(holder.img);
        holder.name.setText(data.get(i).get("name").toString());
        holder.desc.setText(data.get(i).get("desc").toString());


        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(i);
                }
            });
            holder. itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(i);
                    return true;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_recy_item_1_pic);
            name = itemView.findViewById(R.id.tv_recy_item_1_name);
            desc = itemView.findViewById(R.id.tv_recy_item_1_desc);
        }
    }


}

package com.example.appw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.appw.R;

public class ThumbnailsWindowsx extends LinearLayout {
    private static final String TAG = "ThumbnailsWindowsx";
    private Context mContext;
    private OnSelectedListener mOnSelectedListener;

    public ThumbnailsWindowsx(Context context) {
        super(context);
        mContext = context;
        //setupViews();
    }

    public ThumbnailsWindowsx(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //setupViews();
    }
    public void setupViews(){

    }
    //弹出按钮框
    public void showPopupMenu(final View view){
        final PopupMenu popupMenu = new PopupMenu(mContext,view);//什么地方需要弹出菜单
        //menu 布局
        popupMenu.getMenuInflater().inflate(R.menu.main,popupMenu.getMenu());//这里用到了弹出式菜单
        //点击事件

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.register:
                        if (null != mOnSelectedListener) {
                            mOnSelectedListener.OnSelected(item, 0);
                        }
                        break;
                    case R.id.login:
                        if (null != mOnSelectedListener) {
                            mOnSelectedListener.OnSelected(item, 1);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        //关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu){
                Toast.makeText(view.getContext(),"close",Toast.LENGTH_SHORT).show();
            }
        });
        //显示菜单
        popupMenu.show();
    }

    /**
     * 设置选择监听
     *
     * @param l
     */
    public void setOnSelectedListener(OnSelectedListener l) {
        this.mOnSelectedListener = l;
    }

    /**
     * 选择监听接口
     */
    public interface OnSelectedListener {
        void OnSelected(MenuItem v, int position);
    }

}

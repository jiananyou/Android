package com.hziee.exam.pwdmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//创建Adapter
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.VH> {
    private ArrayList<Password> mData;          //存放密码对象数据
    private Context mContext;

    //有参构造
    RecycleViewAdapter(ArrayList<Password> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    //创建ViewHolder
    static class VH extends RecyclerView.ViewHolder {
        TextView title;
        TextView pwd;
        VH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_title);
            pwd = itemView.findViewById(R.id.tv_item_pwd);
        }
    }

    //为每个Item inflater分配一个View
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.recycleview_item,viewGroup,false);
        return new VH(v);
    }

    //适配渲染数据到View中
    @Override
    public void onBindViewHolder(@NonNull VH viewHolder, int i) {
        viewHolder.title.setText("标题：" + mData.get(i).getTitle());
        viewHolder.pwd.setText("密码：" + mData.get(i).getPwd());
    }

    //获取项目个数
    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }
}

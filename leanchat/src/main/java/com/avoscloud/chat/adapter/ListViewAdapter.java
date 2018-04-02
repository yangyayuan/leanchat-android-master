package com.avoscloud.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avoscloud.chat.R;
import com.avoscloud.chat.activity.WebSiteActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 第二页RecyclerView的适配器，可以写多个不同名称的adapter来适配tab监听事件
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.MyViewHolder> {

    List<String> list=new ArrayList<String>();

    private Context context;
    public ListViewAdapter(Context context){
        for(int i=0;i<100;i++){
            list.add("列表:"+i);
        }
        this.context=context;
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_list_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.myPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,WebSiteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(intent);
            }
        });


    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView myPhoto;

        public MyViewHolder(View view) {
            super(view);
            myPhoto = (ImageView) view.findViewById(R.id.myphoto);
        }
    }
}

















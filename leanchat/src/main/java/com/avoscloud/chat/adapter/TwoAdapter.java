package com.avoscloud.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avoscloud.chat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的适配器
 */
public class TwoAdapter extends RecyclerView.Adapter<TwoAdapter.MyViewHolder> {

    List<String> list = new ArrayList<String>();

    private Context context;

    public TwoAdapter(Context context) {
        for (int i = 0; i < 100; i++) {
            list.add("列表:" + i);
        }
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_list_two, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
           //do something
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.who);
        }
    }
}

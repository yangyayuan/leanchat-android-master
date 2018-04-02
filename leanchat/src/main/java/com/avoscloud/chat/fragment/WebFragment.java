package com.avoscloud.chat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avoscloud.chat.R;
import com.avoscloud.chat.activity.SendMsgWeb;
import com.avoscloud.chat.activity.WebSiteActivity;
import com.avoscloud.chat.adapter.ListViewAdapter;
import com.avoscloud.chat.adapter.OneAdapter;
import com.avoscloud.chat.adapter.TwoAdapter;

import butterknife.ButterKnife;

/**
 * Created by 10714 on 2018/4/1.
 */

public class WebFragment extends BaseFragment {


    private ListViewAdapter listAdapter;
    private OneAdapter oneAdapter;
    private TwoAdapter twoAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.web_fragment, container, false);
        ButterKnife.bind(this, view);
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerView);
        listAdapter = new ListViewAdapter(getActivity().getApplicationContext());
        oneAdapter = new OneAdapter(getActivity().getApplicationContext());
        twoAdapter = new TwoAdapter(getActivity().getApplicationContext());

        FloatingActionButton myFab = (FloatingActionButton)view.findViewById(R.id.myfab);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("项目一"));
        tabLayout.addTab(tabLayout.newTab().setText("项目二"));
        tabLayout.addTab(tabLayout.newTab().setText("项目三"));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {//ListView效果
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(listAdapter);
                }
                if (tab.getPosition() == 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(oneAdapter);
                }
                if (tab.getPosition() == 2) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(twoAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(listAdapter);

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(),SendMsgWeb.class);
                startActivity(intent);
            }
        });


        return view;
        //return view;
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        headerLayout.showTitle("网页");
    }

}


























package com.avoscloud.chat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avoscloud.chat.R;

import butterknife.ButterKnife;

/**
 * Created by 10714 on 2018/4/1.
 */

public class WebFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.web_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}

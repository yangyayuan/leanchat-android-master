package com.avoscloud.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.avoscloud.chat.R;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

public class WebSiteActivity extends AppCompatActivity {

    private MapView mMapview = null;
    private BaiduMap mBaiduMap;
    private ImageButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_site);
        mMapview = (MapView)findViewById(R.id.mymapview);
        mButton = (ImageButton) findViewById(R.id.mybutton2);
        mBaiduMap = mMapview.getMap();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WebSiteActivity.this,EnjoyActivity.class);
                startActivity(intent);
            }
        });
    }
}

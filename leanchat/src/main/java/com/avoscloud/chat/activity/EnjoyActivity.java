package com.avoscloud.chat.activity;

import android.content.Intent;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avoscloud.chat.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EnjoyActivity extends AppCompatActivity {
    private ImageView mImageViewSelect;
    private byte[] mImageBytes = null;
    private Handler mHandler = new Handler();
    private ProgressBar mProgerss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_mes_web);

        mImageViewSelect = (ImageView) findViewById(R.id.imageview_select_publish);
        mProgerss = (ProgressBar) findViewById(R.id.mProgess);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(getString(R.string.publish));

        Button mButtonSelect = (Button) findViewById(R.id.button_select_publish);
        mButtonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 42);
            }
        });

        final EditText mDiscriptionEdit = (EditText) findViewById(R.id.edittext_discription_publish);
        final EditText mTitleEdit = (EditText) findViewById(R.id.edittext_title_publish);
        final EditText mPriceEdit = (EditText) findViewById(R.id.edittext_price_publish);

        findViewById(R.id.button_submit_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(mTitleEdit.getText().toString())) {
                    Toast.makeText(EnjoyActivity.this, "请输入标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(mDiscriptionEdit.getText().toString())) {
                    Toast.makeText(EnjoyActivity.this, "请输入商品描述", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(mPriceEdit.getText().toString())) {
                    Toast.makeText(EnjoyActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mImageBytes == null) {
                    Toast.makeText(EnjoyActivity.this, "请选择一张照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgerss.setVisibility(View.VISIBLE);
                AVObject entry = new AVObject("Entry");
                entry.put("title", mTitleEdit.getText().toString());
                entry.put("description", mDiscriptionEdit.getText().toString());
                entry.put("price", Integer.parseInt(mPriceEdit.getText().toString()));
                entry.put("owner", AVUser.getCurrentUser());
                entry.put("image", new AVFile("productPic", mImageBytes));
                entry.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            mProgerss.setVisibility(View.GONE);
                            EnjoyActivity.this.finish();
                        } else {
                            mProgerss.setVisibility(View.GONE);
                            Toast.makeText(EnjoyActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        }, mImageUploadProgressCallback);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 42 && resultCode == RESULT_OK) {
            try {
                mImageViewSelect.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
                mImageBytes = getBytes(getContentResolver().openInputStream(data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }
}

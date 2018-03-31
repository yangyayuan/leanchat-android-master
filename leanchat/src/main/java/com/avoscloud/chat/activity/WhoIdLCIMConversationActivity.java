package com.avoscloud.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.Arrays;

import cn.leancloud.chatkit.activity.LCIMConversationActivity;

/**
 * Created by 10714 on 2018/3/31.
 */

public class WhoIdLCIMConversationActivity extends LCIMConversationActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Intent intent = getIntent();
        final String whoId = intent.getStringExtra("ID");
        //sendMessageToJerryFromTom(whoId);

        // Tom 用自己的名字作为clientId，获取AVIMClient对象实例

        AVIMClient tom = AVIMClient.getInstance("TOM");
        // 与服务器连接
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建与Jerry之间的对话
                    client.createConversation(Arrays.asList(whoId), "Tom & Jerry", null,
                            new AVIMConversationCreatedCallback() {

                                @Override
                                public void done(AVIMConversation conversation, AVIMException e) {
                                    if (e == null) {
                                        AVIMTextMessage msg = new AVIMTextMessage();
                                        msg.setText("耗子，起床！");
                                        // 发送消息
                                        conversation.sendMessage(msg, new AVIMConversationCallback() {

                                            @Override
                                            public void done(AVIMException e) {
                                                if (e == null) {
                                                    Log.d("Tom & Jerry", "发送成功！");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });


    }

//    public void sendMessageToJerryFromTom(final String whoId) {
//
//    }

}

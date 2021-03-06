package com.avoscloud.chat.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.SaveCallback;
import com.avoscloud.chat.App;
import com.avoscloud.chat.R;
import com.avoscloud.chat.fragment.ConversationListFragment;
import com.avoscloud.chat.fragment.DiscoverFragment;
import com.avoscloud.chat.fragment.ProfileFragment;
import com.avoscloud.chat.fragment.WebFragment;
import com.avoscloud.chat.friends.ContactFragment;
import com.avoscloud.chat.model.LeanchatUser;
import com.avoscloud.chat.redpacket.RedPacketUtils;
import com.avoscloud.chat.service.PreferenceMap;
import com.avoscloud.chat.service.UpdateService;
import com.avoscloud.chat.util.LogUtils;
import com.avoscloud.chat.util.UserCacheUtils;
import com.avoscloud.chat.util.Utils;


/**
 * Created by lzw on 14-9-17.
 */
public class MainActivity extends AVBaseActivity {
  public static final int FRAGMENT_N = 5;
  public static final int[] tabsNormalBackIds = new int[]{R.drawable.tabbar_chat,R.drawable.tabbar_chat,
      R.drawable.tabbar_contacts, R.drawable.tabbar_discover, R.drawable.tabbar_me};
  public static final int[] tabsActiveBackIds = new int[]{R.drawable.tabbar_chat_active,R.drawable.tabbar_chat_active,
      R.drawable.tabbar_contacts_active, R.drawable.tabbar_discover_active,
      R.drawable.tabbar_me_active};
  private static final String FRAGMENT_TAG_CONVERSATION = "conversation";
  private static final String FRAGMENT_TAG_web = "web";
  private static final String FRAGMENT_TAG_CONTACT = "contact";
  private static final String FRAGMENT_TAG_DISCOVER = "discover";
  private static final String FRAGMENT_TAG_PROFILE = "profile";
  private static final String[] fragmentTags = new String[]{FRAGMENT_TAG_CONVERSATION, FRAGMENT_TAG_web,FRAGMENT_TAG_CONTACT,
      FRAGMENT_TAG_DISCOVER, FRAGMENT_TAG_PROFILE};

//  public LocationClient locClient;
 // public MyLocationListener locationListener;
  Button conversationBtn,webBtn, contactBtn, discoverBtn, mySpaceBtn;
  View fragmentContainer;
  ContactFragment contactFragment;
  WebFragment webFragment;
  DiscoverFragment discoverFragment;
  ConversationListFragment conversationListFragment;
  ProfileFragment profileFragment;
  Button[] tabs;
  View recentTips, contactTips;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    findView();
    init();

    discoverBtn.performClick();
//    initBaiduLocClient();
//    updateUserLocation();
    UserCacheUtils.cacheUser(LeanchatUser.getCurrentUser());
  }

  @Override
  protected void onResume() {
    super.onResume();
    UpdateService updateService = UpdateService.getInstance(this);
    updateService.checkUpdate();
  }

  private void init() {
    tabs = new Button[]{conversationBtn, webBtn,contactBtn, discoverBtn, mySpaceBtn};
  }

  private void findView() {
    conversationBtn = (Button) findViewById(R.id.btn_message);
    webBtn = (Button) findViewById(R.id.btn_web);
    contactBtn = (Button) findViewById(R.id.btn_contact);
    discoverBtn = (Button) findViewById(R.id.btn_discover);
    mySpaceBtn = (Button) findViewById(R.id.btn_my_space);
    fragmentContainer = findViewById(R.id.fragment_container);
    recentTips = findViewById(R.id.iv_recent_tips);
    contactTips = findViewById(R.id.iv_contact_tips);
  }

  public void onTabSelect(View v) {
    int id = v.getId();
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    hideFragments(manager, transaction);
    setNormalBackgrounds();
    if (id == R.id.btn_message) {
      if (conversationListFragment == null) {
        conversationListFragment = new ConversationListFragment();
        transaction.add(R.id.fragment_container, conversationListFragment, FRAGMENT_TAG_CONVERSATION);
      }
      transaction.show(conversationListFragment);
    } else if (id == R.id.btn_web) {
      if (webFragment == null) {
        webFragment = new WebFragment();
        transaction.add(R.id.fragment_container, webFragment, FRAGMENT_TAG_web);
      }
      transaction.show(webFragment);
    } else if (id == R.id.btn_contact) {
      if (contactFragment == null) {
        contactFragment = new ContactFragment();
        transaction.add(R.id.fragment_container, contactFragment, FRAGMENT_TAG_CONTACT);
      }
      transaction.show(contactFragment);
    } else if (id == R.id.btn_discover) {
      if (discoverFragment == null) {
        discoverFragment = new DiscoverFragment();
        transaction.add(R.id.fragment_container, discoverFragment, FRAGMENT_TAG_DISCOVER);
      }
      transaction.show(discoverFragment);
    } else if (id == R.id.btn_my_space) {
      if (profileFragment == null) {
        profileFragment = new ProfileFragment();
        transaction.add(R.id.fragment_container, profileFragment, FRAGMENT_TAG_PROFILE);
      }
      transaction.show(profileFragment);
    }
    int pos;
    for (pos = 0; pos < FRAGMENT_N; pos++) {
      if (tabs[pos] == v) {
        break;
      }
    }
    transaction.commit();
    setTopDrawable(tabs[pos], tabsActiveBackIds[pos]);
  }

  private void setNormalBackgrounds() {
    for (int i = 0; i < tabs.length; i++) {
      Button v = tabs[i];
      setTopDrawable(v, tabsNormalBackIds[i]);
    }
  }

  private void setTopDrawable(Button v, int resId) {
    v.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(resId), null, null);
  }

  private void hideFragments(FragmentManager fragmentManager, FragmentTransaction transaction) {
    for (int i = 0; i < fragmentTags.length; i++) {
      Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags[i]);
      if (fragment != null && fragment.isVisible()) {
        transaction.hide(fragment);
      }
    }
  }

//  public static void updateUserLocation() {
//    PreferenceMap preferenceMap = PreferenceMap.getCurUserPrefDao(App.ctx);
//    AVGeoPoint lastLocation = preferenceMap.getLocation();
//    if (lastLocation != null) {
//      final LeanchatUser user = LeanchatUser.getCurrentUser();
//      final AVGeoPoint location = user.getAVGeoPoint(LeanchatUser.LOCATION);
//      if (location == null || !Utils.doubleEqual(location.getLatitude(), lastLocation.getLatitude())
//        || !Utils.doubleEqual(location.getLongitude(), lastLocation.getLongitude())) {
//        user.put(LeanchatUser.LOCATION, lastLocation);
//        user.saveInBackground(new SaveCallback() {
//          @Override
//          public void done(AVException e) {
//            if (e != null) {
//              LogUtils.logException(e);
//            } else {
//              AVGeoPoint avGeoPoint = user.getAVGeoPoint(LeanchatUser.LOCATION);
//              if (avGeoPoint == null) {
//                LogUtils.e("avGeopoint is null");
//              } else {
//                LogUtils.v("save location succeed latitude " + avGeoPoint.getLatitude()
//                  + " longitude " + avGeoPoint.getLongitude());
//              }
//            }
//          }
//        });
//      }
//    }
//  }

//  public class MyLocationListener implements BDLocationListener {
//
//    @Override
//    public void onReceiveLocation(BDLocation location) {
//      double latitude = location.getLatitude();
//      double longitude = location.getLongitude();
//      int locType = location.getLocType();
//      LogUtils.d("onReceiveLocation latitude=" + latitude + " longitude=" + longitude
//          + " locType=" + locType + " address=" + location.getAddrStr());
//      String currentUserId = LeanchatUser.getCurrentUserId();
//      if (!TextUtils.isEmpty(currentUserId)) {
//        PreferenceMap preferenceMap = new PreferenceMap(MainActivity.this, currentUserId);
//        AVGeoPoint avGeoPoint = preferenceMap.getLocation();
//        if (avGeoPoint != null && avGeoPoint.getLatitude() == location.getLatitude()
//            && avGeoPoint.getLongitude() == location.getLongitude()) {
//          updateUserLocation();
//          locClient.stop();
//        } else {
//          AVGeoPoint newGeoPoint = new AVGeoPoint(location.getLatitude(),
//              location.getLongitude());
//          if (newGeoPoint != null) {
//            preferenceMap.setLocation(newGeoPoint);
//          }
//        }
//      }
//    }
//  }
}

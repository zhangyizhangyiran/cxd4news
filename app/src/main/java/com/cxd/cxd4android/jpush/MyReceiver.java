package com.cxd.cxd4android.jpush;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.cxd.cxd4android.activity.MainActivity;
import com.cxd.cxd4android.activity.SplashActivity;
import com.cxd.cxd4android.activity.subactivity.BoutBannerActivity;
import com.cxd.cxd4android.activity.subactivity.SeNewNoticeActivity;
import com.cxd.cxd4android.model.BannerModel;
import com.cxd.cxd4android.model.JPushBaseModel;
import com.cxd.cxd4android.model.NoticeModel;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            String extra =bundle.getString(JPushInterface.EXTRA_EXTRA);
			Logger.i(extra);

			Intent i;
			Gson gson = new Gson();
			JPushBaseModel model = gson.fromJson(extra, JPushBaseModel.class);

			//判断app进程是否存活
			if(SystemUtils.isAppAlive(context, "com.cxd.cxd4android")){
				if (("banner").equals(model.page)) {
					BannerModel bannerModel = new BannerModel();
					bannerModel.location = "";
					bannerModel.title = model.title;
					bannerModel.imgRootUrl = "";
					bannerModel.url = model.action;
					Intent mainIntent = new Intent(context, MainActivity.class);
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);

					i = new Intent(context, BoutBannerActivity.class);
					i.putExtra("BannerModel", (Serializable) bannerModel);
					Logger.i("banner页" + model.page + model.action);
					Intent[] intents = {mainIntent, i};
					context.startActivities(intents);
				} else if (("notice").equals(model.page)) {
					NoticeModel noticeModel=new NoticeModel();
					noticeModel.page=model.page;
					noticeModel.title=model.title;
					noticeModel.action = model.action;

					Intent mainIntent = new Intent(context, MainActivity.class);
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Logger.i("公告页");
					i = new Intent(context, SeNewNoticeActivity.class);
					i.putExtra("noticeModel",(Serializable)noticeModel);
					Intent[] intents = {mainIntent, i};
					context.startActivities(intents);
				} else {

					Intent mainIntent = new Intent(context, MainActivity.class);
					mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(mainIntent);

//					i = new Intent(context, TestActivity.class);
//					//打开自定义的Activity
//					i.putExtras(bundle);
//					//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					context.startActivity(i);
//
//					L.I("公告页");
//					Intent[] intents = {mainIntent, i};
//					context.startActivities(intents);
				}
			}else {

				if (("banner").equals(model.page)) {
					BannerModel bannerModel = new BannerModel();
					bannerModel.location = "";
					bannerModel.title = "";
					bannerModel.imgRootUrl = "";
					bannerModel.url = model.action;


					i = new Intent(context, BoutBannerActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtra("BannerModel", (Serializable) bannerModel);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
					Logger.i("banner页" + model.page + model.action);

				} else if (("notice").equals(model.page)) {
					Logger.i("公告页");
					NoticeModel noticeModel=new NoticeModel();
					noticeModel.page=model.page;
					noticeModel.title=model.title;
					noticeModel.action = model.action;

					i = new Intent(context, SeNewNoticeActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.putExtra("noticeModel", (Serializable) noticeModel);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(i);
				} else {
					i = new Intent(context, MainActivity.class);

					//打开自定义的Activity
					i.putExtras(bundle);
					//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(i);
				}
			}
			/*else if (("to_invest").equals(model.to_page)){
				L.I("投资页面");
				i = new Intent(context, MainActivityTest.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}*//*else if (("to_auth").equals(model.to_page)){
				L.I("认证页面");
				i = new Intent(context, MeMyAccountActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
			}*/


			//打开自定义的Activity
        	/*Intent i = new Intent(context, TestActivity.class);
        	i.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);*/
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivityTest
	private void processCustomMessage(Context context, Bundle bundle) {
		if (SplashActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(SplashActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(SplashActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(SplashActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
	}
}

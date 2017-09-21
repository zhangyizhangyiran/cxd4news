package com.cxd.cxd4android.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.subactivity.MeMyAccountActivity;
import com.cxd.cxd4android.global.BaiDustatistic;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IBuyProductCheckCallBack;
import com.cxd.cxd4android.interfaces.IBuyProductJumpLoginCallBack;
import com.cxd.cxd4android.widget.gestrues.GestureLockView;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CheckoutGestureLockActivity extends BaseActivity{

	@Bind(R.id.gestureLockView)
	GestureLockView gestureLockView;
	@Bind(R.id.textview)
	 TextView textview;
	@Bind(R.id.forget_gesture_psw)
	 TextView forget_gesture_psw;
	@Bind(R.id.forget_gesture_account)
	 TextView forget_gesture_account;

	private Animation animation;

	private int errorNum;//错误数量

	private int limitErrorNum=5;//限制错误数次

	private boolean isFirstDeblock=false;

	public static String CHECKOUT_LOCK="checkoutLock";

	LocalUserModel userModel;

	public String VALUE = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout_gesturelock);
		ButterKnife.bind(this);

		userModel = new LocalUserModel();
		VALUE = getIntent().getStringExtra("KEY");

		init();
		setListener();
	}

	//设置监听
	private void setListener() {
		forget_gesture_psw.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (("MeMyAccountFragmentModify").equals(VALUE)) {
					EventBus.getDefault().post(new IBuyProductJumpLoginCallBack("BuyProductActivityPsw"));
					MeMyAccountActivity.MeMyAccountActivity.finish();

					String getid = userModel.getid();
					//清除数据
					userModel.clear();
					userModel.setid(getid);
				} else {
					EventBus.getDefault().post(new IBuyProductCheckCallBack("BuyProductActivityPsw"));
				}

				finish();
				StatService.onEvent(CheckoutGestureLockActivity.this, BaiDustatistic.gesturepwd_forget, "", 1);//事件统计
			}
		});

		forget_gesture_account.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (("MeMyAccountFragmentModify").equals(VALUE)) {
					EventBus.getDefault().post(new IBuyProductJumpLoginCallBack("BuyProductActivityAccount"));
					MeMyAccountActivity.MeMyAccountActivity.finish();
//					//清除数据
					userModel.clear();
				} else {
					EventBus.getDefault().post(new IBuyProductCheckCallBack("BuyProductActivityAccount"));
				}
				finish();
				StatService.onEvent(CheckoutGestureLockActivity.this, BaiDustatistic.gesturepwd_loginother, "", 1);//事件统计

			}
		});
	}

	public void init(){

		animation = new TranslateAnimation(-20, 20, 0, 0);
		animation.setDuration(50);
		animation.setRepeatCount(2);
		animation.setRepeatMode(Animation.REVERSE);
		//设置密码
//		String key = LockPatternUtils.getLockPattern(this, Constant.GESTURE_KEY);
		String key = userModel.getLockPattern();
		if(TextUtils.isEmpty(key)){
			finish();
		}else{
			gestureLockView.setKey(key);
		}

		//手势完成后回
		gestureLockView.setOnGestureFinishListener(new GestureLockView.OnGestureFinishListener() {
			@Override
			public void OnGestureFinish(boolean success, String key) {
				if (success) {
					textview.setTextColor(Color.parseColor("#FFFFFF"));
					textview.setVisibility(View.VISIBLE);
					textview.setText("密码正确");
					textview.startAnimation(animation);
					finish();

					if (("BuyProductActivityRechange").equals(VALUE)) {
						EventBus.getDefault().post(new IBuyProductCheckCallBack("BuyProductActivityRechange"));
						userModel.setGestrueIsFirst("yes");
					}else if (("BuyProductActivityMakemoney").equals(VALUE)){
						EventBus.getDefault().post(new IBuyProductCheckCallBack("BuyProductActivityMakemoney"));
						userModel.setGestrueIsFirst("yes");
					}else if (("MeMainFragmentRecharge").equals(VALUE)){
						EventBus.getDefault().post(new IBuyProductCheckCallBack("MeMainFragmentRecharge"));
						userModel.setGestrueIsFirst("yes");
					}else if (("MeMyAccountFragmentModify").equals(VALUE)) {
						Intent intent = new Intent(CheckoutGestureLockActivity.this, SetGestureLockActivity.class);
						intent.putExtra("KEY", "MeMyAccountFragmentModify");
						startActivity(intent);
					}
				} else {
					errorNum++;
					if (errorNum >= limitErrorNum) {
						Toast.makeText(getApplicationContext(), "错误次数超过" + limitErrorNum + "次，请重新登录", Toast.LENGTH_SHORT).show();
//						LockPatternUtils.setLogin(getApplicationContext(), false);
//						LockPatternUtils.saveLockPattern(getApplicationContext(), MyApplication.GESTURE_KEY, "");
//						startActivity(new Intent(CheckoutGestureLockActivity.this,LoginActivity.class));
//						finish();

						EventBus.getDefault().post(new IBuyProductCheckCallBack("BuyProductActivityAccount"));
						//再次操作账户退出,退出登录
						finish();
					}
					textview.setTextColor(Color.parseColor("#FF2525"));
					textview.setVisibility(View.VISIBLE);
					textview.setText("密码错误" + errorNum + "次");
					textview.startAnimation(animation);

				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 与StatService.onResume(this)类似;
		 */
		StatService.onPageStart(this, "验证手密");//(this);
	}

	public void onPause() {
		super.onPause();

		/**
		 * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 与StatService.onPause(this)类似;
		 */
		StatService.onPageEnd(this, "验证手密");//(this);
	}
}

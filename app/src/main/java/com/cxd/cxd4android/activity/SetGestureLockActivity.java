package com.cxd.cxd4android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IGestrueSetCallBack;
import com.cxd.cxd4android.widget.gestrues.GestureLockView;
import com.cxd.cxd4android.widget.gestrues.GestureLockView.OnGestureFinishListener;
import com.cxd.cxd4android.widget.gestrues.LockAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 设置手势密码
 * @author dalong
 *
 */
public class SetGestureLockActivity extends BaseActivity {

	@Bind(R.id.gv_lock)
	GridView gv_lock;//提示手势图片
	@Bind(R.id.gv_textview)
	TextView gv_textview;//提示语
	@Bind(R.id.gestureLockView)
	GestureLockView gestureLockView;//手势密码锁

	private LockAdapter lockAdapter;

	private boolean  isSetting;//是否绘制

	private TranslateAnimation animation;

	private int limitNum=4;


	LocalUserModel userModel;

	public String VALUE = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_gesturelock);
		ButterKnife.bind(this);

		userModel = new LocalUserModel();
		VALUE = getIntent().getStringExtra("KEY");

		setView();
		addListener();
	}


	private void setView() {
		lockAdapter = new LockAdapter(this);
		gv_lock.setAdapter(lockAdapter);
		gv_textview.setText("请绘制手势密码");
		gv_textview.setVisibility(View.VISIBLE);
		gv_textview.setTextColor(getResources().getColor(R.color.white));
		gestureLockView.setLimitNum(limitNum);
	}

	private void addListener() {
		animation = new TranslateAnimation(-20, 20, 0, 0);
		animation.setDuration(50);
		animation.setRepeatCount(2);
		animation.setRepeatMode(Animation.REVERSE);
		gestureLockView.setOnGestureFinishListener(new OnGestureFinishListener() {
			@Override
			public void OnGestureFinish(boolean success, String key) {
				if (success) {
					lockAdapter.setKey(key);
					if (!isSetting) {
						gv_textview.setText("再次绘制");
						gestureLockView.setKey(key);
						isSetting = true;
					} else {
						gv_textview.setTextColor(getResources().getColor(R.color.white));
						gv_textview.setText("绘制成功");
//						LockPatternUtils.saveLockPattern(getApplicationContext(), Constant.GESTURE_KEY, key);
						userModel.setLockPattern(key);
						finish();

						/*if (("MeMainFragment").equals(VALUE)){
							IGestrueSetCallBack.OnSetGestrue("MeMainFragment");
						}*/
						if (("MeMyAccountFragmentModify").equals(VALUE)) {
							EventBus.getDefault().post(new IGestrueSetCallBack("MeMyAccountFragmentModify"));
						}
						if (("MeMyAccountFragmentSwitch").equals(VALUE)) {
							EventBus.getDefault().post(new IGestrueSetCallBack("MeMyAccountFragmentSwitch"));

						}
					}
				} else {
					if (key.length() >= limitNum)
						gv_textview.setText("绘制的密码与上次不一致！");
					else
						gv_textview.setText("绘制的个数不能低于" + limitNum + "个");
					gv_textview.startAnimation(animation);
					gv_textview.setTextColor(getResources().getColor(R.color.red));
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
		StatService.onPageStart(this, "设置手密");//(this);
	}

	public void onPause() {
		super.onPause();

		/**
		 * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 与StatService.onPause(this)类似;
		 */
		StatService.onPageEnd(this, "设置手密");//(this);
	}
}

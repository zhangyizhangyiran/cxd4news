package com.cxd.cxd4android.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.cxd.cxd4android.R;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IUpdataInfoCallBack;
import com.cxd.cxd4android.widget.gestrues.GestureLockView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MeCheckGestureLockFragment extends BaseFragment {


    @Bind(R.id.gestureLockView)
     GestureLockView gestureLockView;

    @Bind(R.id.textview)
     TextView textview;

    private Animation animation;
    private int errorNum;//错误数量
    private int limitErrorNum=5;//限制错误数次

    public String VALUE = "";

    /**
     * 用户信息
     */
    LocalUserModel userModel;

    public static IUpdataInfoCallBack IUpdataInfoCallBack;
    MeMainFragment MeMainFragment;
    MeLoginFragment MeLoginFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_me_checkgesturelock, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userModel = new LocalUserModel();
        VALUE = getArguments().getString("KEY");
        init();
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
//            finish();
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
//                    finish();



                    if (("MainMeFragment").equals(VALUE)){
                        MeMainFragment();
                        userModel.setGestrueIsFirst("yes");

                    }

                } else {
                    errorNum++;
                    if (errorNum >= limitErrorNum) {
                        T.D("错误次数超过" + limitErrorNum + "次，请重新登录");
//						LockPatternUtils.setLogin(getApplicationContext(), false);
//						LockPatternUtils.saveLockPattern(getApplicationContext(), MyApplication.GESTURE_KEY, "");
//						startActivity(new Intent(CheckoutGestureLockActivity.this,LoginActivity.class));
//						finish();


                        //再次操作账户退出,退出登录
//                        finish();

                        if (("MainMeFragment").equals(VALUE)){
                            remove("MeCheckGestureLockFragment");
                            MeLoginFragment();
                        }

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
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.forget_gesture_psw,R.id.forget_gesture_account})
     void onClick(View view) {
        switch (view.getId()){
            case R.id.forget_gesture_psw://忘记手势密码
                Constant.FRAGMENT_JUMP = "MainMeFragment";
                remove("MeCheckGestureLockFragment");

                //清除数据
                userModel.clear();
                MeLoginFragment();
                break;
            case R.id.forget_gesture_account://其他账户登录
                Constant.FRAGMENT_JUMP = "";
                remove("MeCheckGestureLockFragment");

                //清除数据
                userModel.clear();
                MeLoginFragment();
                break;
            default:
                break;
        }
    }

    /**
     * 我验证手密跳转主页
     */
    public void MeMainFragment() {

//        if (MeMainFragment == null) {
            MeMainFragment = new MeMainFragment();
//        }

        add(R.id.main_fr_main_me, MeMainFragment, "MeMainFragment", null);
    }

    /**
     * 我验证手密跳转登录页
     */
    public void MeLoginFragment() {

        if (MeLoginFragment == null) {
            MeLoginFragment = new MeLoginFragment();
        }

        add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);
    }


    @Override
    public void onResume() {
        super.onResume();

        /**
         * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onResume(this)类似;
         */
        StatService.onPageStart(getActivity(), "我验证手密");//(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        /**
         * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
         * 与StatService.onPause(this)类似;
         */
        StatService.onPageEnd(getActivity(), "我验证手密");//(this);
    }
}

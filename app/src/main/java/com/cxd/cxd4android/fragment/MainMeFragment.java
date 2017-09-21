package com.cxd.cxd4android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.activity.BuyProductActivity;
import com.cxd.cxd4android.activity.CheckoutGestureLockActivity;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.Constant;
import com.cxd.cxd4android.global.LocalUserModel;
import com.cxd.cxd4android.interfaces.IBuyProductJumpLoginCallBack;
import com.cxd.cxd4android.interfaces.IBuyProductLockCallBack;
import com.micros.utils.A;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by moon.zhong on 2015/2/4.
 */
public class MainMeFragment extends BaseFragment {
    LocalUserModel userModel = new LocalUserModel();
    boolean background;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_layout_main_me, container, false);
            return contentView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        background = A.isBackground(getActivity());


        if (!background) {
            if (("ON").equals(userModel.getGestrueSwitch())) {                //设置保存状态

                MeCheckGestureLockFragment MeCheckGestureLockFragment = new MeCheckGestureLockFragment();
                Bundle bundle = new Bundle();
                bundle.putString("KEY", "MainMeFragment");
                MeCheckGestureLockFragment.setArguments(bundle);
                add(R.id.main_fr_main_me, MeCheckGestureLockFragment, "MeCheckGestureLockFragment", null);
            } else {
                if (userModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {

                    MeMainFragment MeMainFragment = new MeMainFragment();
                    add(R.id.main_fr_main_me, MeMainFragment, "MeMainFragment", null);

                } else if (userModel.LOGIN_STATE_OFFLINE.equals(userModel.getLOGIN_STATE())) {

                    MeLoginFragment MeLoginFragment = new MeLoginFragment();
                    add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);

                } else if (userModel.LOGIN_STATE_UNKNOW.equals(userModel.getLOGIN_STATE())) {

                    MeLoginFragment MeLoginFragment = new MeLoginFragment();
                    add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);

                } else {

                    MeLoginFragment MeLoginFragment = new MeLoginFragment();
                    add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);

                }
            }

        } else {
            if (userModel.LOGIN_STATE_ONLINE.equals(userModel.getLOGIN_STATE())) {

                MeMainFragment MeMainFragment = new MeMainFragment();
                add(R.id.main_fr_main_me, MeMainFragment, "MeMainFragment", null);

            } else if (userModel.LOGIN_STATE_OFFLINE.equals(userModel.getLOGIN_STATE())) {

                MeLoginFragment MeLoginFragment = new MeLoginFragment();
                add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);

            } else if (userModel.LOGIN_STATE_UNKNOW.equals(userModel.getLOGIN_STATE())) {

                MeLoginFragment MeLoginFragment = new MeLoginFragment();
                add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);

            } else {

                MeLoginFragment MeLoginFragment = new MeLoginFragment();
                add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);

            }
        }
    }

    /**
     * 事件响应方法,充值和马上赚钱验证成功返回
     */
    @Subscribe
    public void onEvent(IBuyProductLockCallBack event) {
        MeMainFragment MeMainFragment = new MeMainFragment();
        add(R.id.main_fr_main_me, MeMainFragment, "MeMainFragment", null);
        if (!background) {
            if ("no".equals(userModel.getGestrueIsFirst())) {
                if ("first".equals(userModel.getIsFirstSaveLock())) {
                    userModel.setIsFirstSaveLock("nofirst");
                } else {
//                    remove("MeCheckGestureLockFragment");
                }
            }
        } else {
        }
    }

    /**
     * 事件响应方法,跳转登录回调
     */
    @Subscribe
    public void onEvent(IBuyProductJumpLoginCallBack event) {
        if (("BuyProductActivityPsw").equals(event.getJump())) {
            Constant.FRAGMENT_JUMP = "MainMeFragment";
            MeLoginFragment MeLoginFragment = new MeLoginFragment();
            add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);
        } else if (("BuyProductActivityAccount").equals(event.getJump())) {
            Constant.FRAGMENT_JUMP = "";
            MeLoginFragment MeLoginFragment = new MeLoginFragment();
            add(R.id.main_fr_main_me, MeLoginFragment, "MeLoginFragment", null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

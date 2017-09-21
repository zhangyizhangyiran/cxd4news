package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeGhbWithdrawFragment;
import com.cxd.cxd4android.fragment.MeWithdrawFragment;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;

/**
 * ClassName:我-提现
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class MeWithdrawActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_withdraw);

        if (savedInstanceState == null) {
            if (Constant.ACCOUNT_GHB.equals(userModel.getThirdPayType())) {
                MeGhbWithdrawFragment ghbWithdrawFragment = new MeGhbWithdrawFragment();
                add(R.id.fragment_mywithdraw, ghbWithdrawFragment, "MeGhbWithdrawFragment", null);
            }else if (Constant.ACCOUNT_YEEPAY.equals(userModel.getThirdPayType())) {
                MeWithdrawFragment meWithdrawFragment = new MeWithdrawFragment();
                add(R.id.fragment_mywithdraw, meWithdrawFragment, "MeWithdrawFragment", null);
            }
        }
    }

}

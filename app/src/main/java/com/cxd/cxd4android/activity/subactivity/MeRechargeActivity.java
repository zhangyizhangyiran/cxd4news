package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeRechargeFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * ClassName:我-充值
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class MeRechargeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_recharge);

        if (savedInstanceState == null) {

            MeRechargeFragment MeRechargeFragment = new MeRechargeFragment();
            add(R.id.fragment_myrecharge, MeRechargeFragment, "MeRechargeFragment", null);
        }
    }
}

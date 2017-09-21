package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeMyAccountFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * ClassName:我-我的账户
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class MeMyAccountActivity extends BaseActivity {

    public static MeMyAccountActivity MeMyAccountActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeMyAccountActivity = this;
        setContentView(R.layout.activity_me_my_account);

        if (savedInstanceState == null) {

            MeMyAccountFragment MeMyAccountFragment = new MeMyAccountFragment();
            add(R.id.fragment_myaccount, MeMyAccountFragment, "MeMyAccountFragment", null);
        }
    }
}

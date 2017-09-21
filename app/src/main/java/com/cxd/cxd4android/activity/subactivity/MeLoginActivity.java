package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeLoginFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * ClassName:登录
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class MeLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_login);

        if (savedInstanceState == null) {

            MeLoginFragment MeLoginFragment = new MeLoginFragment();
            add(R.id.fragment_melogin, MeLoginFragment, "MeLoginFragment", null);
        }
    }
}

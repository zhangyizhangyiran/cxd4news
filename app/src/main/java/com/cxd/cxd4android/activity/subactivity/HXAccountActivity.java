package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.HXAccountFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * Created by Fong on 16/10/25.
 * 华兴账户页面
 */

public class HXAccountActivity extends BaseActivity {

    public static HXAccountActivity hxAccountActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hxAccountActivity=this;
        setContentView(R.layout.activity_hx_account);
        if (savedInstanceState == null) {

            HXAccountFragment hxAccountFragment = new HXAccountFragment();
            add(R.id.fragment_hxaccount, hxAccountFragment, "hxAccountFragment", null);
        }
    }

}
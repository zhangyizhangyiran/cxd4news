package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.SeHelpCenterFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * ClassName:服务-帮助中心
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class SeHelpCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_help_center);
        if (savedInstanceState == null) {

            SeHelpCenterFragment seHelpCenterFragment = new SeHelpCenterFragment();
            add(R.id.fragment_helpcenter, seHelpCenterFragment, "SeHelpCenterFragment", null);
        }
    }
}

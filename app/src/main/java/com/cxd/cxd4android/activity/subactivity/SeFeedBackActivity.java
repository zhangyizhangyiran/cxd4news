package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.SeFeedBackFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * ClassName:服务-意见反馈页面
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class SeFeedBackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_se_feed_back);
        if (savedInstanceState == null) {

            SeFeedBackFragment seFeedBackFragment = new SeFeedBackFragment();
            add(R.id.fragment_feedback, seFeedBackFragment, "SeFeedBackFragment", null);
        }
    }
}

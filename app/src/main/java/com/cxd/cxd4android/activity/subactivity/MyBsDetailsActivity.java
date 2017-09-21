package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.BounsDetailsFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * ClassName:礼品详情页
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 13:40
 * version：V1.0
 */
public class MyBsDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus_details);
        if (savedInstanceState == null) {

            BounsDetailsFragment bDetail = new BounsDetailsFragment();
            add(R.id.fragment_mybouns, bDetail, "BounsDetailsFragment", null);
        }


    }
}
package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeMyInvesFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * ClassName:我-投资
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class MeMyInvesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_my_inves);

        if (savedInstanceState == null) {

            MeMyInvesFragment meMyInvesFragment = new MeMyInvesFragment();
            add(R.id.fragment_myinves, meMyInvesFragment, "MeMyInvesFragment", null);
        }

    }
}

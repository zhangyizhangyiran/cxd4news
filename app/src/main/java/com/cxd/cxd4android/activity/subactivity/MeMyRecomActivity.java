package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeMyRecomFragment;
import com.cxd.cxd4android.global.BaseActivity;

/**
 * ClassName:我-我的推荐
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class MeMyRecomActivity extends BaseActivity {

    public static MeMyRecomActivity MeMyRecomActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeMyRecomActivity = this;
        setContentView(R.layout.activity_me_my_recom);

        if (savedInstanceState == null) {

            MeMyRecomFragment MeMyRecomFragment = new MeMyRecomFragment();
            add(R.id.fragment_myrecom, MeMyRecomFragment, "MeMyRecomFragment", null);
        }
    }
}

package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeMyBounsFragment;
import com.cxd.cxd4android.global.BaseActivity;


/**
 * ClassName:我-我的积分
 * Description：
 * Author：XiaoFa
 * Date：2016/4/22 16:40
 * version：V1.0
 */
public class MeMyBonusPointsActivity extends BaseActivity {

    public static MeMyBonusPointsActivity MeMyBonusPointsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MeMyBonusPointsActivity = this;
        setContentView(R.layout.activity_me_my_bouns_points);

        if (savedInstanceState == null) {

            MeMyBounsFragment meMyBounsFragment = new MeMyBounsFragment();
            add(R.id.fragment_mybouns_points, meMyBounsFragment, "MeMyBounsFragment", null);
        }
    }
}
package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeMyRtMoneyCalendFragment;
import com.cxd.cxd4android.global.BaseActivity;

public class ReturnMoneyCalendarActivity extends BaseActivity {
    public static ReturnMoneyCalendarActivity ReturnMoneyCalendarActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_my_inves);
        ReturnMoneyCalendarActivity = this;

        if (savedInstanceState == null) {

            MeMyRtMoneyCalendFragment meMyRtMoneyCalendFragment = new MeMyRtMoneyCalendFragment();
            add(R.id.fragment_myinves, meMyRtMoneyCalendFragment, "MeMyRtMoneyCalendFragment", null);
        }
    }
}
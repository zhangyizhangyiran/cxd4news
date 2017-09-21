package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.MeMyBillFragment;
import com.cxd.cxd4android.fragment.MeMyGhbBillFragment;
import com.cxd.cxd4android.global.BaseActivity;
import com.cxd.cxd4android.global.Constant;

/**
 * ClassName:我-我的账单
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class MeMyBillActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_my_bill);

        if (savedInstanceState == null) {
            if (Constant.ACCOUNT_YEEPAY.equals(userModel.getThirdPayType())) {

                MeMyBillFragment MeMyBillFragment = new MeMyBillFragment();
                add(R.id.fragment_mybill, MeMyBillFragment, "MeMyBillFragment", null);

            } else if (Constant.ACCOUNT_GHB.equals(userModel.getThirdPayType())) {

                MeMyGhbBillFragment myGhbBillFragment = new MeMyGhbBillFragment();
                add(R.id.fragment_mybill, myGhbBillFragment, "MeMyGhbBillFragment", null);

            }else if (Constant.ACCOUNT_SHB.equals(userModel.getThirdPayType())){
                MeMyGhbBillFragment myGhbBillFragment = new MeMyGhbBillFragment();
                add(R.id.fragment_mybill, myGhbBillFragment, "MeMyGhbBillFragment", null);
            }
        }
    }
}

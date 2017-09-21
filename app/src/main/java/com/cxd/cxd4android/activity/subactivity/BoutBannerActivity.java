package com.cxd.cxd4android.activity.subactivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.BoutBannerFragment;
import com.cxd.cxd4android.global.WebViewActivity;
import com.cxd.cxd4android.model.BannerModel;


/**
 * ClassName:精选页面-banner
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class BoutBannerActivity extends WebViewActivity {

    private BoutBannerFragment mBoutBannerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决WebView中软键盘会遮挡输入框相关问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_bout_banner);

        if (savedInstanceState == null) {

            BannerModel BannerModel = (BannerModel) getIntent().getSerializableExtra("BannerModel");
            mBoutBannerFragment = new BoutBannerFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("BannerModel", BannerModel);
            mBoutBannerFragment.setArguments(bundle);
            add(R.id.fragment_boutbanner, mBoutBannerFragment, "BoutBannerFragment", null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (mBoutBannerFragment instanceof BoutBannerFragment) {
            //调用BoutBannerFragment的onKeyDown方法实现网页返回上一层
            mBoutBannerFragment.onKeyDown(keyCode, event);
            return false;
        }
        return false;


    }
}

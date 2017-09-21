package com.cxd.cxd4android.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.cxd.cxd4android.R;
import com.cxd.cxd4android.fragment.WebViewFragment;
import com.cxd.cxd4android.global.BaseFragment;
import com.cxd.cxd4android.global.WebViewActivity;
import com.cxd.cxd4android.model.YeePayModel;

/**
 * ClassName:易宝交互页面
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/2/25 15:40
 * version：V1.0
 */
public class YeePayActivity extends WebViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决WebView中软键盘会遮挡输入框相关问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_layout_yee_pay);

        if (savedInstanceState == null) {

            YeePayModel yeePayModel = (YeePayModel) getIntent().getSerializableExtra("YeePayModel");
            Bundle bundle = new Bundle();
            bundle.putSerializable("YeePayModel", yeePayModel);
            WebViewFragment webViewFragment = new WebViewFragment();
            webViewFragment.setArguments(bundle);
            add(R.id.fragment_yee_pay, webViewFragment, "WebViewFragment", null);

        }
    }

    public void add(int containerViewId, BaseFragment fragment, String tag, String stackName) {
        //.addToBackStack(stackName)
        getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, tag).commitAllowingStateLoss();
    }
}

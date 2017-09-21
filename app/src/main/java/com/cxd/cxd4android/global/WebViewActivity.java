package com.cxd.cxd4android.global;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;

import com.cxd.cxd4android.widget.X5WebView;

/**
 * @version V1.0
 * @ClassName:
 * @Description:
 * @Author：xiaofa
 * @Date：2016/7/6 18:14
 */
public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        X5WebView.setSmallWebViewEnabled(true);
    }
}

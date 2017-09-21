package com.cxd.cxd4android.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @version V1.0
 * @ClassName: 自定义scrollview，监听它到顶部和底部
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/27 17:41
 */
public class MyScrollview extends ScrollView {

    public MyScrollview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context) {
        super(context);
    }

    MyOnScrollChanged _t = null;

    public void onS(MyOnScrollChanged t) {
        _t = t;
    }

    // 是否正在移动
    public boolean istouch = false;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub
        super.onScrollChanged(l, t, oldl, oldt);

        if (istouch) {
            if (this.getScrollY() + this.getHeight() >= computeVerticalScrollRange()) {
                istouch = false;
                _t.down();
            }
            if (t == 0) {
                istouch = false;
                _t.up();
            }
        }

    }
}

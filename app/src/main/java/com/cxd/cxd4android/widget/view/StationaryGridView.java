package com.cxd.cxd4android.widget.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * @version V1.0
 * @ClassName:嵌套到scrollview中时 解决不能一起滑动问题
 * @Description:
 * @Author：xiaofa
 * @Date：2016/4/21 20:57
 */
public class StationaryGridView extends GridView{

    public StationaryGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= 9) {
            this.setOverScrollMode(View.OVER_SCROLL_NEVER);//解决滑动到顶部和底部阴影问题
        }
    }

    public StationaryGridView(Context context) {
        super(context);
    }

    public StationaryGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Build.VERSION.SDK_INT >= 9) {
            this.setOverScrollMode(View.OVER_SCROLL_NEVER);//解决滑动到顶部和底部阴影问题
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

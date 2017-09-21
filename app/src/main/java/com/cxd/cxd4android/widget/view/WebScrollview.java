package com.cxd.cxd4android.widget.view;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebView;

/**
 * @version V1.0
 * @ClassName: 解决scrollview嵌套webview不能滑动问题,
 * @Description:自定义继承WebView, 监听滚动，拿到滚动距离，在回调中将滚动距离作用到外层的scrollview上。
 * @Author：xiaofa
 * @Date：2016/6/15 18:52
 */
public class WebScrollview extends WebView {
//    private GestureDetector mGestureDetector;
//    View.OnTouchListener mGestureListener;
//
//    public WebScrollview(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mGestureDetector = new GestureDetector(context, new YScrollDetector());
//        setFadingEdgeLength(0);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
//    }
//
//    // Return false if we're scrolling in the x direction
//    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            if (Math.abs(distanceY) > Math.abs(distanceX)) {
//                return true;
//            }
//            return false;
//        }
//    }

        private OnScrollChangedCallback mOnScrollChangedCallback;

//        public WebScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
//            super(context, attrs, defStyleAttr);
//            // TODO Auto-generated constructor stub
//        }

        public WebScrollview(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
        }

        public WebScrollview(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        public void setOnScrollChangedCallback(
                final OnScrollChangedCallback onScrollChangedCallback) {
            mOnScrollChangedCallback = onScrollChangedCallback;
        }

        public static interface OnScrollChangedCallback {
            public void onScroll(int deltaY);
        }
        @Override
        public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                    int scrollY, int scrollRangeX, int scrollRangeY,
                                    int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            if (mOnScrollChangedCallback != null) {
                mOnScrollChangedCallback.onScroll(deltaY);
            }
            return true;
        }
}
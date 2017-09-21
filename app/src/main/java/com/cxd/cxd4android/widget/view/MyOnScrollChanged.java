package com.cxd.cxd4android.widget.view;


import com.orhanobut.logger.Logger;

/**
 * @version V1.0
 * @ClassName:z自定义scrollview中上下滑动事件
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/27 17:43
 */
public  class MyOnScrollChanged {

    public MyOnScrollChanged() {
        // TODO Auto-generated constructor stub
    }
    public void up()
    {
        Logger.i("MyScrollView 到顶部");
    }
    public void down()
    {
        Logger.i("MyScrollView 到底部");
    }

}

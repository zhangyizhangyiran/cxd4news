package com.cxd.cxd4android.interfaces;

/**
 * @version V1.0
 * @ClassName:
 * @Description:
 * @Author：xiaofa
 * @Date：2016/7/18 16:57
 */
public class IPageSelectCallBack {
    private int page;

    public IPageSelectCallBack(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }
}

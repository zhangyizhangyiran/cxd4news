package com.cxd.cxd4android.interfaces;

/**
 * ClassName:IRepestLoginCallBack
 * Description：重复登录后回调
 * Author：Chengel_HaltuD
 * Date：2016/7/7 11:09
 * version：V1.0
 */
public class IRepestLoginCallBack {

    private String loginState = "";

    public IRepestLoginCallBack(String loginState) {
        this.loginState = loginState;
    }

    public String getRestLoginCallBack() {
        return loginState;
    }
}

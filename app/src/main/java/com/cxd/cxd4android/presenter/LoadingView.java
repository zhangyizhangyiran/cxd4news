package com.cxd.cxd4android.presenter;

import com.micros.injection.presenter.MicroBaseView;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/7/5 10:26
 * version：V1.0
 */
public interface LoadingView<T> extends MicroBaseView<T> {

    void getDataSuccess(T model);

    void getDataFail(String msg);

    void showLoading();

    void hideLoading();

}
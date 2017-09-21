package com.cxd.cxd4android.api;

import com.micros.injection.presenter.MicroApiCallback;

/**
 * ClassName:Api回调
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/7/5 10:24
 * version：V1.0
 */
public interface ApiCallback<T> extends MicroApiCallback<T> {

    void onSuccess(T model);

    void onFailure(int code, String msg);

    void onCompleted();

}

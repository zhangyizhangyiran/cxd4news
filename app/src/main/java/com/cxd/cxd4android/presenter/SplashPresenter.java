package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.AppBoutMiddleModel;
import com.cxd.cxd4android.model.ShareMallModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:SplashPresenter
 * Description：启动页面广告图
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class SplashPresenter extends MicroBasePresenter<LoadingView> {

    public SplashPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getAppUrls(strn),
                new SubscriberCallBack<ShareMallModel>(new ApiCallback<ShareMallModel>() {
                    @Override
                    public void onSuccess(ShareMallModel model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mvpView.getDataFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        mvpView.hideLoading();
                    }
                }));
    }

}

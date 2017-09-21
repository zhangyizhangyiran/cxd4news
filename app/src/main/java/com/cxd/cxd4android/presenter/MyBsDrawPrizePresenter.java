package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.PointHistoryModel;
import com.cxd.cxd4android.model.ScreenBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * @version V1.0
 * @ClassName:
 * @Description:
 * @Author：xiaofa
 * @Date：2016/7/6 16:46
 */
public class MyBsDrawPrizePresenter extends MicroBasePresenter<LoadingView> {

    public MyBsDrawPrizePresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getUserPrizeDetails(strn),
                new SubscriberCallBack<PointHistoryModel>(new ApiCallback<PointHistoryModel>() {
                    @Override
                    public void onSuccess(PointHistoryModel model) {
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

    public void loadDataScreen(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getCxdAppDict(strn),
                new SubscriberCallBack<ScreenBaseModel>(new ApiCallback<ScreenBaseModel>() {
                    @Override
                    public void onSuccess(ScreenBaseModel model) {
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

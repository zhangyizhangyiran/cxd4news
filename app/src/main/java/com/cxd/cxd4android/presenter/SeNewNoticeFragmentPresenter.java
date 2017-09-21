package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.NewNoticeBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:BounsConfirmFragmentPresenter
 * Description：礼品确认页面 中的数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class SeNewNoticeFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public SeNewNoticeFragmentPresenter(LoadingView view) {
        attachView(view);
    }

    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getNoticeDetail(strn),
                new SubscriberCallBack<NewNoticeBaseModel>(new ApiCallback<NewNoticeBaseModel>() {
                    @Override
                    public void onSuccess(NewNoticeBaseModel model) {
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

    public void loadNoticeList(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getNoticeList(strn),
                new SubscriberCallBack<NewNoticeBaseModel>(new ApiCallback<NewNoticeBaseModel>() {
                    @Override
                    public void onSuccess(NewNoticeBaseModel model) {
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

    public void loadRepayList(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getRepayList(strn),
                new SubscriberCallBack<NewNoticeBaseModel>(new ApiCallback<NewNoticeBaseModel>() {
                    @Override
                    public void onSuccess(NewNoticeBaseModel model) {
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

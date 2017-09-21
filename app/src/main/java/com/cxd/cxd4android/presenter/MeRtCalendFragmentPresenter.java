package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.BackCalendarModel;
import com.cxd.cxd4android.model.UserInvestsBaseModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:BounsConfirmFragmentPresenter
 * Description：礼品确认页面 中的数据请求
 * Author：XiaoFa
 * Date：2016/7/9 13:14
 * version：V1.0
 */
public class MeRtCalendFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeRtCalendFragmentPresenter(LoadingView view) {
        attachView(view);
    }
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadMMBackCalendar(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getMMBackCalendar(strn),
                new SubscriberCallBack<BackCalendarModel>(new ApiCallback<BackCalendarModel>() {
                    @Override
                    public void onSuccess(BackCalendarModel model) {
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
    public void loadDDBackCalendar(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getDDBackCalendar(strn),
                new SubscriberCallBack<UserInvestsBaseModel>(new ApiCallback<UserInvestsBaseModel>() {
                    @Override
                    public void onSuccess(UserInvestsBaseModel model) {
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

package com.cxd.cxd4android.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.AppMypointModel;
import com.cxd.cxd4android.model.BindBankCardBaseModel;
import com.cxd.cxd4android.model.BindCardQueryBaseModel;
import com.cxd.cxd4android.model.BindPhoneBaseModel;
import com.cxd.cxd4android.model.GhbBindCardModel;
import com.cxd.cxd4android.model.GhbUserExtModel;
import com.cxd.cxd4android.model.IsRealNameBaseModel;
import com.cxd.cxd4android.model.PwsTransBaseModel;
import com.cxd.cxd4android.model.ShareMallModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.micros.injection.presenter.MicroBasePresenter;

/**
 * ClassName:MeMainFragmentPresenter
 * Description：主页面 我中的数据请求
 * Author：XiaoFa
 * Date：2016/7/11 13:14
 * version：V1.0
 */
public class MeMainFragmentPresenter extends MicroBasePresenter<LoadingView> {

    public MeMainFragmentPresenter(LoadingView view) {
        attachView(view);
    }

    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void loadAccountData(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getAccountBalance(strn),
                new SubscriberCallBack<AccountBalanceBaseModel>(new ApiCallback<AccountBalanceBaseModel>() {
                    @Override
                    public void onSuccess(AccountBalanceBaseModel model) {
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

    public void loadIsRealName(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getisRealnameAuthent(strn),
                new SubscriberCallBack<IsRealNameBaseModel>(new ApiCallback<IsRealNameBaseModel>() {
                    @Override
                    public void onSuccess(IsRealNameBaseModel model) {
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

    public void loadBankCard(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getBindCardQuery(strn),
                new SubscriberCallBack<BindCardQueryBaseModel>(new ApiCallback<BindCardQueryBaseModel>() {
                    @Override
                    public void onSuccess(BindCardQueryBaseModel model) {
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

    public void loadBindBankCard(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getBindBankCard(strn),
                new SubscriberCallBack<BindBankCardBaseModel>(new ApiCallback<BindBankCardBaseModel>() {
                    @Override
                    public void onSuccess(BindBankCardBaseModel model) {
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

    public void loadPwsTrans(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getResetYeePayPsw(strn),
                new SubscriberCallBack<PwsTransBaseModel>(new ApiCallback<PwsTransBaseModel>() {
                    @Override
                    public void onSuccess(PwsTransBaseModel model) {
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

    public void loadBindPhone(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getResetMobile(strn),
                new SubscriberCallBack<BindPhoneBaseModel>(new ApiCallback<BindPhoneBaseModel>() {
                    @Override
                    public void onSuccess(BindPhoneBaseModel model) {
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

    public void loadMyPointH5(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getAppMyPiont(strn),
                new SubscriberCallBack<AppMypointModel>(new ApiCallback<AppMypointModel>() {
                    @Override
                    public void onSuccess(AppMypointModel model) {
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

    /**
     * 加载E账户信息和绑卡状态
     * @param strn
     */
    public void loadGhbUserExt(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGhbUserExt(strn),
                new SubscriberCallBack<GhbUserExtModel>(new ApiCallback<GhbUserExtModel>() {
                    @Override
                    public void onSuccess(GhbUserExtModel model) {
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

    /**
     * 加载userInfo信息数据
     * @param strn
     */
    public void loadUserInfo(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getUserInfo(strn),
                new SubscriberCallBack<UserInfoModel>(new ApiCallback<UserInfoModel>() {
                    @Override
                    public void onSuccess(UserInfoModel model) {
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

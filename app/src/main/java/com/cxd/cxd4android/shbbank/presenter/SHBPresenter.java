package com.cxd.cxd4android.shbbank.presenter;

import com.cxd.cxd4android.api.ApiCallback;
import com.cxd.cxd4android.api.ApiClient;
import com.cxd.cxd4android.api.ApiStores;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.presenter.LoadingView;
import com.cxd.cxd4android.server.SubscriberCallBack;
import com.cxd.cxd4android.shbbank.model.SHBActivationAModel;
import com.cxd.cxd4android.shbbank.model.SHBAssessModel;
import com.cxd.cxd4android.shbbank.model.SHBBankPersonInfo;
import com.cxd.cxd4android.shbbank.model.SHBBankResultInfo;
import com.cxd.cxd4android.shbbank.model.SHBMobileChange;
import com.cxd.cxd4android.shbbank.model.SHBModel;
import com.cxd.cxd4android.shbbank.model.SHBOPenccountAModel;
import com.cxd.cxd4android.shbbank.model.SHBQueryBankList;
import com.cxd.cxd4android.shbbank.model.SHBReaetBankCard;
import com.cxd.cxd4android.shbbank.model.SHBUserInvest;
import com.cxd.cxd4android.shbbank.model.SHBUserResult;
import com.micros.injection.presenter.MicroBasePresenter;

import java.util.Map;

/**
 * Created by chengelhaltud on 17/9/1.
 */

public class SHBPresenter extends MicroBasePresenter<LoadingView> {

    public SHBPresenter(LoadingView view) {
        attachView(view);
    }

    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);

    public void openAccount(Map<String, String> map) {
        mvpView.showLoading();

        addSubscription(apiStores.getOpenAccount(map),
                new SubscriberCallBack<SHBOPenccountAModel>(new ApiCallback<SHBOPenccountAModel>() {
                    @Override
                    public void onSuccess(SHBOPenccountAModel model) {
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


    //    获取银行列表
    public void bankList() {
        mvpView.showLoading();
        addSubscription(apiStores.getShbBankList(), new SubscriberCallBack<SHBQueryBankList>(new ApiCallback<SHBQueryBankList>() {
            @Override
            public void onSuccess(SHBQueryBankList model) {
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

    //    获取用户信息(风险评估状态，等)
    //    获取个人银行卡信息
    public void SHBPersonINfo(String s) {
        mvpView.showLoading();
        addSubscription(apiStores.getShbPersonInfo(s), new SubscriberCallBack<SHBBankPersonInfo>(new ApiCallback<SHBBankPersonInfo>() {
            @Override
            public void onSuccess(SHBBankPersonInfo model) {
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

    public void resetMobile(Map<String, String> map) {
        mvpView.showLoading();

        addSubscription(apiStores.getResetMobile(map),
                new SubscriberCallBack<SHBMobileChange>(new ApiCallback<SHBMobileChange>() {
                    @Override
                    public void onSuccess(SHBMobileChange model) {
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


    public void resetPassword(Map<String, String> map) {
        mvpView.showLoading();

        addSubscription(apiStores.getResetPassword(map),
                new SubscriberCallBack<SHBModel>(new ApiCallback<SHBModel>() {
                    @Override
                    public void onSuccess(SHBModel model) {
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


    //用户是否参加评估
    public void shbUserResult(String s) {
        mvpView.showLoading();
        addSubscription(apiStores.getShbuserAssessResult(s),
                new SubscriberCallBack<SHBUserResult>(new ApiCallback<SHBUserResult>() {
                    @Override
                    public void onSuccess(SHBUserResult model) {
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

    //换卡
    public void shbREsetBankCard(Map<String, String> map) {
        mvpView.showLoading();

        addSubscription(apiStores.getResetBankCard(map),
                new SubscriberCallBack<SHBReaetBankCard>(new ApiCallback<SHBReaetBankCard>() {
                    @Override
                    public void onSuccess(SHBReaetBankCard model) {
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


    public void userAssessResult(Map<String, String> map) {
        mvpView.showLoading();

        addSubscription(apiStores.getUserAssessResult(map),
                new SubscriberCallBack<SHBAssessModel>(new ApiCallback<SHBAssessModel>() {
                    @Override
                    public void onSuccess(SHBAssessModel model) {
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

    //用户投资数据请求
    public void userInvest(Map<String, String> map) {
        mvpView.showLoading();

        addSubscription(apiStores.getSHBInvest(map),
                new SubscriberCallBack<SHBUserInvest>(new ApiCallback<SHBUserInvest>() {
                    @Override
                    public void onSuccess(SHBUserInvest model) {
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

//    用户激活

    public void activateStockedUser(String map) {
        mvpView.showLoading();

        addSubscription(apiStores.getActivateStockedUser(map),
                new SubscriberCallBack<SHBActivationAModel>(new ApiCallback<SHBActivationAModel>() {
                    @Override
                    public void onSuccess(SHBActivationAModel model) {
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

////////////////////////////////////////////    老接口

    /**
     * 加载风险评估等数据
     *
     * @param strn
     */
    public void loadSHBGuidePage(String strn) {
        mvpView.showLoading();

        addSubscription(apiStores.getGhbGuidePage(strn),
                new SubscriberCallBack<GuideModel>(new ApiCallback<GuideModel>() {
                    @Override
                    public void onSuccess(GuideModel model) {
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


    /**
     * 加载SHB上海银行换卡结果
     *
     */

    public void loadBankInfo(String strn) {
        mvpView.showLoading();
        addSubscription(apiStores.getSHBBankCardInfo(strn),
                new SubscriberCallBack<SHBBankResultInfo>(new ApiCallback<SHBBankResultInfo>() {
                    @Override
                    public void onSuccess(SHBBankResultInfo model) {
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

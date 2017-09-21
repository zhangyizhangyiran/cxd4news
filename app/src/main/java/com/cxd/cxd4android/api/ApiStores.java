package com.cxd.cxd4android.api;

import com.cxd.cxd4android.model.AccountBalanceBaseModel;
import com.cxd.cxd4android.model.AllPointsModel;
import com.cxd.cxd4android.model.AppBoutMiddleModel;
import com.cxd.cxd4android.model.AppMypointModel;
import com.cxd.cxd4android.model.BackCalendarModel;
import com.cxd.cxd4android.model.BannerBaseModel;
import com.cxd.cxd4android.model.BillBaseModel;
import com.cxd.cxd4android.model.BindBankCardBaseModel;
import com.cxd.cxd4android.model.BindCardQueryBaseModel;
import com.cxd.cxd4android.model.BindPhoneBaseModel;
import com.cxd.cxd4android.model.BounsSaveProductModel;
import com.cxd.cxd4android.model.BsDetailsModel;
import com.cxd.cxd4android.model.BsShipAddrModel;
import com.cxd.cxd4android.model.CheckMobileBaseModel;
import com.cxd.cxd4android.model.CheckVersionBaseModel;
import com.cxd.cxd4android.model.GhbBindCardModel;
import com.cxd.cxd4android.model.GhbIdentityModel;
import com.cxd.cxd4android.model.GhbInvestModel;
import com.cxd.cxd4android.model.GhbRechargeModel;
import com.cxd.cxd4android.model.GhbUserExtModel;
import com.cxd.cxd4android.model.GhbWithdrawModel;
import com.cxd.cxd4android.model.GiftRecommendModel;
import com.cxd.cxd4android.model.GuideModel;
import com.cxd.cxd4android.model.IdentityBaseModel;
import com.cxd.cxd4android.model.InvesListBaseModel;
import com.cxd.cxd4android.model.InvestModel;
import com.cxd.cxd4android.model.IsRealNameBaseModel;
import com.cxd.cxd4android.model.LoginBaseModel;
import com.cxd.cxd4android.model.MakeMoneyModel;
import com.cxd.cxd4android.model.NewNoticeBaseModel;
import com.cxd.cxd4android.model.NewerBaseModel;
import com.cxd.cxd4android.model.PointHistoryModel;
import com.cxd.cxd4android.model.ProductRecodBaseModel;
import com.cxd.cxd4android.model.PwsTransBaseModel;
import com.cxd.cxd4android.model.RecomBaseModel;
import com.cxd.cxd4android.model.RedMessageModel;
import com.cxd.cxd4android.model.RedPaperModel;
import com.cxd.cxd4android.model.RegisterBaseModel;
import com.cxd.cxd4android.model.RepayPlanBaseModel;
import com.cxd.cxd4android.model.RequestResultModel;
import com.cxd.cxd4android.model.ReturnYeePayMsgBaseModel;
import com.cxd.cxd4android.model.ScreenBaseModel;
import com.cxd.cxd4android.model.SendCodeModel;
import com.cxd.cxd4android.model.SendYeePayMsgBaseModel;
import com.cxd.cxd4android.model.ServiceBaseModel;
import com.cxd.cxd4android.model.ShareMallModel;
import com.cxd.cxd4android.model.SimpleModel;
import com.cxd.cxd4android.model.UnIdentityBaseModel;
import com.cxd.cxd4android.model.UphAddrDataModel;
import com.cxd.cxd4android.model.UploadDeviceTokenModel;
import com.cxd.cxd4android.model.UserInfoModel;
import com.cxd.cxd4android.model.UserInvestsBaseModel;
import com.cxd.cxd4android.model.WithdrawBaseModel;
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

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2016/7/4 9:48
 * version：V1.0
 */
public interface ApiStores {

    /**
     * 投资记录
     **/
    @FormUrlEncoded
    @POST("/loanc/investRecords?")
    Observable<ProductRecodBaseModel> getInvestRecords(@Field("value") String value);

    /**我的账户 余额**/
    /**
     * 用户余额
     **/
    @FormUrlEncoded
    @POST("/myaccountc/accountBalance?")
    Observable<AccountBalanceBaseModel> getAccountBalance(@Field("value") String value);

    /**
     * 购买页面 项目详情
     **/
    @FormUrlEncoded
    @POST("/loanc/loan?")
    Observable<NewerBaseModel> getLoan(@Field("value") String value);

    /**
     * 购买页面 投资（购买）
     **/
    @FormUrlEncoded
    @POST("/loanc/invest?")
    Observable<IdentityBaseModel> getInvest(@Field("value") String value);

    /**
     * 列表筛选条件
     **/
    @FormUrlEncoded
    @POST("/otherc/cxdAppDict?")
    Observable<ScreenBaseModel> getCxdAppDict(@Field("value") String value);

    /**
     * 11获取抽奖记录
     **/
    @FormUrlEncoded
    @POST("/pointc/getUserPrizeDetails?")
    Observable<PointHistoryModel> getUserPrizeDetails(@Field("value") String value);

    /**
     * 11获取积分明细
     **/
    @FormUrlEncoded
    @POST("/pointc/getUserPointHistory?")
    Observable<PointHistoryModel> getUserPointHistory(@Field("value") String value);

    /**
     * 奖品兑换保存
     **/
    @FormUrlEncoded
    @POST("/pointc/saveDHGift?")
    Observable<RequestResultModel> getSaveDHGift(@Field("value") String value);

    /**
     * 奖品详情
     **/
    @FormUrlEncoded
    @POST("/pointc/giftDetails?")
    Observable<BsDetailsModel> getGiftDetails(@Field("value") String value);

    /**
     * 3奖品兑换保存
     **/
    @FormUrlEncoded
    @POST("/pointc/saveDHGift?")
    Observable<BounsSaveProductModel> getSaveProduct(@Field("value") String value);

    /**
     * 5获取用户收货地址
     **/
    @FormUrlEncoded
    @POST("/pointc/getUserAddress?")
    Observable<BsShipAddrModel> getUserAddress(@Field("value") String value);

    /**
     * 6保存用户收货地址
     **/
    @FormUrlEncoded
    @POST("/pointc/saveUserAddress?")
    Observable<RequestResultModel> getSaveAddress(@Field("value") String value);


    /**
     * Banner
     **/
    @FormUrlEncoded
    @POST("/otherc/loadBanners?")
    Observable<BannerBaseModel> getLoadBanners(@Field("value") String value);

    /**
     * 新手标
     **/
    @FormUrlEncoded
    @POST("/loanc/loadNewerLoan?")
    Observable<NewerBaseModel> getLoadNewerLoan(@Field("value") String value);

    /**
     * 版本检测
     **/
    @FormUrlEncoded
    @POST("/otherc/version?")
    Observable<CheckVersionBaseModel> getLoadVersion(@Field("value") String value);

    /**
     * 充值
     **/
    @FormUrlEncoded
    @POST("/myaccountc/recharge?")
    Observable<IdentityBaseModel> getRecharge(@Field("value") String value);

    /**
     * 投资列表
     **/
    @FormUrlEncoded
    @POST("/loanc/loans?")
    Observable<InvesListBaseModel> getInvestMent(@Field("value") String value);

    /**
     * 忘记密码
     **/
    @FormUrlEncoded
    @POST("/userc/resetPsw?")
    Observable<com.cxd.cxd4android.model.SimpleModel> getResetPsw(@Field("value") String value);

    /**
     * 注册推荐人是否存在
     **/
    @FormUrlEncoded
    @POST("/userc/isHaveUser?")
    Observable<CheckMobileBaseModel> getIsHaveUser(@Field("value") String value);

    /**
     * 发送验证码
     **/
    @FormUrlEncoded
    @POST("/userc/registerSendVerificationCode?")
    Observable<SendCodeModel> getRegisterSendCode(@Field("value") String value);

    /**
     * 登录
     **/
    @FormUrlEncoded
    @POST("/userc/login?")
    Observable<LoginBaseModel> getLogin(@Field("value") String value);

    /**
     * 绑卡查询
     **/
    @FormUrlEncoded
    @POST("/myaccountc/bindCardQuery?")
    Observable<BindCardQueryBaseModel> getBindCardQuery(@Field("value") String value);

    /**
     * 用户是否实名认证
     **/
    @FormUrlEncoded
    @POST("/userc/isRealnameAuthentication?")
    Observable<IsRealNameBaseModel> getisRealnameAuthent(@Field("value") String value);

    /**
     * 易宝绑卡
     **/
    @FormUrlEncoded
    @POST("/myaccountc/bindBankCard?")
    Observable<BindBankCardBaseModel> getBindBankCard(@Field("value") String value);

    /**
     * 解绑银行卡
     **/
    @FormUrlEncoded
    @POST("/myaccountc/unCardBind?")
    Observable<UnIdentityBaseModel> getUnBindBankCard(@Field("value") String value);

    /**
     * 修改易宝手机号
     **/
    @FormUrlEncoded
    @POST("/userc/resetYeePayMobile?")
    Observable<BindPhoneBaseModel> getResetMobile(@Field("value") String value);

    /**
     * 实名认证
     **/
    @FormUrlEncoded
    @POST("/userc/realnameAuthentication?")
    Observable<IdentityBaseModel> getRealname(@Field("value") String value);

    /**
     * 修改登录密码
     **/
    @FormUrlEncoded
    @POST("/userc/updatePsw?")
    Observable<com.cxd.cxd4android.model.SimpleModel> getUpdatePsw(@Field("value") String value);

    /**
     * 修改交易密码
     **/
    @FormUrlEncoded
    @POST("/userc/resetYeePayPsw?")
    Observable<PwsTransBaseModel> getResetYeePayPsw(@Field("value") String value);

    /**
     * 我的账单
     **/
    @FormUrlEncoded
    @POST("/myaccountc/transactionRecords?")
    Observable<BillBaseModel> getTransactionRecords(@Field("value") String value);

    /**
     * 积分-获取用户积分
     **/
    @FormUrlEncoded
    @POST("/pointc/getUserPoint?")
    Observable<AllPointsModel> getUserPoint(@Field("value") String value);

    /**
     * 2兑换奖品列表
     **/
    @FormUrlEncoded
    @POST("/pointc/giftRecommend?")
    Observable<GiftRecommendModel> getGiftRecommend(@Field("value") String value);

    /**
     * 我的投资
     **/
    @FormUrlEncoded
    @POST("/loanc/userInvests?")
    Observable<UserInvestsBaseModel> getUserInvests(@Field("value") String value);

    /**
     * 还款计划
     **/
    @FormUrlEncoded
    @POST("/loanc/repayPlan?")
    Observable<RepayPlanBaseModel> getRepayPlan(@Field("value") String value);

    /**
     * 我的推荐
     **/
    @FormUrlEncoded
    @POST("userc/getReferrerInfoList?")
    Observable<RecomBaseModel> getReferrerInfoList(@Field("value") String value);

    /**
     * 2根据月份获取还款信息
     **/
    @FormUrlEncoded
    @POST("/userc/loanInvestBackCalendar?")
    Observable<BackCalendarModel> getMMBackCalendar(@Field("value") String value);

    /**
     * 3根据日期获取还款信息
     **/
    @FormUrlEncoded
    @POST("/userc/backDateOfInvest?")
    Observable<UserInvestsBaseModel> getDDBackCalendar(@Field("value") String value);

    /**
     * 注册
     **/
    @FormUrlEncoded
    @POST("/userc/register?")
    Observable<RegisterBaseModel> getRegister(@Field("value") String value);

    /**
     * 注册手机号是否存在
     **/
    @FormUrlEncoded
    @POST("/userc/isUsePhone?")
    Observable<SimpleModel> getIsUsePhone(@Field("value") String value);

    /**
     * 提现费用
     **/
    @FormUrlEncoded
    @POST("/otherc/withdrawalFee?")
    Observable<WithdrawBaseModel> getWithdrawalFee(@Field("value") String value);

    /**
     * 提现
     **/
    @FormUrlEncoded
    @POST("/myaccountc/withdraw?")
    Observable<IdentityBaseModel> getwithdraw(@Field("value") String value);

    /**
     * 意见反馈
     **/
    @FormUrlEncoded
    @POST("/otherc/feedback?")
    Observable<com.cxd.cxd4android.model.SimpleModel> getFeedback(@Field("value") String value);

    /**
     * 总用户投资收益
     **/
    @FormUrlEncoded
    @POST("/otherc/moneyStatic?")
    Observable<ServiceBaseModel> getMoneyStatic(@Field("value") String value);

    /**
     * 通知详情
     **/
    @FormUrlEncoded
    @POST("otherc/loadNoticeDetail?")
    Observable<NewNoticeBaseModel> getNoticeDetail(@Field("value") String value);

    /**
     * 最新公告New
     **/
    @FormUrlEncoded
    @POST("otherc/loadNoticeList?")
    Observable<NewNoticeBaseModel> getNoticeList(@Field("value") String value);

    /**
     * 最新还款通知New
     **/
    @FormUrlEncoded
    @POST("otherc/loadRepayList?")
    Observable<NewNoticeBaseModel> getRepayList(@Field("value") String value);

    /**
     * 10根据投资ID获取投资信息
     **/
    @FormUrlEncoded
    @POST("/pointc/getInvestByInvestId?")
    Observable<InvestModel> getInvestByInvestId(@Field("value") String value);

    /**
     * uphID获取产品发货信息
     **/
    @FormUrlEncoded
    @POST("/pointc/getProductByUphId?")
    Observable<UphAddrDataModel> getProductByUphId(@Field("value") String value);

    /**
     * 发送app日志记录
     **/
    @FormUrlEncoded
    @POST("/otherc/appLog?")
    Observable<SendYeePayMsgBaseModel> getAppLog(@Field("value") String value);

    /**
     * 充值log日志
     **/
    @FormUrlEncoded
    @POST("/logc/operation_tip_log?")
    Observable<ReturnYeePayMsgBaseModel> getTiplog(@Field("value") String value);

    /**
     * 积分商城分享&启动页广告
     **/
    @FormUrlEncoded
    @POST("/otherc/appUrls")
    Observable<ShareMallModel> getAppUrls(@Field("value") String value);

    /**
     * app首页中间推荐展现形式 native & h5
     **/
    @FormUrlEncoded
    @POST("/loanc/index_middle_h5?")
    Observable<AppBoutMiddleModel> getAppBountMiddle(@Field("value") String value);

    /**
     * app积分商城展现形式 native & h5
     **/
    @FormUrlEncoded
    @POST("/pointc/my_piont_h5?")
    Observable<AppMypointModel> getAppMyPiont(@Field("value") String value);

    /**
     * 华兴开户(实名认证)
     * /userc/ghbRealnameAuthentication
     * 传入参数与易宝相同，返回参数 result-->html native & h5
     **/
    @FormUrlEncoded
    @POST("/userc/ghbRealnameAuthentication?")
    Observable<GhbIdentityModel> getGhbRealnameAuthentication(@Field("value") String value);

    /**
     * 华兴 绑卡激活接口
     **/
    @FormUrlEncoded
    @POST("/myaccountc/ghbBindBankCard?")
    Observable<GhbBindCardModel> getGhbBindBankCard(@Field("value") String value);


    /**
     * 华兴 绑卡状态回调接口
     **/
    @FormUrlEncoded
    @POST("/ghbc/getGhbUserExt?")
    Observable<GhbUserExtModel> getGhbUserExt(@Field("value") String value);

    /**
     * 华兴 充值接口
     **/
    @FormUrlEncoded
    @POST("/myaccountc/ghbRecharge?")
    Observable<GhbRechargeModel> getGhbRecharge(@Field("value") String value);

    /**
     * 华兴 提现接口
     **/
    @FormUrlEncoded
    @POST("/myaccountc/ghbWithdraw?")
    Observable<GhbWithdrawModel> getGhbWithdraw(@Field("value") String value);

    /**
     * 华兴 投资 接口
     **/
    @FormUrlEncoded
    @POST("/loanc/ghbInvest?")
    Observable<GhbInvestModel> getGhbInvest(@Field("value") String value);

    /**
     * 华兴 充值提现 实名认证 提示接口
     **/
    @FormUrlEncoded
    @POST("/otherc/getAppPageTipUrl?")
    Observable<GuideModel> getGhbGuidePage(@Field("value") String value);

    /**
     * 获取用户信息接口
     **/
    @FormUrlEncoded
    @POST("/userc/userInfo?")
    Observable<UserInfoModel> getUserInfo(@Field("value") String value);

    /**
     * 我的账单 (华兴)
     **/
    @FormUrlEncoded
    @POST("/myaccountc/ghbUserBill?")
    Observable<BillBaseModel> getGhbUserBill(@Field("value") String value);

    /**
     * 保存用户系统信息
     **/
    @FormUrlEncoded
    @POST("/otherc/uploadDeviceToken?")
    Observable<UploadDeviceTokenModel> getUploadDeviceToken(@Field("value") String value);

    /**
     * 修改华兴绑卡状态
     **/
    @FormUrlEncoded
    @POST("/userc/ghbUpdateBankCardStatus?")
    Observable<UploadDeviceTokenModel> getUpdateBankCardStatus(@Field("uid") String value);

    /**
     * 新增红包数据
     **/
    @GET("/couponc/getCouponsForInvest?")
    Observable<RedPaperModel> getRedPaper(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/couponc/investChangeCoupon?")
    Observable<RedMessageModel> getRedMessage(@Field("value") String value);

    /**
     * 风险投资数据
     **/
    @GET("/assess/getAssessApp?")
    Observable<MakeMoneyModel> getMakeMoney(@QueryMap Map<String, String> map);


    /**
     * SHB银行列表查询
     **/
    @POST("/shtransactiondc/queryBankList?")
    Observable<SHBQueryBankList> getShbBankList();

    /**
     * SHB身份信息查询
     * /shtransactiondc/queryUserBankInfo
     **/

    @POST("/shtransactiondc/queryUserBankInfo?")
    Observable<SHBBankPersonInfo> getShbPersonInfo(@Query("uid") String s);

    /**
     * 用户是否参见评估
     **/

    @GET("/assess/userAssessResult?")
    Observable<SHBUserResult> getShbuserAssessResult(@Query("uid") String s);

    /**
     * SHB开户数据
     **/
    @POST("/shcorec/personRegister?")
    Observable<SHBOPenccountAModel> getOpenAccount(@QueryMap Map<String, String> map);

    /**
     * SHB激活数据
     **/
    @POST("/shcorec/activateStockedUser?")
    Observable<SHBActivationAModel> getActivateStockedUser(@Query("userId") String map);


    /**
     * SHB修改手机号数据
     **/
    @POST("/shcorec/resetMobile?")
    Observable<SHBMobileChange> getResetMobile(@QueryMap Map<String, String> map);

    /**
     * SHB修改交易密码数据
     **/
    @POST("/shcorec/resetPassword?")
    Observable<SHBModel> getResetPassword(@QueryMap Map<String, String> map);

    /**
     * SHB评估报告数据数据
     **/
    @GET("/assess/userAssessResult?")
    Observable<SHBAssessModel> getUserAssessResult(@QueryMap Map<String, String> map);

    /**
     * SHB修改银行卡
     **/
    @POST("/shcorec/resetBankCard?")
    Observable<SHBReaetBankCard> getResetBankCard(@QueryMap Map<String, String> map);

    /**
     * SHB投资
     **/
    @POST("/shcorec/invest?")
    Observable<SHBUserInvest> getSHBInvest(@QueryMap Map<String, String> map);

    /**
     * SHB换银行卡结果
     **/

    @GET("/shtransactiondc/queryUserBankCardApply?")
    Observable<SHBBankResultInfo> getSHBBankCardInfo(@Query("uid") String map);

}

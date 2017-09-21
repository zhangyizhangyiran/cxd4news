package com.cxd.cxd4android.global;/*
 * Copyright (C) 2012 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.cxd.cxd4android.injection.component.ActivityContext;
import com.cxd.cxd4android.model.BindCardQueryBaseModel;
import com.cxd.cxd4android.model.GhbUserExtModel;
import com.cxd.cxd4android.model.LoginBaseModel;
import com.cxd.cxd4android.model.LoginModel;
import com.micros.utils.Q;
import com.micros.utils.S;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @version V1.0
 * @ClassName: LocalUserModel
 * @Description: 本地数据存储类
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午2:43:25
 */
@Singleton
public class LocalUserModel {

    private Context mContext;

    public static String FILE_NAME = "share_data";

    public static String DEFAULT_VALUE = "";

    public static String LOGIN_STATE_ONLINE = "ONLINE";

    public static String LOGIN_STATE_OFFLINE = "OFFLINE";

    public static String LOGIN_STATE_UNKNOW = "UNKNOW";


    /**
     * 工具类单例
     **/
    public static LocalUserModel userModel;

    public static LocalUserModel getInstance() {
        if (null == userModel) {
            userModel = new LocalUserModel();
        }

        return userModel;
    }

    public LocalUserModel() {
        this.mContext = BaseApplication.getInstance().mApp;
        S.FILE_NAME = FILE_NAME;

    }

    @Inject
    public LocalUserModel(@ActivityContext Context context) {

        this.mContext = context;
        S.FILE_NAME = FILE_NAME;
    }

    /**
     * 登录状态
     */
    public String getDebug() {
        return GetData("Debug", DEFAULT_VALUE);

    }

    public void setDebug(String Debug) {
        if (TextUtils.isEmpty(Debug)) {
            return;
        }
        SaveData("Debug", Debug);
    }

    /**
     * 登录状态
     */
    public String getLOGIN_STATE() {
        return GetData("LOGIN_STATE", DEFAULT_VALUE);

    }

    public void setLOGIN_STATE(String LOGIN_STATE) {
        if (TextUtils.isEmpty(LOGIN_STATE)) {
            return;
        }
        SaveData("LOGIN_STATE", LOGIN_STATE);
    }

    /**
     * 手势密码是否开启
     */
    public String getGestrueSwitch() {
        return GetData("GestrueSwitch", DEFAULT_VALUE);

    }

    public void setGestrueSwitch(String GestrueSwitch) {
        if (TextUtils.isEmpty(GestrueSwitch)) {
            return;
        }
        SaveData("GestrueSwitch", GestrueSwitch);
    }

    /**
     * 手势密码是否是第一次操作,yes 表示手势密码已解锁，no表示没有解锁
     */
    public String getGestrueIsFirst() {
        return GetData("GestrueIsFirst", DEFAULT_VALUE);

    }

    public void setGestrueIsFirst(String GestrueIsFirst) {
        if (TextUtils.isEmpty(GestrueIsFirst)) {
            return;
        }
        SaveData("GestrueIsFirst", GestrueIsFirst);
    }


    /**
     * IsFirstSaveLock 第一次设置手势密码 保存状态
     */
    public String getIsFirstSaveLock() {
        return GetData("savelock", DEFAULT_VALUE);

    }

    public void setIsFirstSaveLock(String IsFirstIn) {
        if (TextUtils.isEmpty(IsFirstIn)) {
            return;
        }
        SaveData("savelock", IsFirstIn);
    }

    /**
     * 保存设置的手势密码，这里就不做加密处理了，使用时应当这里做了加密再保存(读取手势密码)
     */
    public String getLockPattern() {
        return GetData("LockPattern", DEFAULT_VALUE);

    }

    public void setLockPattern(String LockPattern) {
        if (TextUtils.isEmpty(LockPattern)) {
            return;
        }
        SaveData("LockPattern", LockPattern);
    }

    /**
     * Header
     */
    public String getHeader() {
        return GetData("Header", DEFAULT_VALUE);
    }

    public void setHeader(String Header) {
        if (TextUtils.isEmpty(Header)) {
            return;
        }
        SaveData("Header", Header);
    }

    /**
     * 保存用户信息
     */
    public void SaveLoginData(LoginBaseModel model) {
        if (model == null) {
            return;
        }
        LoginModel result = model.result;
        setid(result.id);
        setHeader(result.token);
        setbirthday(result.birthday);
        setmobileNumber(result.mobileNumber);
        setrealname(result.realname);
        setidCard(result.idCard);
        setusername(result.username);
        setpassword(result.password);
        setYeepayAuth(result.realname_auth);
        setGhbPayAuth(result.ghb_realname_auth);
        setSHBAuth(result.sh_realname_auth);
//        Log.i("Auth--->",result.realname_auth+"="+result.ghb_realname_auth);
        //已经绑定手机号
        if (!Q.isEmpty(result.mobileNumber)) {

            setBindPhone("setBindPhone");
        }
        //已经实名认证
        if (!Q.isEmpty(result.idCard) && !Q.isEmpty(result.realname)) {
            setBindName("setBindName");
        }
    }

    /**
     * 保存银行卡信息
     */
    public void SaveBankCardData(BindCardQueryBaseModel model) {
        if (model == null) {
            return;
        }
        setcardNo(model.result.get(0).cardNo);
        setrealname(model.result.get(0).realname);
        setbankName(model.result.get(0).bankName);
        setidCard(model.result.get(0).idCard);
        setBankCardStatus(model.result.get(0).status);
        seticonUrl(model.result.get(0).iconUrl);
        setbaofoo_limit_des(model.result.get(0).baofoo_limit_des);
        setbaofoo_limit_money(model.result.get(0).baofoo_limit_money);
    }

    /**
     * 是否实名认证
     */
    public String getauth() {
        return GetData("auth", DEFAULT_VALUE);
    }

    public void setauth(String auth) {
        if (TextUtils.isEmpty(auth)) {
            return;
        }
        SaveData("auth", auth);
    }

    /**
     * 是否绑定银行卡
     */
    public String getBankCardStatus() {
        return GetData("BankCardStatus", DEFAULT_VALUE);
    }

    public void setBankCardStatus(String BankCardStatus) {
        if (TextUtils.isEmpty(BankCardStatus)) {
            return;
        }
        SaveData("BankCardStatus", BankCardStatus);
    }

    /**
     * 申请用户权限
     */
    public String getREAD_PHONE_STATE() {
        return GetData("READ_PHONE_STATE", DEFAULT_VALUE);

    }

    public void setREAD_PHONE_STATE(String READ_PHONE_STATE) {
        if (TextUtils.isEmpty(READ_PHONE_STATE)) {
            return;
        }
        SaveData("READ_PHONE_STATE", READ_PHONE_STATE);
    }


    /**
     * baofoo_limit_des
     */
    public String getbaofoo_limit_des() {
        return GetData("baofoo_limit_des", DEFAULT_VALUE);

    }

    public void setbaofoo_limit_des(String baofoo_limit_des) {
        if (TextUtils.isEmpty(baofoo_limit_des)) {
            return;
        }
        SaveData("baofoo_limit_des", baofoo_limit_des);
    }

    /**
     * baofoo_limit_money
     */
    public String getbaofoo_limit_money() {
        return GetData("baofoo_limit_money", DEFAULT_VALUE);

    }

    public void setbaofoo_limit_money(String baofoo_limit_money) {
        if (TextUtils.isEmpty(baofoo_limit_money)) {
            return;
        }
        SaveData("baofoo_limit_money", baofoo_limit_money);
    }

    /**
     * iconUrl
     */
    public String geticonUrl() {
        return GetData("iconUrl", DEFAULT_VALUE);

    }

    public void seticonUrl(String iconUrl) {
        if (TextUtils.isEmpty(iconUrl)) {
            return;
        }
        SaveData("iconUrl", iconUrl);
    }

    /**
     * 是否绑定手机
     */
    public String getBindPhone() {
        return GetData("BindPhone", DEFAULT_VALUE);
    }

    public void setBindPhone(String BindPhone) {
        if (TextUtils.isEmpty(BindPhone)) {
            return;
        }
        SaveData("BindPhone", BindPhone);
    }

    /**
     * BindName
     */
    public String getBindName() {
        return GetData("BindName", DEFAULT_VALUE);
    }

    public void setBindName(String BindName) {
        if (TextUtils.isEmpty(BindName)) {
            return;
        }
        SaveData("BindName", BindName);
    }

    /**
     * 返回当前程序版本名
     */
    public String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = BaseApplication.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

        }
        return versionName;
    }

    /**
     * 返回当前程序包名
     */
    public String getAppPackageName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = BaseApplication.getInstance().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            versionName = pi.packageName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {

        }
        return versionName;


    }


    /**
     * authCode
     */
    public String getauthCode() {
        return GetData("authCode", DEFAULT_VALUE);

    }

    public void setauthCode(String authCode) {
        if (TextUtils.isEmpty(authCode)) {
            return;
        }
        SaveData("authCode", authCode);
    }

    /**
     * mobileNumber
     */
    public String getmobileNumber() {
        return GetData("mobileNumber", DEFAULT_VALUE);

    }

    public void setmobileNumber(String mobileNumber) {
        if (TextUtils.isEmpty(mobileNumber)) {
            return;
        }
        SaveData("mobileNumber", mobileNumber);
    }

    /**
     * realname
     */
    public String getrealname() {
        return GetData("realname", DEFAULT_VALUE);

    }

    public void setrealname(String realname) {
        if (TextUtils.isEmpty(realname)) {
            return;
        }
        SaveData("realname", realname);
    }

    /**
     * email
     */
    public String getemail() {
        return GetData("email", DEFAULT_VALUE);

    }

    public void setemail(String email) {
        if (TextUtils.isEmpty(email)) {
            return;
        }
        SaveData("email", email);
    }

    /**
     * referrer
     */
    public String getreferrer() {
        return GetData("referrer", DEFAULT_VALUE);

    }

    public void setreferrer(String referrer) {
        if (TextUtils.isEmpty(referrer)) {
            return;
        }
        SaveData("referrer", referrer);
    }

    /**
     * status
     */
    public String getstatus() {
        return GetData("status", DEFAULT_VALUE);

    }

    public void setstatus(String status) {
        if (TextUtils.isEmpty(status)) {
            return;
        }
        SaveData("status", status);
    }

    /**
     * idCard
     */
    public String getidCard() {
        return GetData("idCard", DEFAULT_VALUE);

    }

    public void setidCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return;
        }
        SaveData("idCard", idCard);
    }

    /**
     * username
     */
    public String getusername() {
        return GetData("username", DEFAULT_VALUE);

    }

    public void setusername(String username) {
        if (TextUtils.isEmpty(username)) {
            return;
        }
        SaveData("username", username);
    }

    /**
     * id
     */
    public String getid() {
        return GetData("id", DEFAULT_VALUE);

    }

    public void setid(String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
        SaveData("id", id);
    }

    /**
     * registerTime
     */
    public String getregisterTime() {
        return GetData("registerTime", DEFAULT_VALUE);

    }

    public void setregisterTime(String registerTime) {
        if (TextUtils.isEmpty(registerTime)) {
            return;
        }
        SaveData("registerTime", registerTime);
    }

    /**
     * password
     */
    public String getpassword() {
        return GetData("password", DEFAULT_VALUE);

    }

    public void setpassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return;
        }
        SaveData("password", password);
    }

    /**
     * score
     */
    public String getscore() {
        return GetData("score", DEFAULT_VALUE);

    }

    public void setscore(String score) {
        if (TextUtils.isEmpty(score)) {
            return;
        }
        SaveData("score", score);
    }

    /**
     * sex
     */
    public String getsex() {
        return GetData("sex", DEFAULT_VALUE);

    }

    public void setsex(String sex) {
        if (TextUtils.isEmpty(sex)) {
            return;
        }
        SaveData("sex", sex);
    }

    /**
     * birthday
     */
    public String getbirthday() {
        return GetData("birthday", DEFAULT_VALUE);
    }

    public void setbirthday(String birthday) {
        if (TextUtils.isEmpty(birthday)) {
            return;
        }
        SaveData("birthday", birthday);
    }

    /**
     * balanceAvaliable
     */
    public String getbalanceAvaliable() {
        return GetData("balanceAvaliable", DEFAULT_VALUE);
    }

    public void setbalanceAvaliable(String balanceAvaliable) {
        if (TextUtils.isEmpty(balanceAvaliable)) {
            return;
        }
        SaveData("balanceAvaliable", balanceAvaliable);
    }

    /**
     * balance(账户总额)
     */
    public String getbalance() {
        return GetData("balance", DEFAULT_VALUE);
    }

    public void setbalance(String balance) {
        if (TextUtils.isEmpty(balance)) {
            return;
        }
        SaveData("balance", balance);
    }

    /**
     * cardNo
     */
    public String getcardNo() {
        return GetData("cardNo", DEFAULT_VALUE);
    }

    public void setcardNo(String cardNo) {
        if (TextUtils.isEmpty(cardNo)) {
            return;
        }
        SaveData("cardNo", cardNo);
    }

    /**
     * bankName
     */
    public String getbankName() {
        return GetData("bankName", DEFAULT_VALUE);
    }

    public void setbankName(String bankName) {
        if (TextUtils.isEmpty(bankName)) {
            return;
        }
        SaveData("bankName", bankName);
    }

    /**
     * 保存易宝-华兴账户类型
     */
    public String getThirdPayType() {
        return GetData("thirdPay", DEFAULT_VALUE);
    }

    public void setThirdPayType(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("thirdPay", data);
    }

    /**
     * 易宝是否实名认证
     */

    public String getYeepayAuth() {
        return GetData("yeepayAuth", DEFAULT_VALUE);
    }

    public void setYeepayAuth(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("yeepayAuth", data);
    }

    /**
     * 华兴是否实名认证
     */

    public String getGhbAuth() {
        return GetData("ghbpayAuth", DEFAULT_VALUE);
    }

    public void setGhbPayAuth(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("ghbpayAuth", data);
    }

    /**
     * 上海银行是否实名认证
     */

    public String getSHBAuth() {
        return GetData("shbAuth", DEFAULT_VALUE);
    }

    public void setSHBAuth(String shbAuth) {
        if (TextUtils.isEmpty(shbAuth)) {
            return;
        }
        SaveData("shbAuth", shbAuth);
    }

    /**
     * N未激活 Y已激活，未激活调用激活按钮，Y状态判断是否开户逻辑
     */

    public String getActive() {
        return GetData("active", DEFAULT_VALUE);
    }

    public void setActive(String active) {
        if (TextUtils.isEmpty(active)) {
            return;
        }
        SaveData("active", active);
    }

    /**
     * 华兴是否绑卡
     */

    public String getGhbBindCard() {
        return GetData("ghbbindcard", DEFAULT_VALUE);
    }

    public void setGhbBindCard(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("ghbbindcard", data);
    }

    /**
     * 保存华兴E账户信息
     */
    public void saveGhbEAccount(GhbUserExtModel model) {

        if (model == null) {
            return;
        }
        setGhbBindCard(model.getResult().getBindCard()); // 是否绑卡状态 N未绑卡  Y已经绑卡
        setGhbEUserName(model.getResult().getGhbAccountName());  // E账户姓名
        setGhbEBranch(model.getResult().getGhbBranch());    // 银行名称
        setGhbECardNumber(model.getResult().getGhbAccount());   // E账户
        setEIdCard(model.getResult().getIdCard()); //保存身份证信息
        setWithdrawTimes(model.getResult().getWithdrawTimes()); //提现次数
    }


    /**
     * 华兴E账户用户名
     */

    public String getGhbEUserName() {
        return GetData("ghbeuser", DEFAULT_VALUE);
    }

    public void setGhbEUserName(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("ghbeuser", data);
    }

    /**
     * 华兴E账户开户地址
     */
    public String getGhbEBranch() {
        return GetData("ghbebranch", DEFAULT_VALUE);
    }

    public void setGhbEBranch(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("ghbebranch", data);
    }

    /**
     * 华兴E账户卡号
     */
    public String getGhbECardNumber() {
        return GetData("ghbecard", DEFAULT_VALUE);
    }

    public void setGhbECardNumber(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("ghbecard", data);
    }

    /**
     * IdCard;  // 身份证号
     */
    public String getEIdCard() {
        return GetData("idcard", DEFAULT_VALUE);
    }

    public void setEIdCard(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("idcard", data);
    }

    /**
     * WithdrawTimes 提现次数
     */
    public String getWithdrawTimes() {
        return GetData("widtimes", DEFAULT_VALUE);
    }

    public void setWithdrawTimes(String data) {
        if (TextUtils.isEmpty(data)) {
            return;
        }
        SaveData("widtimes", data);
    }

    /*******************************************************************************************************************/

    public void SaveData(String key, String value) {
        if (mContext != null)
            S.put(mContext, key, value);
    }

    public String GetData(String key, String defaultvalue) {
        return (String) S.get(mContext, key, defaultvalue);
    }

    public Map<String, ?> getAll() {
        return S.getAll(mContext);
    }

    public boolean contains(String key) {
        return S.contains(mContext, key);
    }

    public void remove(String key) {
        S.remove(mContext, key);
    }

    public void clear() {
        S.clear(mContext);
    }

}


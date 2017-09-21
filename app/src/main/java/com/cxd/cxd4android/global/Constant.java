/*
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
package com.cxd.cxd4android.global;

/**
 * @version V1.0
 * @ClassName: Constant
 * @Description: 全局常量
 * @Author：Chengel_HaltuD
 * @Date 2015-5-30 下午2:43:07
 */
public class Constant {

//	正式
//    public static String WEBBASEAPI = "https://api.51cxd.cn/";

    public static String WEBBASEAPI = "http://dev.api.51cxd.cn/";
    /**
     * 网络请求返回结果:
     * status分为以下状态:
     * 200: 调用成功
     * 500: 预期失败
     * 404: 系统错误
     * 403: 重复登录
     */
    public static String STATUS_SUCCESS = "200";
    public static String STATUS_FAILED = "500";
    public static String STATUS_ERROR = "404";
    public static String STATUS_UNLOGIN = "403";



    /**
     * 风险评估判断
     **/
    public static String SHB_PING_GUO = "";

    /**
     * 上海银行提现
     **/

    public static String SHB_TIXIAN = "";

    /**
     * 上海银行充值
     **/
    public static String SHB_CHONGZHI = "";

    /**
     * Intent跳转
     **/
    public static String INTENT_JUMP = "";

    /**
     * Fragment跳转
     **/
    public static String FRAGMENT_JUMP = "";

    /**
     * 点击弹出对话框跳转
     **/
    public static String DIALOG_JUMP = "";

    /**
     * 手势密码key
     **/
    public static final String GESTURE_KEY = "gesture_key";
    /**
     * 易宝跳转跳转
     **/
    public static String actionUrl = "";
    /**
     * 易宝过滤日志
     **/
    public static String fillterStr = "yeepay";


    /**
     * 服务:基本信息
     **/
    public static String essential_information = "http://cxd-asset.oss-cn-beijing.aliyuncs.com/activities/H5/2017-05/basic_information.html";

    /**
     * 服务:治理信息
     **/
    public static String administer_information = "http://cxd-asset.oss-cn-beijing.aliyuncs.com/activities/H5/2017-05/Management_information.html";

    /**
     * 服务:平台报告
     **/
    public static String platform_information = "http://cxd-asset.oss-cn-beijing.aliyuncs.com/activities/H5/2017-05/Platform_content.html";
    /**
     * 服务:新手指南
     **/
    public static String se_ll_help_Guide = "http://cxd-asset.oss-cn-beijing.aliyuncs.com/activities/H5/2017-05/Newbie_guide.html";
    /**
     * 服务:重大事项
     **/
    public static String matter_information = "http://cxd-asset.oss-cn-beijing.aliyuncs.com/activities/H5/2017-05/listPage.html?cateName=event";

    /**
     * 服务: 政策法规
     **/

    public static String Person_information = "http://cxd-asset.oss-cn-beijing.aliyuncs.com/activities/H5/2017-05/listPage.html?cateName=statute";

    /**
     * 服务: 网贷知识
     **/

    public static String Legislation_information = "http://cxd-asset.oss-cn-beijing.aliyuncs.com/activities/H5/2017-05/listPage.html?cateName=knowledge";
    /**
     * 推荐：h5URL
     */

    public static String HShow = "http://cxd-asset.oss-cn-beijing.aliyuncs.com/activities/H5/2017-06-26/activeImg/index.html";
    /**
     * 服务:关于我们
     **/
    public static String help_about_us = WEBBASEAPI + "/h5c/help_about_us";
    /**
     * 服务:帮助中心:新手指引
     **/
    public static String help_new_user = WEBBASEAPI + "/h5c/help_new_user";
    /**
     * 服务:帮助中心:投资问题
     **/
    public static String help_invest = WEBBASEAPI + "/h5c/help_invest";
    /**
     * 服务:帮助中心:登录/注册
     **/
    public static String help_reg = WEBBASEAPI + "/h5c/help_reg";
    /**
     * 服务:帮助中心:提现/充值
     **/
    public static String help_withdraw = WEBBASEAPI + "/h5c/help_withdraw";
    /**
     * 服务:帮助中心:安全问题
     **/
    public static String help_safe = WEBBASEAPI + "/h5c/help_safe";
    /**
     * 服务:帮助中心:积分规则
     **/
    public static String integral_rules = WEBBASEAPI + "/h5c/integral_rules";
    /**
     * 2.4.11-2进入抽奖页面
     **/
    public static String pointc_pointLuckDraw = WEBBASEAPI + "/pointc/pointLuckDraw?user_id=";

    /**
     * 我:我的投资
     **/
    public static String my_invest = "";
    /**
     * 我:我的转让
     **/
    public static String my_transfer = "";
    /**
     * 我:我的账户
     **/
    public static String my_account = "";
    /**
     * 我:我的账单
     **/
    public static String my_bill = "";
    /**
     * 我:回款日历
     **/
    public static String my_calendar = "";
    /**
     * 我:我的推荐
     **/
    public static String my_recommended = "";
    /**
     * 我:我的积分
     **/
    public static String my_piont = "";
    /**
     * 我:我的红包
     **/
    public static String my_hongbao = "";
    /**
     * 我:会员福利
     **/
    public static String my_fuli = "";
    /**
     * app首页中间推荐展现形式
     **/
//	public static String boutMiddlePic = "";
//	public static String boutMiddlePicUrl = "";
    public static String boutMiddleType = "";
    /**
     * app积分商城展现形式
     **/
    //public static String myPointPic = "";
//	public static String myPointPicUrl = "";
    public static String myPointType = "";
    /*展示类型*/
    public static final String H5 = "h5";
    public static final String NATIVE = "native";

    /**
     * 账户类型
     **/
    public static final String ACCOUNT_YEEPAY = "yeepay";
    public static final String ACCOUNT_GHB = "ghb";
    public static final String ACCOUNT_SHB = "sh";

    /*.H5跳转页面
    实名认证 realnameAuth(参数).GuidanceController(String str)
    绑卡 bindCard(参数).GuidanceController(String str)
    充值 recharge(参数).GuidanceController(String str)
    提现 withdraw(参数).GuidanceController(String str)
    /ghbh5c/promptJump
    参数 type：realnameAuth，bindCard，recharge，withdraw*/
    public static final String REALNAMEAUTH = "realnameAuth";
    public static final String BINDCARD = "bindCard";
    public static final String RECHARGE = "recharge";
    public static final String WITHDRAW = "withdraw";
    public static final String GHB_H5_PROMPTJUMP = WEBBASEAPI + "/ghbh5c/promptJump?"; // 绑卡引导

    public static String guideType = "";//判断Js唯一标示

    /**
     * 易宝跳转
     *
     * @param sign
     * @param reqContent
     * @param actionUrl
     * @return
     */
    public static String createAutoSubmitForm(String sign, String reqContent, String actionUrl) {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>跳转......</title>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        sb.append("</head>");
        sb.append("<body>");
        sb.append("<form action=\"" + actionUrl + "\" method=\"post\" id=\"frm1\" style=\"display:none;\">");
        sb.append("<input type=\"hidden\" name=\"sign\" value=\"" + sign + "\">");
        sb.append("<input type=\"hidden\" name=\"req\" value=reqContent>");
        sb.append("</form>");
        sb.append("<script type=\"text/javascript\">document.getElementById(\"frm1\").submit()</script>");
        sb.append("</body>");
        String sbStr = sb.toString().replaceAll("\"", "'").replaceAll("reqContent", "\"" + reqContent + "\"");
        return sbStr;
    }

}

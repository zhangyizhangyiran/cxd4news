package com.cxd.cxd4android.model;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class LoginModel{

    /**
     * birthday
     * sex              性别 男 女
     * score
     * password
     * registerTime
     * id			    用户ID
     * username	        用户姓名
     * token
     * idCard
     * status
     * referrer
     * email
     * realname
     * mobileNumber
     */
    public String birthday = "";

    public String sex = "";

    public String score = "";

    public String password = "";

    public String registerTime = "";

    public String id = "";

    public String username = "";

    public String token = "";

    public String idCard = "";

    public String status = "";

    public String referrer = "";

    public String email = "";

    public String realname = "";

    public String mobileNumber = "";

    /**
     * 易宝是否实名认证
     * NOT 未认证
     * CHECKED 已认证
     */
    public String realname_auth;

    /**
     * 华兴是否实名认证
     * NOT 未认证
     * CHECKED 已认证
     */
    public String ghb_realname_auth;

    /**
     * 上海银行是否实名认证
     * NOT 未认证
     * CHECKED 已认证
     */
    public String sh_realname_auth;

}

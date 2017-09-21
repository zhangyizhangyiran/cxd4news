package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class IdentityModel implements Serializable{

    /**
     * callbackUrl          除投资(购买)以外易宝回调路径
     * isIntercept          true 拦截易宝  false 不拦截易宝
     */
    public String sign = "";

    public String reqContent = "";

    public String actionUrl = "";

    public String callbackUrl = "";

    public String isIntercept = "";

}

package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/21 11:52
 * version：V1.0
 */
public class YeePayModel implements Serializable{

    /**
     * 头部显示标题
     */
    public String title = "";
    /**
     * 跳转链接
     */
    public String url = "";
    /**
     * 是否拦截链接
     */
    public String isIntercept = "";
    /**
     * 回调链接
     */
    public String callbackUrl = "";
}

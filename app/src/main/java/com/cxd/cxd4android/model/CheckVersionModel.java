package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class CheckVersionModel implements Serializable{

    /**
     * description
     * id
     * lastversion
     * url
     * isUpdate         1：更新 2：不更新
     *
     */
    public String description = "";

    public String id = "";

    public String lastversion = "";

    public String url = "";

    public String isUpdate = "";

}

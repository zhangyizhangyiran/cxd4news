package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class BannerModel implements Serializable{

    /**
     * imgRootUrl
     * location
     * title
     * url
     */
    public String imgRootUrl = "";

    public String location = "";

    public String title = "";

    public String url = "";

    @Override
    public String toString() {
        return "BannerModel{" +
                "imgRootUrl='" + imgRootUrl + '\'' +
                ", location='" + location + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

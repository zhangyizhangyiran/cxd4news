package com.cxd.cxd4android.shbbank.model;

import java.io.Serializable;

/**
 * Created by chengelhaltud on 17/9/2.
 */

public class WebViewModel implements Serializable{

    private String title;
    private String url;
    private String type;

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    private String identify;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

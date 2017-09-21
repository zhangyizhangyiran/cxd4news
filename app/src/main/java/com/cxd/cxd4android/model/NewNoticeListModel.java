package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class NewNoticeListModel implements Serializable{

    public String body = "";

    public String keywords = "";

    public String status = "";

    public String imgRoot = "";

    public String last_modify_user = "";

    public String node_type = "";

    public String creator = "";

    public String id = "";

    public String title = "";

    public String update_time = "";

    public String description = "";

    public String name = "";

    public String subtitle = "";

    public String create_time = "";

    public String language = "";

    public String thumb = "";

    public String node_url = "";

    @Override
    public String toString() {
        return "NewNoticeListModel{" +
                "body='" + body + '\'' +
                ", keywords='" + keywords + '\'' +
                ", status='" + status + '\'' +
                ", imgRoot='" + imgRoot + '\'' +
                ", last_modify_user='" + last_modify_user + '\'' +
                ", node_type='" + node_type + '\'' +
                ", creator='" + creator + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", update_time='" + update_time + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", create_time='" + create_time + '\'' +
                ", language='" + language + '\'' +
                ", thumb='" + thumb + '\'' +
                ", node_url='" + node_url + '\'' +
                '}';
    }
}

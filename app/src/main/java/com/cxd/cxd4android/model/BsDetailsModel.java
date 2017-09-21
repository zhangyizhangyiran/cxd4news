package com.cxd.cxd4android.model;

import java.io.Serializable;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: 礼品详情页面
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/25 16:24
 */
public class BsDetailsModel implements Serializable {

    private ResultBean result;


    private String status;
    private String msg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean implements Serializable{
        private int position;
        private String createTime;
        private String imgpath4;
        private String imgpath3;
        private String updateTime;
        private String imgpath2;
        private String imgpath1;
        private String type;
        private String id;
        private String userPoint;
        private int price;
        private String inventory;
        private String integration;
        private String name;
        private String active;
        private String activityType;
        private String detailImgpath;
        private List<String> picList;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getImgpath4() {
            return imgpath4;
        }

        public void setImgpath4(String imgpath4) {
            this.imgpath4 = imgpath4;
        }

        public String getImgpath3() {
            return imgpath3;
        }

        public void setImgpath3(String imgpath3) {
            this.imgpath3 = imgpath3;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getImgpath2() {
            return imgpath2;
        }

        public void setImgpath2(String imgpath2) {
            this.imgpath2 = imgpath2;
        }

        public String getImgpath1() {
            return imgpath1;
        }

        public void setImgpath1(String imgpath1) {
            this.imgpath1 = imgpath1;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserPoint() {
            return userPoint;
        }

        public void setUserPoint(String userPoint) {
            this.userPoint = userPoint;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getInventory() {
            return inventory;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
        }

        public String getIntegration() {
            return integration;
        }

        public void setIntegration(String integration) {
            this.integration = integration;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getActivityType() {
            return activityType;
        }

        public void setActivityType(String activityType) {
            this.activityType = activityType;
        }

        public String getDetailImgpath() {
            return detailImgpath;
        }

        public void setDetailImgpath(String detailImgpath) {
            this.detailImgpath = detailImgpath;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }
    }
}

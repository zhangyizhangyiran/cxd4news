package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName:
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/30 15:44
 */
public class PointHistoryResultModel implements Serializable{


    /**
     * createTime : 2016-05-30
     * investId : 27d641a52c514a7c9726983eb48f2915
     * mode : invest
     * operateType : minus
     * point : -40
     * productId : 27d641a52c514a7c9726983eb48f2915
     * productName : 洗发膏
     * surplusPoint : 67874
     * uphId : 25507000b6654969a77f62718d5b948e
     * userId : sssss
     */

    private boolean isclicked = false;
    private String createTime;
    private String investId;
    private String mode;
    private String operateType;
    private int point;
    private String productId;
    private String productName;
    private int surplusPoint;
    private String uphId;
    private String userId;
    private String type;
    public boolean getisclicked() {
        return isclicked;
    }

    public void setisclicked(boolean isclicked) {
        this.isclicked = isclicked;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInvestId() {
        return investId;
    }

    public void setInvestId(String investId) {
        this.investId = investId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSurplusPoint() {
        return surplusPoint;
    }

    public void setSurplusPoint(int surplusPoint) {
        this.surplusPoint = surplusPoint;
    }

    public String getUphId() {
        return uphId;
    }

    public void setUphId(String uphId) {
        this.uphId = uphId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

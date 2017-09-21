package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName: 获取产品发货信息
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/30 17:53
 */
public class UphAddrDataModel implements Serializable{


    /**
     * expressNumber : 无
     * status : delivery_complete
     * expressCompany : 无
     * productName : 未中奖
     */

    private ResultBean result;
    /**
     * result : {"expressNumber":"无","status":"delivery_complete","expressCompany":"无","productName":"未中奖"}
     * status : 200
     * msg : 成功
     */

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
        private String expressNumber;
        private String status;
        private String expressCompany;
        private String productName;

        public String getExpressNumber() {
            return expressNumber;
        }

        public void setExpressNumber(String expressNumber) {
            this.expressNumber = expressNumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExpressCompany() {
            return expressCompany;
        }

        public void setExpressCompany(String expressCompany) {
            this.expressCompany = expressCompany;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }
}

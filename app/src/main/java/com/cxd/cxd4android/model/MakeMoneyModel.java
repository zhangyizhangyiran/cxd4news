package com.cxd.cxd4android.model;

/**
 * Created by zhangyi on 17/4/10.
 */

public class MakeMoneyModel {

    /**
     * result : {"assess_describe":"为了便于您了解自身的风险承受能力，选择合适的投资产品和服务，请您填写以下风险承受能力评估问卷。","assess_title":"个人投资者风险承受能力评估问卷","security_alert":"风险提示：网络借贷投资可能获得比较高的投资收益，但也存在比较大的投资风险，请您根据自身的风险承受能力，审慎作出投资决定。","status":"200"}
     * msg : SUCCESS
     * status : 200
     */

    private ResultBean result;
    private String msg;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * assess_describe : 为了便于您了解自身的风险承受能力，选择合适的投资产品和服务，请您填写以下风险承受能力评估问卷。
         * assess_title : 个人投资者风险承受能力评估问卷
         * security_alert : 风险提示：网络借贷投资可能获得比较高的投资收益，但也存在比较大的投资风险，请您根据自身的风险承受能力，审慎作出投资决定。
         * status : 200
         */

        private String assess_describe = "";
        private String assess_title = "";
        private String security_alert = "";
        private String status = "";

        public String getAssess_describe() {
            return assess_describe;
        }

        public void setAssess_describe(String assess_describe) {
            this.assess_describe = assess_describe;
        }

        public String getAssess_title() {
            return assess_title;
        }

        public void setAssess_title(String assess_title) {
            this.assess_title = assess_title;
        }

        public String getSecurity_alert() {
            return security_alert;
        }

        public void setSecurity_alert(String security_alert) {
            this.security_alert = security_alert;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}

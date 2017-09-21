package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName:
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/30 18:15
 */
public class InvestModel implements Serializable {


    /**
     * rate : 20
     * investMoney : 5000000
     * allInterest : 504109.6
     * type : 月
     * deadline : 6
     * loanName : 5月25号14点半测试
     */

    private ResultBean result;
    /**
     * result : {"rate":20,"investMoney":5000000,"allInterest":504109.6,"type":"月","deadline":6,"loanName":"5月25号14点半测试"}
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
        private String rate;
        private String investMoney;
        private String allInterest;
        private String type;
        private String deadline;
        private String loanName;

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getInvestMoney() {
            return investMoney;
        }

        public void setInvestMoney(String investMoney) {
            this.investMoney = investMoney;
        }

        public String getAllInterest() {
            return allInterest;
        }

        public void setAllInterest(String allInterest) {
            this.allInterest = allInterest;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getLoanName() {
            return loanName;
        }

        public void setLoanName(String loanName) {
            this.loanName = loanName;
        }
    }
}

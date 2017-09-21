package com.cxd.cxd4android.model;

import java.util.List;

/**
 * Created by zhangyi on 17/2/14.
 * 红包数据
 */

public class RedPaperModel {
    /**
     * msg : SUCCESS
     * result : [{"coupon_amount":10,"coupon_describe":"单笔投资1000元激活 \r\n适用于所有标","coupon_time":1484549489000,"coupon_user_id":100001,"invest_id":"2","is_can_use":"YES","overdue_time":1487141489000,"send_user_id":"qaz010102","status":"init","user_id":"qaz010102"},{"coupon_amount":20,"coupon_describe":"单笔投资2000元激活 \r\n适用于所有标","coupon_time":1484549489000,"coupon_user_id":100002,"invest_id":"3","is_can_use":"NO","overdue_time":1487141489000,"send_user_id":"qaz010102","status":"init","user_id":"qaz010102"},{"coupon_amount":50,"coupon_describe":"单笔投资3000元激活 \r\n适用于月标","coupon_time":1484549489000,"coupon_user_id":100003,"invest_id":"4","is_can_use":"NO","overdue_time":1487141489000,"send_user_id":"qaz010102","status":"init","user_id":"qaz010102"},{"coupon_amount":88,"coupon_describe":"单笔投资10000元激活 \r\n适用于月标","coupon_time":1484549489000,"coupon_user_id":100004,"invest_id":"5","is_can_use":"NO","overdue_time":1487141489000,"send_user_id":"qaz010102","status":"init","user_id":"qaz010102"}]
     * status : 200
     */

    private String msg;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * coupon_amount : 10
         * coupon_describe : 单笔投资1000元激活
         * 适用于所有标
         * coupon_time : 1484549489000
         * coupon_user_id : 100001
         * invest_id : 2
         * is_can_use : YES
         * overdue_time : 1487141489000
         * send_user_id : qaz010102
         * status : init
         * user_id : qaz010102
         */

        private int coupon_amount;
        private String coupon_describe;
        private long coupon_time;
        private String coupon_user_id;
        private String invest_id;
        private String is_can_use;
        private long overdue_time;
        private String send_user_id;
        private String status;
        private String user_id;
        private String loan_type;

        public String getLoan_type() {
            return loan_type;
        }

        public void setLoan_type(String loan_type) {
            this.loan_type = loan_type;
        }

        public int getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(int coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public String getCoupon_describe() {
            return coupon_describe;
        }

        public void setCoupon_describe(String coupon_describe) {
            this.coupon_describe = coupon_describe;
        }

        public long getCoupon_time() {
            return coupon_time;
        }

        public void setCoupon_time(long coupon_time) {
            this.coupon_time = coupon_time;
        }

        public String getCoupon_user_id() {
            return coupon_user_id;
        }

        public void setCoupon_user_id(String coupon_user_id) {
            this.coupon_user_id = coupon_user_id;
        }

        public String getInvest_id() {
            return invest_id;
        }

        public void setInvest_id(String invest_id) {
            this.invest_id = invest_id;
        }

        public String getIs_can_use() {
            return is_can_use;
        }

        public void setIs_can_use(String is_can_use) {
            this.is_can_use = is_can_use;
        }

        public long getOverdue_time() {
            return overdue_time;
        }

        public void setOverdue_time(long overdue_time) {
            this.overdue_time = overdue_time;
        }

        public String getSend_user_id() {
            return send_user_id;
        }

        public void setSend_user_id(String send_user_id) {
            this.send_user_id = send_user_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
//
//    /**
//     * msg : SUCCESS
//     * item : [{"coupon_amount":10,"coupon_describe":"单笔投资1000元激活 \r\n适用于所有标","coupon_time":1484549489000,"coupon_user_id":100001,"invest_id":"2","is_can_use":"YES","overdue_time":1487141489000,"send_user_id":"qaz010102","status":"init","user_id":"qaz010102"},{"coupon_amount":20,"coupon_describe":"单笔投资2000元激活 \r\n适用于所有标","coupon_time":1484549489000,"coupon_user_id":100002,"invest_id":"3","is_can_use":"NO","overdue_time":1487141489000,"send_user_id":"qaz010102","status":"init","user_id":"qaz010102"},{"coupon_amount":50,"coupon_describe":"单笔投资3000元激活 \r\n适用于月标","coupon_time":1484549489000,"coupon_user_id":100003,"invest_id":"4","is_can_use":"NO","overdue_time":1487141489000,"send_user_id":"qaz010102","status":"init","user_id":"qaz010102"},{"coupon_amount":88,"coupon_describe":"单笔投资10000元激活 \r\n适用于月标","coupon_time":1484549489000,"coupon_user_id":100004,"invest_id":"5","is_can_use":"NO","overdue_time":1487141489000,"send_user_id":"qaz010102","status":"init","user_id":"qaz010102"}]
//     * status : 200
//     */
//
//    private String msg;
//    private String status;
//    private List<ItemBean> item;
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public List<ItemBean> getItem() {
//        return item;
//    }
//
//    public void setItem(List<ItemBean> item) {
//        this.item = item;
//    }
//
//    public static class ItemBean {
//        /**
//         * coupon_amount : 10
//         * coupon_describe : 单笔投资1000元激活
//         适用于所有标
//         * coupon_time : 1484549489000
//         * coupon_user_id : 100001
//         * invest_id : 2
//         * is_can_use : YES
//         * overdue_time : 1487141489000
//         * send_user_id : qaz010102
//         * status : init
//         * user_id : qaz010102
//         */
//
//        private int coupon_amount;
//        private String coupon_describe;
//        private long coupon_time;
//        private int coupon_user_id;
//        private String invest_id;
//        private String is_can_use;
//        private long overdue_time;
//        private String send_user_id;
//        private String status;
//        private String user_id;
//
//        public int getCoupon_amount() {
//            return coupon_amount;
//        }
//
//        public void setCoupon_amount(int coupon_amount) {
//            this.coupon_amount = coupon_amount;
//        }
//
//        public String getCoupon_describe() {
//            return coupon_describe;
//        }
//
//        public void setCoupon_describe(String coupon_describe) {
//            this.coupon_describe = coupon_describe;
//        }
//
//        public long getCoupon_time() {
//            return coupon_time;
//        }
//
//        public void setCoupon_time(long coupon_time) {
//            this.coupon_time = coupon_time;
//        }
//
//        public int getCoupon_user_id() {
//            return coupon_user_id;
//        }
//
//        public void setCoupon_user_id(int coupon_user_id) {
//            this.coupon_user_id = coupon_user_id;
//        }
//
//        public String getInvest_id() {
//            return invest_id;
//        }
//
//        public void setInvest_id(String invest_id) {
//            this.invest_id = invest_id;
//        }
//
//        public String getIs_can_use() {
//            return is_can_use;
//        }
//
//        public void setIs_can_use(String is_can_use) {
//            this.is_can_use = is_can_use;
//        }
//
//        public long getOverdue_time() {
//            return overdue_time;
//        }
//
//        public void setOverdue_time(long overdue_time) {
//            this.overdue_time = overdue_time;
//        }
//
//        public String getSend_user_id() {
//            return send_user_id;
//        }
//
//        public void setSend_user_id(String send_user_id) {
//            this.send_user_id = send_user_id;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public String getUser_id() {
//            return user_id;
//        }
//
//        public void setUser_id(String user_id) {
//            this.user_id = user_id;
//        }
//    }
}

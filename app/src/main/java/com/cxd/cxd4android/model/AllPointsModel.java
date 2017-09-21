package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName:总积分
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/13 17:57
 */
public class AllPointsModel implements Serializable{

    /**
     * status		分为以下状态:
     * 				200: 调用成功
     * 				500: 预期失败
     * 				404: 系统错误
     *
     * msg			结果信息/错误信息
     * result		数据
     */
    private ResultBean result;
    /**
     * result : {"point":"0"}
     * status : ​200
     * msg : 获取用户总积分成功
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

    public static class ResultBean {
        private String point;

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }
    }

/*    public String msg = "";

    public String status = "";

    public BaseAllPointModel result ;*/

}

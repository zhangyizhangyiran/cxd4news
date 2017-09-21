package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/19 15:17
 * version：V1.0
 */
public class UserInvestsInfoModel  implements Serializable {

    /**
     * nextCollectMoney      下期还款额
     * nextRepayDate         下期还款日
     * waitingCollectMoney   待收本息
     * totalInterest        总利息
     * totalAmount          总本息
     */
    public String nextCollectMoney = "";

    public String nextRepayDate = "";

    public String waitingCollectMoney = "";

    public String totalInterest = "";

    public String totalAmount = "";
}

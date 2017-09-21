package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class WithdrawModel implements Serializable{

    /**
     * fee          提现费用
     * toMoney      传给后台的钱数(传给易宝)
     * toShowMoney  实际提现
     */
    public String fee = "";
    public String toMoney = "";
    public String toShowMoney = "";

}

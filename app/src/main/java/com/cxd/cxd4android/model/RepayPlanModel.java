package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/11 17:38
 * version：V1.0
 */
public class RepayPlanModel implements Serializable{

    /**
     * id           还款计划ID
     * period       当前还款第几期
     * time         还款时间
     * repayDay     还款日
     * corpus       本金
     * interest     利息
     * defaultInterest  罚息（逾期利息）
     * length       本期长度
     * fee          手续费（从利息中扣除，给系统）
     * investId
     * status       状态
     */
    public String id = "";

    public String period = "";

    public String time = "";

    public String repayDay = "";

    public String corpus = "";

    public String interest = "";

    public String defaultInterest = "";

    public String length = "";

    public String fee = "";

    public String status = "";

    public String investId = "";

}

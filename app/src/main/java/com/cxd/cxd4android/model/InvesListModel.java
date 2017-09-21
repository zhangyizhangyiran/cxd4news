package com.cxd.cxd4android.model;

import java.io.Serializable;
import java.util.List;

import static android.R.attr.type;

/**
 * ClassName:
 * Description：
 * Author：Chengel_HaltuD
 * Date：2015/12/16 10:34
 * version：V1.0
 */
public class InvesListModel implements Serializable {

    /**
     * id               借款ID
     * name             借款名称
     * userId           借款人
     * description      借款描述
     * loanPurpose      借款用途
     * money            实际借款金额
     * loanMoney        预计的借款金额
     * cardinalNumber   投资递增金额
     * rate             项目利率
     * minInvestMoney   最小投资金额
     * repayType        还款方式
     * progress			进度
     * remainTime		剩余时间
     * deadline			投标记录
     * exceptTime		预计计息时间
     * businessType		项目类型
     * status			项目状态
     * investTime		投资时间
     * unit				项目期限单位
     * remainMoney		剩余可投金额
     * trusteeshipType  项目标示(易宝,华兴)
     * notInvestMsg     非易宝用户点击易宝项目(提示语)
     * <p>
     * listLoanInfoList         项目详情
     * loanInfoPicUrls          项目详情图片
     * listWindControlList       项目风控详情
     * loanWindControlPicUrls   项目风控图片
     * remaininTime      剩余时间，类型long
     * custodySave        存管银行
     * interestType     计息方式：
     * loanType  还款方式：
     * verifyTime 募集起开始时间
     * repayGuarantee 风控的还款保障
     * overdue_money 逾期金额
     * overdue_times 逾期次数
     * realName  真实姓名
     * idCard  身份证号
     * typedesc 还款方式文字描述
     * type 还款方式类型
     * typeUrl 还款方式为等额本息 html5页面
     */

    public String typeUrl = "";
    public String type = "";
    public String typeDesc = "";
    public String loanLabel = "";
    public String addRate = "";
    public String exceptTimeApp = "";

    public Long remaininTime;
    public String middleText = "马上赚钱";

    public String overdue_times = "";
    public String overdue_money = "";
    public String repayGuarantee = "";
    public String realName = "";
    public String idCard = "";
    public String id = "";
    public String custodySave = "";
    public String interestType = "";
    public String loanType = "";
    public String verifyTime = "";

    public String name = "";

    public String userId = "";

    public String description = "";

    public String loanPurpose = "";

    public String money = "";

    public String loanMoney = "";

    public String cardinalNumber = "";

    public String rate = "";

    public String minInvestMoney = "";

    public String repayType = "";

    public String progress = "";

    public String remainTime = "";

    public String deadline = "";

    public String exceptTime = "";

    public String businessType = "";

    public String status = "";

    public String investTime = "";

    public String unit = "";

    public String remainMoney = "";

    public String trusteeshipType = "";

    public String notInvestMsg = "";

    public List<InvesListLoanInfoListModel> listLoanInfoList;

    public List<InvesListLoanPicUrlListModel> loanInfoPicUrls;

    public List<InvesListLoanInfoListModel> listWindControlList;

    public List<InvesListLoanPicUrlListModel> loanWindControlPicUrls;

    public String investMoney = "";


}

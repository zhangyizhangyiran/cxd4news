package com.cxd.cxd4android.model;

import java.util.List;

/**
 * @version V1.0
 * @ClassName: 积分明细
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/30 15:43
 */
public class PointHistoryModel {
    /**
     * 结果信息/错误信息
     */
    public String msg = "";
    /**
     * status分为以下状态:
     * 200: 调用成功
     * 500: 预期失败
     * 404: 系统错误
     */
    public String status = "";
    /**
     * 数据
     */
    public List<PointHistoryResultModel> result ;
}

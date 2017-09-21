package com.cxd.cxd4android.model;

import java.io.Serializable;

/**
 * @version V1.0
 * @ClassName: hui回款日历
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/12 11:36
 */
public class BackCalendarModel implements Serializable {

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
    public CalendarList result;

}

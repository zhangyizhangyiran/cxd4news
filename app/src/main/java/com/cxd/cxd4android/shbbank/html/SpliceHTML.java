package com.cxd.cxd4android.shbbank.html;

/**
 * Created by chengelhaltud on 17/8/30.
 */

public class SpliceHTML {

    /**
     * 上海银行跳转
     */
    public static String createAutoSubmitForm(String url, String serviceName, String platformNo,String reqData,String keySerial,String sign) {
        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>跳转......</title>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        sb.append("</head>");

        sb.append("<body>");
        sb.append("<form action=\"" + url + "\" method=\"post\" id=\"frm1\" style=\"display:none;\">");
        sb.append("<input type=\"text\" class=\"form-control\" id=\"serviceName\" name=\"serviceName\" value=\""+serviceName+"\"></input>");
        sb.append("<input type=\"text\" class=\"form-control\" id=\"platformNo\" name=\"platformNo\" value=\""+platformNo+"\"></input>");
        sb.append("<input type=\"text\" class=\"form-control\" id=\"userDevice\" name=\"userDevice\" value=\"PC\"></input>");
        sb.append("<textarea class=\"form-control\" id=\"reqData\" name=\"reqData\" rows=\"3\">"+reqData+"</textarea>");
        sb.append("<input type=\"text\" class=\"form-control\" id=\"keySerial\" name=\"keySerial\" value=\""+keySerial+"\"></input>");
        sb.append("<textarea class=\"form-control\" id=\"sign\" name=\"sign\" rows=\"5\">"+sign+"</textarea>");
        sb.append("</form>");
        sb.append("<script type=\"text/javascript\">document.getElementById(\"frm1\").submit()</script>");
        sb.append("</body>");

        String sbStr = sb.toString().replaceAll("\"", "'");
        return sbStr;
    }
}

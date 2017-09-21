package com.cxd.cxd4android.shbbank.html;

/**
 * Created by chengelhaltud on 17/8/30.
 */

public class SHBHTML {

    /**
     * 上海银行跳转
     */
    public static String createAutoSubmitForm(String url, String serviceName, String platformNo,String userDevice,String reqData,String keySerial,String sign) {


        String html =
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +

                        "<html>\n" +
                        "<head>\n" +
                        "    <title>跳转......</title>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "    <form action=\"%s\" method=\"post\" id=\"frm1\" style=\"display:none;\">\n" +
                        "        <input type=\"text\" class=\"form-control\" id=\"serviceName\" name=\"serviceName\" value=\"%s\"></input>\n" +
                        "        <input type=\"text\" class=\"form-control\" id=\"platformNo\" name=\"platformNo\" value=\"%s\"></input>\n" +
                        "        <input type=\"text\" class=\"form-control\" id=\"userDevice\" name=\"userDevice\" value=\"%s\"></input>\n" +
                        "        <textarea class=\"form-control\" id=\"reqData\" name=\"reqData\" rows=\"3\">%s</textarea>\n" +
                        "        <input type=\"text\" class=\"form-control\" id=\"keySerial\" name=\"keySerial\" value=\"%s\"></input>\n" +
                        "        <textarea class=\"form-control\" id=\"sign\" name=\"sign\" rows=\"5\">%s</textarea>\n" +
                        "    </form>\n" +
                        "    <script type=\"text/javascript\">\n" +
                        "        document.getElementById(\"frm1\").submit()\n" +
                        "    </script>\n" +
                        "</body>\n";
        String sbStr = String.format(html,url,serviceName,platformNo,userDevice,reqData,keySerial,sign);


        return sbStr;
    }
}

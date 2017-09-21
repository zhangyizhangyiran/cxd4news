//package com.cxd.cxd4android.widget.phone;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.content.pm.ResolveInfo;
//import android.net.Uri;
//
//import java.util.List;
//
///**
// * @version V1.0
// * @ClassName:
// * @Description:
// * @Author：xiaofa
// * @Date：2016/7/14 20:16
// */
//public class GetUri{
//    public static Intent getIntent(Context paramContext)
//    {
//        StringBuilder localStringBuilder = new StringBuilder().append("market://details?id=");
//        String str = paramContext.getPackageName();
//        localStringBuilder.append(str);
//        Uri localUri = Uri.parse(localStringBuilder.toString());
//        return new Intent("android.intent.action.VIEW", localUri);
//    }
//
//    public static void start(Context paramContext, String paramString)
//    {
//        Uri localUri = Uri.parse(paramString);
//        Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
//        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        paramContext.startActivity(localIntent);
//    }
//
//    public static boolean judge(Context paramContext, Intent paramIntent)
//    {
//        List<ResolveInfo> localList = paramContext.getPackageManager().queryIntentActivities(paramIntent, PackageManager.GET_INTENT_FILTERS);
//        if ((localList != null) && (localList.size() > 0)){
//            return false;
//        }else{
//            return true;
//        }
//    }
//}

package com.cxd.cxd4android.widget.gestrues;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 手势密码设置
 * @author dalong
 *
 */
public class LockPatternUtils {
	public static String loginKey="login_key";
	
//	/**
//	 * 保存设置的手势密码，这里就不做加密处理了，使用时应当这里做了加密再保存
//	 * @param locks
//	 */
//	public static void saveLockPattern(Context context,String key,String locks){
//		SharedPreferences prefs = PreferenceManager
//				.getDefaultSharedPreferences(context);
//		Editor edit = prefs.edit();
//		edit.putString(key, locks);
//		edit.commit();
//	}
//	/**
//	 * 读取手势密码
//	 * @param context
//	 * @param key
//	 * @return
//	 */
//	public static String getLockPattern(Context context,String key){
//		SharedPreferences prefs = PreferenceManager
//				.getDefaultSharedPreferences(context);
//		return prefs.getString(key, null);
//	}

	
//	/**
//	 * 设置登录
//	 * @param context
//	 * @param isLogin
//	 */
//	public static void setLogin(Context context,boolean isLogin){
//		SharedPreferences prefs = PreferenceManager
//				.getDefaultSharedPreferences(context);
//		Editor edit = prefs.edit();
//		edit.putBoolean(loginKey, isLogin);
//		edit.commit();
//	}
//	/**
//	 * 获取是否登录
//	 * @param context
//	 * @return
//	 */
//	public static boolean  isLogin(Context context){
//		SharedPreferences prefs = PreferenceManager
//				.getDefaultSharedPreferences(context);
//		return prefs.getBoolean(loginKey, false);
//	}
}

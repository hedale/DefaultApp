package com.hele.defaultapp.util;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import java.util.Collection;

public class CommonUtil {
	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)|| "null".equals(str))
			return true;
		return false;
	}

	/**
	 * 判断list 是否为空
	 * @param list
	 * @return
     */
	public static boolean isEmpty(Collection<? extends Object> list){
		if(list == null || list.isEmpty())
			return true;
		return false;
	}
	/**
	 * 获取本机手机号码
	 * @param context
	 * @return
	 */
	public static String getPhone(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}
	/**
	 * 根据协议跳转到自定义的act
	 * @param uri
	 * @return
	 */
	public static Intent go2NextAct(String uri){
		Intent intent = null;
		return intent;
	}
}

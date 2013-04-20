package com.fgh.room.util;

import android.content.Context;
import android.content.Intent;

public class IntentCaller {
	/**
	 * 调用电话界面
	 * @param ctx
	 */
	public static void DialIntent(Context ctx){
		Intent intent = new Intent();
		//系统默认的action，用来打开默认的电话界面
		intent.setAction(Intent.ACTION_DIAL);
		//需要拨打的号码
//		intent.setData(Uri.parse("tel:"+i));
		ctx.startActivity(intent);
	}
	/**
	 * 调用短信界面
	 * @param ctx
	 */
	public static void SmsIntent(Context ctx){
		Intent intent = new Intent(Intent.ACTION_VIEW);  
        intent.setType("vnd.android-dir/mms-sms");  
//      intent.setData(Uri.parse("content://mms-sms/conversations/"));//此为号码  
        ctx.startActivity(intent);  
	}
	/**
	 * 调用camera
	 * @param ctx
	 */
	public static void CameraIntent(Context ctx){
		Intent i = new Intent(Intent.ACTION_CAMERA_BUTTON, null);   
		ctx.sendBroadcast(i);  
	}
}

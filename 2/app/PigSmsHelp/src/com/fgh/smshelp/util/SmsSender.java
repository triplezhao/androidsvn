package com.fgh.smshelp.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class SmsSender {
	
	public static boolean send(Context ctx,String cmd) {
		boolean isSended=false;
		android.telephony.SmsManager smsMgr = android.telephony.SmsManager.getDefault();
		try {
				smsMgr.sendTextMessage("10086", null, cmd, null, null);
			isSended = true;
		} catch (Exception e) {
			Log.e("Exception", e.getMessage());
			Toast.makeText(ctx, "查询失败...", Toast.LENGTH_SHORT).show();
		}
		return isSended;
	}
}

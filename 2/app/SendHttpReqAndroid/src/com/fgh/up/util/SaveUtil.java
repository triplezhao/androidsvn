package com.fgh.up.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveUtil {

	public static Map<String,String> ReadSharedPreferences(Context context)
	{
		SharedPreferences   user = context.getSharedPreferences("send_info_sf",0);
		String channel=user.getString("channel", "motoHD");
		String app_version=user.getString("app_version", "2.6");
		String number=user.getString("number", "100");
		
		Map<String,String> map=new HashMap<String, String>();
		map.put("channel", channel);
		map.put("app_version", app_version);
		map.put("number", number);
	
	return map;
	
	}

	public static void WriteSharedPreferences(Context context,
			String app_version,String channel,String number) {
		SharedPreferences send_info_sf = context.getSharedPreferences("send_info_sf", 0);
		SharedPreferences.Editor send_info=send_info_sf.edit();
		send_info.putString("channel", channel);
		send_info.putString("app_version", app_version);
		send_info.putString("number", number);
		send_info.commit();
	}
}

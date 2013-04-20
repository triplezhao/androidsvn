package com.fgh.up.logic;

import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.Time;

import com.fgh.up.util.ImeiDateUtil;


//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

public class ParamTool {
	
	public static String getPostData(ReqBean bean){
		
		JSONObject paramJson=new JSONObject();
		
		try {
			
		//body
		JSONObject body=new JSONObject();
		JSONArray launch=new JSONArray();
		JSONArray terminate=new JSONArray();
		
		
		JSONObject launch_item1=new JSONObject();
		launch_item1.put("date", bean.getStringDate());
		launch_item1.put("time", bean.getTime());
		launch_item1.put("session_id", bean.getSession_id());
//		launch.put(launch_item1);
		launch.put(launch_item1);
		
		
		JSONArray activities=new JSONArray();
		
		JSONArray activities_item1=new JSONArray();
		activities_item1.put("com.mfw.roadbook.activity.Splash");
		activities_item1.put(2);
		
		JSONArray activities_item2=new JSONArray();
		activities_item2.put("com.mfw.roadbook.activity.MainPageActivity");
		activities_item2.put(200);
		
		activities.put(activities_item1);
		activities.put(activities_item2);
		JSONObject terminate_item1=new JSONObject();
		
			terminate_item1.put("date", bean.getStringDate());
		
		terminate_item1.put("time",  bean.getTime());
		terminate_item1.put("session_id", bean.getSession_id());
		//持续的时间
		 Time time = new Time("GMT+8");    
	        time.setToNow();   
	        int minute = time.minute;   
		terminate_item1.put("duration", 180+new Random().nextInt(50)+minute);
		terminate_item1.put("activities", activities);
		terminate.put(terminate_item1);
		
		
		
		body.put("launch", launch);
		body.put("terminate", terminate);
		
		
		paramJson.put("body", body);
		
		//constuct header
		
		JSONObject header=new JSONObject();
			header.put("os", "Android");
			header.put("access_subtype", "EDGE");
			header.put("cpu", "ARMv6-compatible processor rev 2 (v6l)");
			header.put("appkey", "4e1ac06e431fe35671000079");
			header.put("sdk_version",bean.getSdk_version());
			header.put("app_version", bean.getApp_version());
			header.put("device_id", bean.getDevice_id());
			header.put("resolution", bean.getResolution());
			header.put("access", bean.getAccess());
		
//			header.put("country","460");
			header.put("country","CN");
			header.put("os_version", bean.getSdk_version());
			header.put("version_code", 11);
			header.put("device_model", bean.getDevice_model());
			header.put("timezone", 8);
			header.put("sdk_type", "Android");
			header.put("carrier", bean.getCarrier());
			header.put("language", "Unknown");
			header.put("channel", bean.getChannel());
//			header.put("lat", String.valueOf(bean.getLocation().lat));
//			header.put("lng",  String.valueOf(bean.getLocation().lng));
			header.put("lat", bean.getLocation().lat);
			header.put("lng",  bean.getLocation().lng);
			
		    paramJson.put("header", header);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramJson.toString();
	}
	
	public static ReqBean getNewReqBean(String app_version,String channel,
			String date){
		//初始化的时候已经随机好了 大部分属性
		ReqBean bean=new ReqBean();
			bean.setApp_version(app_version);
			bean.setChannel(channel);
			long l_dt=ImeiDateUtil.dateString2long(date);
			bean.setDate(l_dt);
			bean.setRandomParam();
			
		return bean;
	}
	
	
}

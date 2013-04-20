package com.fgh.up.pc.logic;

import java.util.Calendar;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.fgh.up.pc.util.ImeiDateUtil;




//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

public class JsonParamTool {
	
	/**
	 * @param bean
	 * @return
	 */
	public static String getPostData(ReqBean bean){
		
		JSONObject paramJson=new JSONObject();
		
		try {
			
		//body
		JSONObject body=new JSONObject();
		JSONArray launch=new JSONArray();
		JSONArray terminate=new JSONArray();
		
		// launch
		JSONObject launch_item1=new JSONObject();
		launch_item1.put("date", bean.getStringDate());
		launch_item1.put("time", bean.getTime());
		launch_item1.put("session_id", bean.getRandomSession_id());
//		launch.put(launch_item1);
		launch.add(launch_item1);
		//launch end
		
		// terminate start
			JSONArray activities=new JSONArray();
				JSONArray activities_item1=new JSONArray();
				activities_item1.add("com.mfw.roadbook.activity.Splash");
				activities_item1.add(2);
				
				JSONArray activities_item2=new JSONArray();
				activities_item2.add("com.mfw.roadbook.activity.MainPageActivity");
				activities_item2.add(130);
				
				
				activities.add(activities_item1);
				activities.add(activities_item2);
				
				
				//一下为 随机 添加的访问页面  
				JSONArray activities_item3=new JSONArray();
				activities_item3.add("org.vudroid.pdfdroid.PdfViewerActivity");
				activities_item3.add(155);
				
				JSONArray activities_item4=new JSONArray();
				activities_item4.add("com.mfw.roadbook.activity.MyBookActivity");
				activities_item4.add(15);
				
				
				//分为5部分  1-2 ； 3-5； 10-29； 30-99；
				int add_act_count=new Random().nextInt(12);
				if(add_act_count>=0&&add_act_count<=4){
					//1-2个页面
				}else if(add_act_count>=5&&add_act_count<=8){
					//3-5个页面
					activities.add(activities_item3);
					activities.add(activities_item4);
				}else if(add_act_count>=9&&add_act_count<12){
					int random_n=new Random().nextInt(26);
					int n=0;
					//6-9个页面
					if(random_n>=0&&random_n<=15){
						n=6;
					
					}else if(random_n>=16&&random_n<=24){
						//10-29个页面
						n=10;
					}else if(random_n==25){
						//30-99个页面
						n=30;
					}
					for(int i=0;i<n;i++){
						JSONArray activities_item_tmp=new JSONArray();
						activities_item_tmp.add("com.mfw.roadbook.activity.MainPageActivity");
						activities_item_tmp.add(3);
						activities.add(activities_item_tmp);
					}
					
				}
				
				
			JSONObject terminate_item1=new JSONObject();
				terminate_item1.put("date", bean.getStringDate());
				terminate_item1.put("time",  bean.getTime());
				terminate_item1.put("session_id", bean.getRandomSession_id());
			    //持续的时间
				Calendar c = Calendar.getInstance();
				//terminate_item1.put("duration", 180+new Random().nextInt(Calendar.DAY_OF_MONTH)+5*c.get(Calendar.DAY_OF_WEEK));
				
				/*1-3秒	282	13.4%
				3-10秒	205	9.7%
				10-30秒	248	11.8%
				30-60秒	234	11.1%
				1-3分钟	440	20.9%
				3-10分钟	423	20.1%
				10-30分钟	221	10.5%
				30分钟以上	53	2.5%
				*/
				int random_n=new Random().nextInt(105);
				int n=2;
				//1-3秒
				if(random_n>=0&&random_n<=13){
					n=0;
				}else if(random_n>=14&&random_n<=23){
					//3-10秒	205	9.7%
					n=10;
				}else if(random_n>=24&&random_n<=35){
					//10-30秒	248	11.8%
					n=10;
				}else if(random_n>=36&&random_n<=47){
					//30-60秒	234	11.1%
					n=30;
				}else if(random_n>=48&&random_n<=69){
					//1-3分钟	440	20.9%
					n=80;
				}else if(random_n>=70&&random_n<=90){
					//3-10分钟	423	20.1%
					n=220;
				}else if(random_n>=91&&random_n<=100){
					//10-30分钟	221	10.5%
					n=600;
				}else if(random_n>=101&&random_n<=104){
					//30分钟以上	53	2.5%
					n=1800;
				}
				terminate_item1.put("duration", n+Constants.random_terminate_time);
				//System.out.println(c.get(Calendar.MINUTE));
				terminate_item1.put("activities", activities);
		terminate.add(terminate_item1);
		// terminate end 
		
		
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

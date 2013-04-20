package com.fgh.up.logic;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import pigframe.PigApplication;

import android.content.res.Configuration;
import android.provider.Settings;

import com.fgh.up.util.ImeiDateUtil;

public class ReqBean {
		//	1.data    2012-04-19
		//	2.time   "06:10:29";
		//	3.session_id  "4e1ac06e431fe356710000791334815829295";
		//	
		//	1.device_model  "MB300"
		//	2.sdk_version   2.3.5 2.3.4 2.3.6 
		//	3.resolution     480*800  480*854 400*240 
		//	4.device_id  IMEI
		//	5.carrier China Mobile   China Unicom  China Telecom
		//	6.access WIFI 2G\/3G
		//	7.os_version 2.3.4
	
	//	这两个参数为参数传入构造方法。其他的都随机
		//	8.app_version 2.6
		//	9.channel zhuowang
	
	long date=0;
	String time="";
	String session_id="";
	
	String device_model="";
	static String[] device_model_array={ "GT-i9100 "," MI-ONE Plus",
		" HTC Incredible S"," MB525 Defy "
		,"HTC Desire HD"
		,"HTC Desire S"
		,"HTC Desire"
		,"HTC HD2"
		,"HTC Wildfire S"
		,"GT-i9108"
		,"HTC Sensation"
		,"GT-i9003"
		,"GT-i9008"
		,"xt800"
		,"GT-S5820"
		};
	static String[] device_model_array_moto={ "MB525 Defy ","XT615","XT301","XT702","ME865"};
	String sdk_version="";
	static String[] sdk_version_array={ "2.3.5","2.3.4","2.3.6 ","2.3.3","2.3.7"};
	String os_version="";
	
	String resolution="";
	static String[] resolution_array={ "800*480","854*480","480*320"};
	String device_id="";
	String carrier="";
	static String[] carrier_array={ "中国移动","中国联通","中国电信"};
	
	String access="";
	static String[] access_array={ "Wi-Fi","2G/3G"};
	
	
	String app_version="";
	String channel="";

	Lat_Lng location;
	
	
	
	public Lat_Lng getLocation() {
		return location;
	}

	public void setLocation(Lat_Lng location) {
		this.location = location;
	}
	
	public ReqBean(){
	}
	
	/**
	 * 随机出需要变化的参数
	 */
	public void setRandomParam(){
		//已有方案
//		date=getRandomDate();
		time=getRandomTime();
		session_id=getRandomSession_id();
		
		
		device_model=getRandomDevice_model();
		sdk_version=getRandomSdk_version();
		resolution=getRandomResolution();
		device_id=getRandomIMEI();
		carrier=getRandomCarrier();
		access=getRandomAccess();
		os_version=getRandomSdk_version();
		location=getRandomLocation();
	}
	
	/**
	 * sessionid的后8位需要变化
	 * @return
	 */
	private   String getRandomSession_id() {  
		StringBuffer bff = new StringBuffer();
	       for(int i=0;i<9;i++){
	                int num = (int) Math.round(Math.random()*8);
	                bff.append(num);
	         }
		session_id="4e1ac06e431fe356710000791334"+bff.toString();
		return session_id;
	} 
	/**
	 * 联网方式 WIFI   2G\/3G
	 * @return
	 */
	private   String getRandomAccess() {  
		int len=access_array.length;
		int select=(int) (Math.random()*len);
		access=access_array[select];
		return access;
	} 
	/**
	 * China Mobile   China Unicom  China Telecom
	 * @return
	 */
	private   String getRandomCarrier() {  
		int len=carrier_array.length;
		int select=(int) (Math.random()*len);
		carrier=carrier_array[select];
		return carrier;
	} 
	
	/**
	 * GT-i9100 / MI-ONE Plus / HTC Incredible S/ MB525 Defy /HTC Desire HD
	 * @return
	 */
	private  String getRandomDevice_model(){
		
		if(channel.equals("motoHD")){
			int len=device_model_array_moto.length;
			int select=(int) (Math.random()*len);
			device_model=device_model_array_moto[select];
			
			int mb525=(int) (Math.random()*4);
			if(mb525==1){
				device_model="MB525 Defy";
			}
			
		}else if(channel.equals("aliyun")){
			device_model="W700";
		}else if(channel.equals("szshuaji")){
//			HTC Desire HD
			int len=device_model_array.length;
			int select=(int) (Math.random()*len);
			device_model=device_model_array[select];
			
			int mi=(int) (Math.random()*6);
			if(mi==1){
				device_model="HTC Desire S";
			}else if(mi==2){
					device_model="HTC Desire HD";
			}else if(mi==3){
				   device_model="HTC Desire HD";
			}
		}else{
			int len=device_model_array.length;
			int select=(int) (Math.random()*len);
			device_model=device_model_array[select];
			
			int mi=(int) (Math.random()*6);
			if(mi==1){
				device_model="GT-i9100";
			}else if(mi==2){
					device_model="MI-ONE Plus";
			}else if(mi==3){
				   device_model="MI-ONE Plus";
			}
		}
		 
		return device_model;
	}
	/**
	 * 2.3.5  2.3.4  2.3.6  2.3.3  2.3.7
	 * @return
	 */
	private  String getRandomSdk_version(){
		int len=sdk_version_array.length;
		int select=(int) (Math.random()*len);
		sdk_version=sdk_version_array[select];
		
		return sdk_version;
	}
	/**
	 * 480*800  480*854  320*480
	 * @return
	 */
	private  String getRandomResolution(){
		int len=resolution_array.length;
		int select=(int) (Math.random()*len);
		resolution=resolution_array[select];
		
		int rl480_800=(int) (Math.random()*3);
		if(rl480_800==1){
			resolution="800*480";
		}
		
		return resolution;
	}
	
	private   String getRandomDate() {  
		  Date now = new Date(); 
	      DateFormat d2 = DateFormat.getDateInstance(); 
	      String str2 = d2.format(now); 
	      return str2;  
	 } 
	 
	 /**
	  * 随机0-24小时内的时间
	 * @return
	 */
	private  String getRandomTime()  
	 {  
//		 Date now = new Date(); 
//		 DateFormat d4 = DateFormat.getTimeInstance(); //使用SHORT风格显示日期和时间
//		 String str4 = d4.format(now);
//		 
//		 int h=(int) (Math.random()*24);
//		 String time= String.valueOf(h)+str4.substring(2);
		 
		 int h=(int) (Math.random()*24);
		 int m=(int) (Math.random()*60);
		 int s=(int) (Math.random()*60);
		 
		 int h1=(int) (Math.random()*12);
		 switch (h1) {
			 case 0:
				 h=21;
				 break;
			case 1:
				h=12;
				break;
			case 2:
				h=13;
				break;
			case 3:
				h=14;
				break;
			case 4:
				h=18;
				break;
			case 5:
				h=19;
				break;
			case 6:
				h=20;
				break;
			case 7:
				h=20;
				break;
			case 8:
				h=21;
				break;
			case 9:
				h=21;
				break;

			default:
				break;
		}
		 
		 String time= String.valueOf(h)+":"
		 				+String.valueOf(m)+":"
		 				+String.valueOf(s);
		 return time;  
	 }  

	
	 /**
	  * //随即生成15位imei号
	 * @return
	 */
	 private  String getRandomIMEI() {
		
	       StringBuffer bff = new StringBuffer();
	       for(int i=0;i<15;i++){
	                int num = (int) Math.round(Math.random()*8);
	                bff.append(num);
	         }
		  return bff.toString();
	 }
	 private  Lat_Lng getRandomLocation() {
		 
		 
//		 Locale localObject1 = Locale.getDefault();
//		 //获取国家码
//		 localObject1.getCountry();
//		 
//		 Configuration localConfiguration = new Configuration();
//	      Settings.System.getConfiguration(
//	    		  PigApplication.getInstance().getContentResolver(), localConfiguration);
	      
	      
		 Lat_Lng locaiton=new Lat_Lng();
		 //测试用 暂时全都是广东
		 locaiton.setLat(23.06);
		 locaiton.setLng(113.28);
		 
//		 int h=(int) (Math.random()*10);
//		 
//		 switch (h) {
//			 case 0:
//				 //beijing 
//				 locaiton.setLat(47.22);
//				 locaiton.setLng(116.20);
//				 break;
//			case 1:
//				//guangdong
//				 locaiton.setLat(23.06);
//				 locaiton.setLng(113.28);
//				break;
//			case 2:
//				//shanghai
//				 locaiton.setLat(31.11);
//				 locaiton.setLng(121.28);
//				break;
//			case 3:
//				//jiangsu
//				 locaiton.setLat(32.03);
//				 locaiton.setLng(118.48);
//				break;
//			case 4:
//				//zhejiang
//				 locaiton.setLat(30.16);
//				 locaiton.setLng(120.08);
//				break;
//			case 5:
//				 //beijing 
//				 locaiton.setLat(47.22);
//				 locaiton.setLng(116.20);
//				break;
//			case 6:
//				 //beijing 
//				 locaiton.setLat(47.22);
//				 locaiton.setLng(116.20);
//				break;
//			case 7:
//				 //beijing 
//				 locaiton.setLat(47.22);
//				 locaiton.setLng(116.20);
//				break;
//			case 8:
//				//guangdong
//				 locaiton.setLat(23.06);
//				 locaiton.setLng(113.28);
//				break;
//			case 9:
//				//guangdong
//				 locaiton.setLat(23.06);
//				 locaiton.setLng(113.28);
//				break;
//
//			default:
//				break;
//		}
		 return locaiton;
	 }
	
	////以上参数需要随机算出来。
	
	
	
	public String getStringDate() {
		return ImeiDateUtil.dateLong2String(date);
	}
	public long getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getSession_id() {
		return session_id;
	}

	public String getDevice_model() {
		return device_model;
	}

	public String getSdk_version() {
		return sdk_version;
	}

	public String getResolution() {
		return resolution;
	}

	public String getDevice_id() {
		return device_id;
	}

	public String getCarrier() {
		return carrier;
	}

	public String getAccess() {
		return access;
	}

	public String getOs_version() {
		return os_version;
	}

	public String getApp_version() {
		return app_version;
	}

	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}

	public String getChannel() {
		return channel;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setDevice_id(String deviceId) {
		device_id = deviceId;
	}
	
	
}

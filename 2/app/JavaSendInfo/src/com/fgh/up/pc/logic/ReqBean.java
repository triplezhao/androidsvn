package com.fgh.up.pc.logic;


import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


import com.fgh.up.pc.util.ImeiDateUtil;

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
	static String[] device_model_array={ 
		 "GT-i9100 "
		,"MI-ONE Plus"
		,"HTC Incredible S"
		,"HTC Desire HD"
		,"HTC Desire S"
		,"HTC Desire"
		,"HTC HD2"
		,"HTC Wildfire S"
		,"HTC Sensation"
		,"GT-i9003"
		,"GT-i9008"
		,"GT-S5820"
		,"GT-i9103"
		,"GT-i9220"
		,"GT-i9108"
		,"GT-S5830"
		,"GT-i9300"
		,"GT-I93001362"
		,"GT-N71001178"
		,"GT-I9300884"
		,"Nexus S"
		,"XT615"
		,"XT301"
		,"XT702"
		,"XT910"
		,"XT882"
		,"XT800"
		,"ME865"
		,"ME860"
		,"MB525 Defy "
		
		};
	static String[] device_model_array_moto={ "MB525 Defy ","XT615","XT301","XT702",
		"ME865","XT910","XT800","ME865"};
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
	public   String getRandomSession_id() {  
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
		int n=(int) (Math.random()*100);
		
		if(n<67){
			carrier=carrier_array[0];
		}else{
			carrier=carrier_array[select];
		}
		return carrier;
	} 
	
	/**
	 * GT-i9100 / MI-ONE Plus / HTC Incredible S/ MB525 Defy /HTC Desire HD
	 * @return
	 */
	/**
	 * @return
	 */
	/**
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
			
		}else if(channel.equals("gfive")||channel.equals("Gfive")){
			device_model="gfive-a79";
			if(new Random().nextInt(2)==1){
				device_model="gfive-a82";
			}
		}
		else if(channel.equals("aliyun")){
			device_model="W700";
		}
		//TCL渠道机型
		/*
		 * TCL S60059	
			31.4%
			TCL S80031	
			16.5%
			TCL A96817	
			9.0%
			TCLW98917	
			9.0%
			TCL A99616	
			8.5%
			TCL S90014	
			7.4%
			TCL A86012	
			6.4%
			TCL8	
			4.3%
			TCL A9194	
			2.1%
			TCL A9983
		 * */
		else if(channel.equals("TCL")){
			device_model="TCL S60059";
			int i=new Random().nextInt(36)+1;
			//30%
			if(i<=12){
				device_model="TCL S60059";
			//16%
			}else if(13<=i&&i<=19){
				device_model="TCL S80031";
			//9%
			}else if(20<=i&&i<=22){
				device_model="TCL A96817";
			//9%
			}else if(23<=i&&i<=25){
				device_model="TCLW98917";
			//8.5%
			}else if(26<=i&&i<=28){
				device_model="TCL A99616";
			//8.5%
			}else if(29<=i&&i<=31){
				device_model="TCL S90014";
			//7.4%	 
			}else if(32<=i&&i<=33){
				device_model="TCL A86012";
			//6.4%
			}else if(34<=i&&i<=35){
				device_model="TCL8";
			//4.3%
			}else if(36==i){
				device_model="TCL A9194";
			}
//			//2.1%
//			}else if(33<=i&&i<=33){
//				device_model="TCL A96817";
//			//
//			}else if(34<=i&&i<=36){
//				device_model="TCL A96817";
//			}
			
		}else if(channel.equals("JinLi")){
			
			if(new Random().nextInt(3)==1){
				device_model="GN108";
			}else{
				device_model="GN180";
			}
		}else if(channel.equals("szshuaji")){
//			HTC Desire HD
			int len=device_model_array.length;
			int select=(int) (Math.random()*len);
			device_model=device_model_array[select];
			
			int mi=(int) (Math.random()*6);
			if(mi==1){
				device_model="ME865";
			}else if(mi==2){
					device_model="ME860";
			}else if(mi==3){
				   device_model="XT910";
			}
		}else{
			//,"GT-I93001362"
			//,"GT-N71001178"
			int len=device_model_array.length;
			int select=(int) (Math.random()*len);
			device_model=device_model_array[select];
			
			int mi=(int) (Math.random()*10)+1;
			if(mi==1){
					device_model="GT-i9220";
			}else if(mi==2){
					device_model="GT-i9100";
			}else if(mi==3){
				    device_model="GT-I93001362";
			}else if(mi>3&&mi<6){
				    device_model="GT-N71001178";
			}else if(mi==6){
					device_model="GT-I9300884";
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

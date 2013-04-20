package com.fgh.up.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImeiDateUtil {
	/**
	 * 2、由long类型转换成Date类型
	 * @param datelong
	 * @return
	 */
	public static String dateLong2String(long datelong){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		//前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		java.util.Date dt = new Date(datelong * 1000);  
		String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
//		System.out.println(sDateTime);
		return sDateTime;
	}
	public static long dateString2long(String dateString){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date result=null;
		try {
			result = format.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time = result.getTime() / 1000; 
		return time;
	}
	
	public static void main(){
		
	}
	
	

	
}

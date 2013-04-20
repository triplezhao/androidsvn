package com.fgh.up.pc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
	
	/**
	 * 2:1:1:1:1:1:1   ：   3:2:1.5:1:0.5    ：3:2:1:1:0.5:0.5+2
		8+8+10  总共2000的总量，按照上面比例分配基本没问题。
		
		12         6:4:3:2:1            6:4:2:2:1:1:4
		
		12 （0周）         4:3:2:1   （1、2、3、4周）              4:2:2:1:1:4 （1、2、3、4、5月+后面所有月）
		
		12+10+14=36

	 * @return
	 */
	public static int getRandomNumWeek(long nowDate){
		
		int numweek=0;
//		12 （0周）     
//		4:3:2:1   （1、2、3、4周）   
//		4:3:2:1:1:3 （1、2、3、4、5月+后面所有月）
//		12+10+14=36
		int i=new Random().nextInt(36)+1;
		//0-1周前 到 本天之间
		if(i<=12){
			numweek=0;
		//1-2周
		}else if(13<=i&&i<=16){
			numweek=1;
		//2-3周
		}else if(17<=i&&i<=19){
			numweek=2;
		//3-4周
		}else if(20<=i&&i<=21){
			numweek=3;
		//4-5周1个月
		}else if(22<=i&&i<=22){
			numweek=4;
		//8-9周  2个月
		}else if(23<=i&&i<=26){
			numweek=8;
		//12-13周 3个月	 
		}else if(27<=i&&i<=29){
			numweek=12;
		//16-17周 4个月
		}else if(30<=i&&i<=31){
			numweek=16;
		//20-17周 5个月
		}else if(32<=i&&i<=32){
			numweek=20;
		//24-15周 6个月	
		}else if(33<=i&&i<=33){
			numweek=24;
		//7个月以后所有月份
		}else if(34<=i&&i<=36){
			numweek=new Random().nextInt(80)+28;
		}
		System.out.println(numweek+" weeks ago");
		return numweek;
		
	}
	public static long getRandomDate(long nowDate){
		
		long numdate=0;
		while(true){
			numdate=new Random().nextInt(365);
			if(numdate>1&&numdate<7){
				break;
			}else if(numdate>7&&numdate<14){
				if(new Random().nextInt(4)<3)
					break;
			}else if(numdate>14&&numdate<21){
				if(new Random().nextInt(4)<2)
					break;
			}else if(numdate>21&&numdate<28){
				if(new Random().nextInt(4)<1)
					break;
			}else if(numdate>30){
				if(new Random().nextInt(10)<1)
				break;
			}
		}
		System.out.println("before:=>"+numdate);
		return nowDate-numdate*24*3600;
		
	}

	
}

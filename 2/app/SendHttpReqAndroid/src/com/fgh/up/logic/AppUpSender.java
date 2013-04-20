package com.fgh.up.logic;

import java.util.Random;

import android.content.Context;
import android.database.Cursor;

import com.fgh.up.database.ImeiBean;
import com.fgh.up.database.ImeiDao;
import com.fgh.up.util.ImeiDateUtil;

public class AppUpSender {
	
	
	private String app_version="";
	private String channel="";
	private int number=0;
	private String date="";
	private boolean isActive=false;
	
	
	
	 public static int mcount=0;
	 public static boolean isStop=false;
	 private CountLisener countLisener;
	 private Context mcontext;
	 private ImeiDao dao;
	public AppUpSender(Context context,String app_version,String channel,
			int number,String date,boolean isActive){
		this.app_version=app_version;
		this.channel=channel;
		this.number=number;
		this.date=date;
		this.isActive=isActive;
		
		mcontext=context;
		dao=ImeiDao.getInstance(mcontext);
	}
	
	public  void doAtcionInThread(){
		
	 Thread thread=new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
						Cursor cursor=null;
						int count=0;
						if(isActive){
							cursor=dao.getChanelImeiCuisor(channel);
							count=cursor.getCount()-1;
						}
						
					while(mcount<number&&!isStop){
						
						try {
							
							ReqBean bean=ParamTool.getNewReqBean(app_version, channel,date);
							//如果是刷活跃，imei要从数据库中读取。
							if(isActive&&cursor!=null){
								ImeiBean imeiBean=null;
						    	
						    	if(count>0){
//							    	int index=mcount<count?mcount:count;
						    		
						    		while(true){
						    			int index=new Random().nextInt(count);
							    		if(cursor.moveToPosition(index)){
								    		imeiBean = new ImeiBean();
											imeiBean.setId(cursor.getString(0));
											imeiBean.setImei_value(cursor.getString(1));
											imeiBean.setImei_chanel(cursor.getString(2));
											long db_date=cursor.getInt(3);
											imeiBean.setImei_date(db_date);
											long now_date=ImeiDateUtil.dateString2long(date);
											long howold=now_date-db_date;
											if(howold<7*24*3600){
												break;
											}else if(howold>7*24*3600&&howold<14*24*3600){
												if(new Random().nextInt(4)<3)
													break;
											}else if(howold>14*24*3600&&howold<21*24*3600){
												if(new Random().nextInt(4)<2)
													break;
											}else if(howold>21*24*3600&&howold<28*24*3600){
												if(new Random().nextInt(4)<1)
													break;
											}else if(howold>30*24*3600){
												break;
											}
								    	}
							    	}
						    		
						    	}
								if(imeiBean!=null){
									String imei=imeiBean.getImei_value();
									bean.setDevice_id(imei);
									System.out.println("change to="+imei);
								}
							}
							
							String postData=ParamTool.getPostData(bean);
							String url="http://www.umeng.com/app_logs";
							if(HttpTool.sendPost(url, postData)){
								
								if(!isActive){
									ImeiBean imeiBean=new ImeiBean();
										imeiBean.setImei_value(bean.getDevice_id());
										imeiBean.setImei_chanel(bean.getChannel());
										imeiBean.setImei_date(bean.getDate());
										//只存储三分之一
										if(new Random().nextInt(4)<1){
											if(	-1L!=dao.insert(imeiBean)){
												System.out.println("insert:"+imeiBean.toString());
											};
										}
											
								}
								System.out.println(postData);
								mcount+=1;
								countLisener.upCount(mcount);
								System.out.println("sended:"+mcount);
								System.out.println("over======================");
							};
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			thread.start();
		
	}
	 
	public  void startSendHttp() {
		
		isStop=false;
		mcount=0;
		
		for(int i=0;i<5;i++){
			doAtcionInThread();
		}
		
	}
	
	public CountLisener getCountLisener() {
		return countLisener;
	}

	public void setCountLisener(CountLisener countLisener) {
		this.countLisener = countLisener;
	}

	
	public void stopSend(){
		isStop=true;
	}
	
	
	
}

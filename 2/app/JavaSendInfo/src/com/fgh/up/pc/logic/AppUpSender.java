package com.fgh.up.pc.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.fgh.up.pc.database.ImeiBean;
import com.fgh.up.pc.database.ImeiDao;
import com.fgh.up.pc.util.ImeiDateUtil;

public class AppUpSender {
	
	
	private String app_version="";
	private String channel="";
	private int number=0;
	private String date="";
	private boolean isActive=false;
	
	
	
	 public static int mcount=0;
	 public static boolean isStop=false;
	 private CountLisener countLisener;
	 private ImeiDao dao;
	public AppUpSender(String app_version,String channel,
			int number,String date,boolean isActive){
		this.app_version=app_version;
		this.channel=channel;
		this.number=number;
		this.date=date;
		this.isActive=isActive;
		
		dao=ImeiDao.getInstance();
	}
	
	public  void doAtcionInThread(){
		
	 Thread thread=new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					dao.createTable(channel);
					while(mcount<number&&!isStop){
						if(isActive){
							System.out.println("==start add Alive one================");
							
						}else{
							System.out.println("==start add Normal one================");
						}
						
						try {
							
							ReqBean bean=JsonParamTool.getNewReqBean(app_version, channel,date);
							//如果是刷活跃，imei要从数据库中读取。
							if(isActive){
								ImeiBean imeiBean=null;
		    					long now_date=ImeiDateUtil.dateString2long(date);
		    					int numweek=ImeiDateUtil.getRandomNumWeek(now_date);
		    					
		    					/*
		    					 * int 值 
		    					 * */
		    					int ONEWEEK=7;
		    					//0则为本周，1则为一周前，即前1至2周之间
		    					long maxdate=now_date-numweek*ONEWEEK*24*3600;
		    					//1-2周  7-14，为了第8天别太多，影响日观察，则
		    					//有一半几率，让改为 8-15
		    					if(numweek==1){
		    						if(new Random().nextInt(2)==0){
		    							maxdate=maxdate-1*24*3600;
		    						}
		    					}
		    					
		    					
		    					//n周前 7天前的日期
		    					long mindate=maxdate-ONEWEEK*24*3600;
		    					//针对每天生成比例
		    					//使每天呈梯度走势
		    					//int_ran代表max前多少天，来确定mindate
		    					
//		    					if(numweek==0){
		    						int int_ran=new Random().nextInt(ONEWEEK)+1;
		    						mindate=maxdate-int_ran*24*3600;
//		    					}
		    					//在 min 和 max 之间 随机查询出一个imei号来。
	    						//看似ini_ran1-7是等比例的，但是查询的时候是个范围，
		    					//所以0肯定比例还是最大的，7是最小的。
		    					
		    					
		    						//channel当做表名字用
					    		imeiBean =dao.queryOneImeiRandomByDate(
					    						maxdate,mindate,channel);
					    		
					    		if(imeiBean==null){
					    			//channel当做表名字用
					    			imeiBean =dao.queryOneImeiRandomByTbName(channel);
					    		}

					    		if(imeiBean==null){
					    			System.out.println("数据库中无记录，怎么刷活跃啊");
					    			break;
					    		}else{
									String imei=imeiBean.getImei_value();
									bean.setDevice_id(imei);
									System.out.println("success imei to="+imei);
								}
							}
							
							String postData=JsonParamTool.getPostData(bean);
							String url="http://www.umeng.com/app_logs";
							if(HttpTool.sendPost(url, postData)){
								
								if(!isActive){
									ImeiBean imeiBean=new ImeiBean();
										imeiBean.setImei_value(bean.getDevice_id());
										imeiBean.setImei_chanel(bean.getChannel());
										imeiBean.setImei_date(bean.getDate());
										//只存储三分之一
//										if(new Random().nextInt(4)<1){
										//channel当做表名字用
											if(	-1L!=dao.insert2table(imeiBean,channel)){
												System.out.println("success insert to table:"+channel+imeiBean.toString());
											};
//										}
											
								}
								
								//else{//这样就算是增加 活跃的 启动次数了，不然启动次数跟活跃次数一样，太假。
									int ranCount=new Random().nextInt(4);
									//0-2
									if (ranCount==0){//不再发
									}else if (0<ranCount&&ranCount<3){//再发1次
										HttpTool.sendPostInThread(url, JsonParamTool.getPostData(bean));
									}else if (ranCount==3){//再发2次
										HttpTool.sendPostInThread(url, JsonParamTool.getPostData(bean));
										HttpTool.sendPostInThread(url, JsonParamTool.getPostData(bean));
									}
								//}
								//System.out.println(postData);
								mcount+=1;
								countLisener.upCount(mcount);
								System.out.println("sended:"+mcount);
								System.out.println("====over==================\n\n");
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
		
		for(int i=0;i<1;i++){
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

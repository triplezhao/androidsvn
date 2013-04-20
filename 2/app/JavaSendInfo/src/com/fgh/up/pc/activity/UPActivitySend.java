package com.fgh.up.pc.activity;


import java.util.Calendar;
import java.util.Random;


import com.fgh.up.pc.PigBaseActivity;
import com.fgh.up.pc.logic.AppUpSender;
import com.fgh.up.pc.logic.Constants;
import com.fgh.up.pc.logic.CountLisener;

public class UPActivitySend extends PigBaseActivity{

	public static  String str_bundle_return="str_bundle_return";
	
	//5个参数 
	private  String et_chanel;
	private  String et_version;
	private  String et_number;
	private  String et_date;
	private  boolean cb_isactive=false;
	
	private AppUpSender sender;
	
	/**
	 * @param args
	 * et_chanel et_version et_number et_date cb_isactive
	 * gfive 2.7 10 2012-6-6 false
	 */
	public static void main(String args[]){
		int paramCount=args.length;
		if(paramCount!=5){
			System.out.println("please input 5 args");
			return ;
		}
		//使用时间，每天的平均使用时间，是个随机数。
		Constants.random_terminate_time=new Random().nextInt(30);
		
		
		UPActivitySend sender=new UPActivitySend();
		sender.onCreate();
		sender.set5Param(args);
		sender.startSend();
	}
	
	/**
	 * 设置默认值
	 */
	private void setDefaultValue(){
	      
		et_chanel="gfive";
		et_version="2.7";
		et_number="5";
		cb_isactive=false;
		setDateToday();
		
	}
	
	private void set5Param(String args[]){
		int paramCount=args.length;
		for(int i=0;i<paramCount;i++){
			System.out.println(args[i]);
			switch (i) {
			case 0:
				et_chanel=args[i];
				break;
			case 1:
				et_version=args[i];
				break;
			case 2:
				et_number=args[i];
				break;
			case 3:
				et_date=args[i];
				if(et_date.equals("today")){
					setDateToday();
				}
				break;
			case 4:
				if(args[i].equals("false")){
					cb_isactive=false;
				}else{
					cb_isactive=true;
				}
				break;

			default:
				break;
			}
		}
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
//		setContentView(R.layout.layout_send);
		initPre();
		initFindViews();
		initListeners();
		initPost();
		
	}
	
	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
	}

	
	
	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		setDefaultValue();
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	/**
	 * 设置时间为今日
	 */
	private void setDateToday(){
		  final Calendar c = Calendar.getInstance();
	      int  year = c.get(Calendar.YEAR);
	      int  month = c.get(Calendar.MONTH);
	      int  day = c.get(Calendar.DAY_OF_MONTH); 
		  et_date=(new StringBuilder()
		                    // Month is 0 based so add 1
		                    .append(year ).append("-")
		                    .append(month+ 1).append("-")
		                    .append(day).append(" ")
		                    ).toString();
		  System.out.println("today is:"+et_date);
		  
	}

	private void startSend(){
		
		String channel=et_chanel;
		String app_version=et_version;
		String number=et_number;
		String date=et_date;
		boolean isactive=cb_isactive;
		
		
		if("".equals(channel)){
			System.out.println("请设置渠道");
			return ;
		};
		if("".equals(app_version)){
			System.out.println("请设置版本");
			return ;
		};
		if("".equals(number)){
			System.out.println("请设置量");
			return ;
		};
		
		 sender=new AppUpSender(
				app_version, channel, 
				Integer.valueOf(number),date,
				isactive
				);
		 sender.setCountLisener(new CountLisener() {
			
			public void upCount( int number) {
				// TODO Auto-generated method stub
//				System.out.println("number");
			}
		});
		 sender.startSendHttp();
	}
	

}

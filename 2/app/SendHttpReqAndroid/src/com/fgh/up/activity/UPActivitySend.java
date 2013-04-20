package com.fgh.up.activity;


import java.util.Calendar;
import java.util.Map;

import pigframe.PigBaseActivity;
import pigframe.control.PigController;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fgh.up.R;
import com.fgh.up.database.ImeiBean;
import com.fgh.up.database.ImeiDao;
import com.fgh.up.logic.AppUpSender;
import com.fgh.up.logic.CountLisener;
import com.fgh.up.logic.HttpTool;
import com.fgh.up.util.SaveUtil;

public class UPActivitySend extends PigBaseActivity{

	public static  String str_bundle_return="str_bundle_return";
	
	private EditText et_chanel;
	private EditText et_version;
	private EditText et_number;
	private EditText et_date;
	private EditText et_proxy;
	
	
	private Button bt_date;
	private Button bt_start;
	private Button bt_stop;
	private Button bt_chanel;
	private Button bt_proxy;
	private TextView tv_count;
	private ProgressBar pb_count;
	private CheckBox cb_isactive;
	
	AppUpSender sender;
	Handler mHandler = new Handler() {
		   @Override
		   public void handleMessage(Message msg) {
		        //操作界面
			   String count= String.valueOf(msg.obj);
			   tv_count.setText(count);
			   pb_count.setProgress(Integer.parseInt(count));
		   }
		  };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_send);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	
	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		et_chanel=(EditText) findViewById(R.id.et_chanel);
		et_version=(EditText) findViewById(R.id.et_version);
		et_number=(EditText) findViewById(R.id.et_number);
		et_date=(EditText) findViewById(R.id.et_date);
		et_proxy=(EditText) findViewById(R.id.et_proxy);
		bt_date=(Button) findViewById(R.id.bt_date);
		bt_start=(Button) findViewById(R.id.bt_start);
		bt_stop=(Button) findViewById(R.id.bt_stop);
		bt_chanel=(Button) findViewById(R.id.bt_chanel);
		bt_proxy=(Button) findViewById(R.id.bt_proxy);
		tv_count=(TextView) findViewById(R.id.tv_count);
		pb_count=(ProgressBar) findViewById(R.id.progress);
		cb_isactive=(CheckBox) findViewById(R.id.cb_isactive);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		bt_proxy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getThisActivity(), UPActivitySetProxy.class);
				startActivityForResult(intent, 1);
			}
		});
		bt_chanel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getThisActivity(), UPActivitySetChanel.class);
				startActivityForResult(intent, 0);
			}
		});
		bt_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDateDialog();
			}
		});
		bt_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startSend();
			}
		});
		bt_stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(sender!=null){
					sender.stopSend();
				}
			}
		});
	}

	
	private void startSend(){
		
		
		pb_count.setProgress(0);
		
		String channel=et_chanel.getText().toString();
		String app_version=et_version.getText().toString();
		String number=et_number.getText().toString();
		String date=et_date.getText().toString();
		String proxy=et_proxy.getText().toString();
		if(!TextUtils.isEmpty(proxy)){
			String[] ary = proxy.split(":");
			HttpTool.ProxyAddress=ary[0];
			HttpTool.ProxyPort=Integer.valueOf(ary[1]);
		}else{
			HttpTool.ProxyAddress="";
			HttpTool.ProxyPort=0;
		}
		
		boolean isactive=cb_isactive.isChecked();
		
		
		pb_count.setMax(Integer.parseInt(number));
		if(TextUtils.isEmpty(channel)){
			showToast("请设置渠道");
			return ;
		};
		if(TextUtils.isEmpty(app_version)){
			showToast("请设置版本");
			return ;
		};
		if(TextUtils.isEmpty(number)){
			showToast("请设置量");
			return ;
		};
		
		 sender=new AppUpSender(this,
				app_version, channel, 
				Integer.valueOf(number),date,
				isactive
				);
		 sender.setCountLisener(new CountLisener() {
			
			@Override
			public void upCount( int number) {
				// TODO Auto-generated method stub
				Message msg=new Message();
				msg.obj=number;
				mHandler.sendMessage(msg);
				
			}
		});
		 sender.startSendHttp();
	}
	
	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		  
		setDateToday();
		 
		setSFValue();
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		initTitleBar("主页", "默认值","退出",
				0,0,
				0, 0,
				new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						setDefaultValue();
					}
				}
				,
				new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		
		Button leftBtn = (Button) findViewById(R.id.leftBtn);
		leftBtn.setTextColor(R.color.black);
		Button rightBtn = (Button) findViewById(R.id.rightBtn);
		rightBtn.setTextColor(R.color.black);
	}
	
	private void setDefaultValue(){

	      
		et_chanel.setText("motoHD");
		et_version.setText("2.6");
		et_number.setText("100");
		pb_count.setMax(100);
		setDateToday();
	}
	private void setSFValue(){
		Map<String, String> params=SaveUtil.ReadSharedPreferences(this);
		String channel=params.get("channel");
		String app_version=params.get("app_version");
		String number=params.get("number");
		et_chanel.setText(channel);
		et_version.setText(app_version);
		et_number.setText(number);
		pb_count.setMax(Integer.parseInt(number));
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		String	channel	=et_chanel.getText().toString();
		String	version	=et_version.getText().toString();
		String	number	=et_number.getText().toString();
		SaveUtil.WriteSharedPreferences(this,
				version, channel, number);
	}


	private void updateDisplay(int year,int month,int day) {
        et_date.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(year ).append("-")
                    .append(month+ 1).append("-")
                    .append(day).append(" ")
                    );
    }
	
	 private DatePickerDialog.OnDateSetListener mDateSetListener =
         new DatePickerDialog.OnDateSetListener() {

             public void onDateSet(DatePicker view, int year, int monthOfYear,
                     int dayOfMonth) {
            	 updateDisplay(year,monthOfYear,dayOfMonth);
             }
         };
         private DatePickerDialog.OnDateSetListener mDelDateSetListener =
        		 new DatePickerDialog.OnDateSetListener() {
        	 
        	 public void onDateSet(DatePicker view, int year, int month,
        			 int day) {
        		 String dateString=new StringBuilder()
					                // Month is 0 based so add 1
					                .append(year ).append("-")
					                .append(month+ 1).append("-")
					                .append(day).append(" ").toString();
        		
        		
        		 ImeiDao dao=ImeiDao.getInstance(getThisActivity());
        		 long delete_n=dao.deleteByDate(dateString);
        		 showToast("删除了"+delete_n+"条"+dateString+"以前的记录");
        		 
//        		 updateDisplay(year,monthOfYear,dayOfMonth);
        	 }
         };
	
	private void showDateDialog(){
		
		  final Calendar c = Calendar.getInstance();
	      int  mYear = c.get(Calendar.YEAR);
	      int  mMonth = c.get(Calendar.MONTH);
	      int  mDay = c.get(Calendar.DAY_OF_MONTH);
		
		  new DatePickerDialog(this,
                mDateSetListener,
                mYear, mMonth, mDay).show();
	}
	private void showDelDateDialog(){
		
		final Calendar c = Calendar.getInstance();
		int  mYear = c.get(Calendar.YEAR);
		int  mMonth = c.get(Calendar.MONTH);
		int  mDay = c.get(Calendar.DAY_OF_MONTH);
		
		new DatePickerDialog(this,
				mDelDateSetListener,
				mYear, mMonth, mDay).show();
	}
	
	private void setDateToday(){
		  final Calendar c = Calendar.getInstance();
	      int  year = c.get(Calendar.YEAR);
	      int  month = c.get(Calendar.MONTH);
	      int  day = c.get(Calendar.DAY_OF_MONTH); 
		  et_date.setText(new StringBuilder()
		                    // Month is 0 based so add 1
		                    .append(year ).append("-")
		                    .append(month+ 1).append("-")
		                    .append(day).append(" ")
		                    );
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode){  
	        case RESULT_OK:
	    		Bundle b = data.getExtras();  
	    		String rstr= b.getString(str_bundle_return);
	    		if(requestCode==0){
	    			et_chanel.setText(rstr);
	    		}else if(requestCode==1){
	    			et_proxy.setText(rstr);
	    		}
	    		
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item;
			item = menu.add(0, 11, 0, "数据管理");
			item.setIcon(R.drawable.icon);
			
			item = menu.add(0, 12, 0, "帮助");
			item.setIcon(R.drawable.icon);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 11:
			// 添加要处理的内容
			Toast.makeText(this, "数据管理", Toast.LENGTH_SHORT).show();
			showDelDateDialog();
			break;
		}
		return false;
	}
}

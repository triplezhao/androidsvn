package com.fgh.voice.act;

import pigframe.PigBaseActivity;
import pigframe.control.PigController;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;

import com.fgh.voice.PWApplication;
import com.fgh.voice.R;
import com.fgh.voice.db.UserDao;
import com.fgh.voice.db.bean.UserBean;



public class PWellcome_Activity extends PW_BaseActivity {

	private static final String TAG = "SmsWellcome_Activity";
    Button bt_back;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wellcome_layout);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	@Override
	public void initFindViews() {
	}

	@Override
	public void initListeners() {
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PWellcome_Activity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						UGCApplication.getInstance().init();
//						initPublicUser();
						startApp();
					}
				});
				
			}
		}).start();
		
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
	}
	private void startApp() {
		
			PigController.startAct(PWNewMainTabActivity.class, getThisActivity(), true);
			//添加界面切换效果，注意只有Android的2.0(SdkVersion版本号为5)以后的版本才支持  
			int version = Integer.valueOf(android.os.Build.VERSION.SDK);     
			if(version  >= 5) {     
			     overridePendingTransition(R.anim.zoomin, R.anim.zoomout);  //此为自定义的动画效果，下面两个为系统的动画效果  
			   //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);    
			     //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
			} 
			//			Bundle data =new Bundle();
//			data.putString(PWTimeLineActivity.EXTRA_STR_NICK_NAME, getResources().getString(R.string.STR_WEIBO_SUBJECT_NAME));
//			PigController.startAct(PWTimeLineActivity.class, getThisActivity(), true);
	}
	
}

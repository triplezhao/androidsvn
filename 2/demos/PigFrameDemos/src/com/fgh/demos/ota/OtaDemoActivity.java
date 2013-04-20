package com.fgh.demos.ota;

import java.io.File;

import pigframe.download.DownLoadOneFile;
import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpResponse;
import pigframe.ota.UpdateManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

import com.fgh.demos.R;

public class OtaDemoActivity extends PigHttpBaseActivity{

	/* 更新位置 */
	public  String ota_url = "http://appcloudy.sinaapp.com/smsugc/ota/demo_version.xml";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

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
		findViewById(R.id.bt_check).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UpdateManager manager = new UpdateManager(getThisActivity());
				// 检查软件更新
				manager.checkUpdate("http://appcloudy.sinaapp.com/smsugc/ota/demo_version.xml");
			}
		});
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.demo_activity_ota_layout);
		
		initTitleBar("检测更新", "返回","",
				R.drawable.topbar_back_selector,R.drawable.topbar_btnbg,
				0, 0,
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					
					}
				},null);
	}


	@Override
	public void onHttpCallBackListener(PigHttpRequest action,PigHttpResponse response) {
		// TODO Auto-generated method stub
		
	}

	
}

package com.fgh.demos.download;

import java.io.File;

import pigframe.download.DownLoadOneFile;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpResponse;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;

import com.fgh.demos.R;

public class DownLoadDemoActivity extends PigHttpBaseActivity{

	

	
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
		findViewById(R.id.bt_downfail).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//制造一个下载失败的例子
				startdownload("http://www.52011111111apk.com/xxxx.apk");
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
		
		setContentView(R.layout.demo_activity_download_layout);
		
		initTitleBar("网络连接", "返回","下载",
				R.drawable.topbar_back_selector,R.drawable.topbar_btnbg,
				0, 0,
				new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					
					}
				},new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startdownload("http://dl.91rb.com/5afaf9f5fd75738ac8bcc815e2911296/android/soft/2010/12/2/f6636ded46854774868e1d22eb11a8ac/2920f95013b9461fb9b7683ef79e60aa.apk");
//						startdownload("http://www.52011111111apk.com/uploads/soft/120104/1-120104104544.apk");
					}
				});
	}


	@Override
	public void onHttpCallBackListener(PigHttpRequest action,PigHttpResponse response) {
		// TODO Auto-generated method stub
		
	}

	
	 private void startdownload(String url){
	     File dir=new File( Environment.getExternalStorageDirectory().getPath()+"/"+"PigFrame");
		 DownLoadOneFile dfl=new DownLoadOneFile(this,url, dir,null);
		 dfl.download();
	  
   }
}

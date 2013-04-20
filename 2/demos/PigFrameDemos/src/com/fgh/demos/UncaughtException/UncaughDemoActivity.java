package com.fgh.demos.UncaughtException;

import pigframe.httpFrame.ActionConstant;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpResponse;
import pigframe.httpFrame.network.AsyncImageLoader;
import pigframe.httpFrame.network.AsyncImageLoader.ImageCallback;
import pigframe.views.TouchView;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.fgh.demos.R;


/**
 * 从网络获取一张图片，并且进行缩放操作，以后会添加一个保存到sd卡的功能
 * @author ztw
 *
 */
public class UncaughDemoActivity extends PigHttpBaseActivity{
	private TouchView touchv;
	private ZoomControls zoomControls;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		touchv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//专门为了出错。。。空指针了
			}
		});
	}


	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
//		touchv=(TouchView) findViewById(R.id.tv_image);
//		zoomControls = (ZoomControls) findViewById(R.id.zc_tool);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.demo_activity_zoompic_layout);
	}

	@Override
	public void onHttpCallBackListener(PigHttpRequest action,PigHttpResponse response) {
		// TODO Auto-generated method stub
	}
	
}

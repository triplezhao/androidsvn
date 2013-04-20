package com.fgh.voice.act;

import pigframe.PigBaseActivity;
import pigframe.control.PigController;
import pigframe.httpFrame.network.AsyncImageLoader;
import pigframe.httpFrame.network.AsyncImageLoader.ImageCallback;
import pigframe.views.TouchView;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.fgh.voice.R;

public class PWZoomPicAtivity extends PW_BaseActivity{

	private TouchView touchv;
	private ZoomControls zoomControls;
	
	private String pic_url = null;
	public static String EXTRA_STR_PIC_URL = "EXTRA_STR_PIC_URL";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	
	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		touchv=(TouchView) findViewById(R.id.tv_image);
		zoomControls = (ZoomControls) findViewById(R.id.zc_tool);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		zoomControls.setOnZoomInClickListener(new OnClickListener() {
			@Override
				public void onClick(View v) {
				touchv.setBigger();
				}
			});
		zoomControls.setOnZoomOutClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				touchv.setSmailler();
			}
		});
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		if(pic_url!=null){
			new AsyncImageLoader().loadDrawable(true,pic_url,
					touchv, new ImageCallback() {
	
				@Override
				public void postImageLoad(Drawable imageDrawable,
						ImageView imageView, String imageUrl) {
					// TODO Auto-generated method stub
					cancelProgressDialog();
					imageView.setImageDrawable(imageDrawable);
				}
	
				@Override
				public void preImageLoad(ImageView imageView, String imageUrl) {
					// TODO Auto-generated method stub
					showProgressDialog("正在获取高清大图");
					touchv.setImageResource( R.drawable.icon);
				}
	
			});
		}
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_zoompic_layout);
		touchv=(TouchView) findViewById(R.id.tv_image);
		
		pic_url=getIntent().getStringExtra(EXTRA_STR_PIC_URL);
		
		
	}
	
	public static void open(Activity from,String picurl){
		Bundle data=new Bundle();
		data.putString(EXTRA_STR_PIC_URL, picurl);
		PigController.startAct(PWZoomPicAtivity.class, from, false, data);
		
	}
	
	
}

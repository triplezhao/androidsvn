package com.fgh.demos.zoompic;

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
public class DemoPicZoomActivity extends PigHttpBaseActivity{
	private TouchView touchv;
	private ZoomControls zoomControls;
//	private static final String demo_pic_url="http://91dota.com/wp-content/themes/huanjue2.22/images/5logo2.png";
	private static final String demo_pic_url="http://91dota.com/wp-content/themes/huanjue2.22/images/5logo2.png";
	
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
		
		new AsyncImageLoader().loadDrawable(true,demo_pic_url,
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
				showProgressDialog("载入中。。。");
				touchv.setImageResource( R.drawable.pig_demo_pic);
			}

		});
		initTitleBar("图片获取", "返回", "", R.drawable.topbar_back_selector, R.drawable.topbar_btnbg, 
				0, R.drawable.topbar_refresh,
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
						
						new AsyncImageLoader().loadDrawable(false,demo_pic_url,
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
								showProgressDialog("载入中。。。");
								touchv.setImageResource( R.drawable.pig_demo_pic);
							}

						});
					}
				});
		
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.demo_activity_zoompic_layout);
		touchv=(TouchView) findViewById(R.id.tv_image);
//		touchv.setImageResource( R.drawable.pig_demo_pic);
	}


	@Override
	public void onHttpCallBackListener(PigHttpRequest action,PigHttpResponse response) {
		// TODO Auto-generated method stub
		
	}

	
}

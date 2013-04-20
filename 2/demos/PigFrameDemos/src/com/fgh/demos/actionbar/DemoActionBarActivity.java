package com.fgh.demos.actionbar;

import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpResponse;
import pigframe.views.widet.PigComposeWidget;
import pigframe.views.widet.QuickAction;
import pigframe.views.widet.QuickActionBar;
import pigframe.views.widet.QuickActionWidget;
import pigframe.views.widet.QuickActionWidget.OnQuickActionClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.fgh.demos.R;


/**
 * 从网络获取一张图片，并且进行缩放操作，以后会添加一个保存到sd卡的功能
 * @author ztw
 *
 */
public class DemoActionBarActivity extends PigHttpBaseActivity{
	
	private Button tv_test_bar;
	private PigComposeWidget pcv_compose;
	 
	 private QuickActionWidget mBar;
	
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
		tv_test_bar=(Button) findViewById(R.id.tv_test_bar);
		pcv_compose=(PigComposeWidget) findViewById(R.id.pcv_compose);
		prepareQuickActionBar();
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		pcv_compose.setAction(0, R.drawable.home1, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showToast("0");
			}
		});
		
		tv_test_bar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mBar.show(tv_test_bar);
			}
		});
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
		initTitleBar("ActionBar", "返回", "", R.drawable.topbar_back_selector, 0, 
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
					}
				});
		
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		setContentView(R.layout.demo_activity_actionbar_layout);
	}


	@Override
	public void onHttpCallBackListener(PigHttpRequest action,PigHttpResponse response) {
		// TODO Auto-generated method stub
		
	}
	 private void prepareQuickActionBar() {
	        mBar = new QuickActionBar(getThisActivity());
	        mBar.addQuickAction(new QuickAction(getThisActivity(),
	        		R.drawable.topbar_back_selector, "返回"));
	        mBar.addQuickAction(new QuickAction(getThisActivity(),
	        		R.drawable.tab_home, "主页"));
	        mBar.setOnQuickActionClickListener(new OnQuickActionClickListener() {
				
				@Override
				public void onQuickActionClicked(QuickActionWidget widget, int position) {
					// TODO Auto-generated method stub
					switch (position) {
					case 0:
						showToast("返回");
						break;
					case 1:
						showToast("主页");
						break;
					default:
						break;
					}
					
				    
				}
			});
	    }
	
}

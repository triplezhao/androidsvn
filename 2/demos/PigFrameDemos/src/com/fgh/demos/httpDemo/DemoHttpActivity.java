package com.fgh.demos.httpDemo;

import pigframe.httpFrame.PigHttpCallBackListener;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpResponse;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fgh.demos.R;


/**
 * 
 * 演示利用pigframe获取网络数据的过程。
 * 1、定义一个action继承自HTTPAction。设置url、http参数、post/get、usecache等
 * 2、调用action的doaction方法。
 * 3、实现一个ActionListener接口。来接受、处理返回来的结果。
 * 
 * @author ztw
 *
 */
public  class DemoHttpActivity extends PigHttpBaseActivity{
	
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
		initTitleBar("搜索影片", "返回","",
				R.drawable.topbar_back_selector,R.drawable.topbar_btnbg,
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
						showToast("刷新数据，重新获取");
						showProgressDialog("获取数据。。。");
						DemoAction demoaction=new DemoAction(getThisActivity(),"465170158", false);
						demoaction.doRequest(new PigHttpCallBackListener() {
							
							@Override
							public void onHttpCallBackListener(PigHttpRequest actioned,
									PigHttpResponse response) {
								// TODO Auto-generated method stub
								int actionid=actioned.getActionId();
								
								if (actionid==DemoActionConstants.Action_Id_gettopic) {
									if(response.getRet_HttpStatusCode()==200){
//										if(parseDataErrorCode==OK){
										TextView tv=(TextView) findViewById(R.id.tv_test_http);
										Spanned text=Html.fromHtml(actioned.getActionUrl()+":\n"+response.getRet_String());
										tv.setText(text);
										tv.setMovementMethod(LinkMovementMethod.getInstance());
										}
									}
									cancelProgressDialog();
							}
						});
					}
				});
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		showToast("获取数据，优先使用本地缓存");
		showProgressDialog("获取数据。。。");
		DemoAction demoaction=new DemoAction(getThisActivity(),"465170158", true);
		demoaction.doRequest(new PigHttpCallBackListener() {
			
			@Override
			public void onHttpCallBackListener(PigHttpRequest actioned,
					PigHttpResponse response) {
				// TODO Auto-generated method stub
				int actionid=actioned.getActionId();
				
				if (actionid==DemoActionConstants.Action_Id_gettopic) {
					if(response.getRet_HttpStatusCode()==200){
//						if(parseDataErrorCode==OK){
						TextView tv=(TextView) findViewById(R.id.tv_test_http);
						Spanned text=Html.fromHtml(actioned.getActionUrl()+":\n"+response.getRet_String());
						tv.setText(text);
						tv.setMovementMethod(LinkMovementMethod.getInstance());
						}
					}
					cancelProgressDialog();
			}
		});
		
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.demo_activity_http_layout);
	}

	

	@Override
	public void onHttpCallBackListener(PigHttpRequest actioned,PigHttpResponse response) {
		// TODO Auto-generated method stub
//		int actionid=actioned.getActionId();
//		
//		if (actionid==DemoActionConstants.Action_Id_gettopic) {
//			if(response.getRet_HttpStatusCode()==200){
////				if(parseDataErrorCode==OK){
//				TextView tv=(TextView) findViewById(R.id.tv_test_http);
//				Spanned text=Html.fromHtml(actioned.getActionUrl()+":\n"+response.getRet_String());
//				tv.setText(text);
//				tv.setMovementMethod(LinkMovementMethod.getInstance());
//				}
//			}
//			cancelProgressDialog();
		}
	}
	

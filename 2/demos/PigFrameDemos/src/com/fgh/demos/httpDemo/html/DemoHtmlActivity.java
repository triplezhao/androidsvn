package com.fgh.demos.httpDemo.html;

import java.io.UnsupportedEncodingException;

import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpResponse;
import pigframe.httpFrame.network.AsyncImageLoader;
import pigframe.httpFrame.network.AsyncImageLoader.HtmlImageCallback;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fgh.demos.R;
import com.fgh.demos.httpDemo.DemoActionConstants;


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
public  class DemoHtmlActivity extends PigHttpBaseActivity{
	private TextView tv;
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
		initTitleBar("用TV现实HTML", "返回","",
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
						HtmlDemoAction demoaction=new HtmlDemoAction(getThisActivity(),
								DemoActionConstants.Action_Url_Html
								, false);
						demoaction.doRequest(DemoHtmlActivity.this);
					}
				});
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		showToast("获取数据，优先使用本地缓存");
		showProgressDialog("获取数据。。。");
		HtmlDemoAction demoaction=new HtmlDemoAction(getThisActivity(),
				DemoActionConstants.Action_Url_Html
				, true);
		demoaction.doRequest(DemoHtmlActivity.this);
		
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.demo_activity_http_layout);
		
		tv=(TextView) findViewById(R.id.tv_test_http);
	}

	@Override
	public void onHttpCallBackListener(PigHttpRequest actioned,PigHttpResponse response) {
		// TODO Auto-generated method stub
		int actionid=actioned.getActionId();
		if (actionid==DemoActionConstants.Action_Id_Html) {
//			showToast(action.getUrl()+action.getReturn_data().getString(PigHttpConnection.httpStrRetrun));
			
		   try {
			String	strResult=new String(response.getRet_String().getBytes("GBK"),"utf-8").trim();
//			strResult=strResult.replace("", "&nbsp");
//			strResult=strResult.replace("", "&raquo");
//			Spanned text=Html.fromHtml(strResult);
			setTextParten(strResult);
//			tv.setText(text);
			tv.setMovementMethod(LinkMovementMethod.getInstance());
		   } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			cancelProgressDialog();
		}

	}
	
//	实现一下ImageGetter就可以让图片显示了：
	 ImageGetter imgGetter = new Html.ImageGetter() {
		 Drawable drawable = null;
	              @Override
	              public Drawable getDrawable(String source) {
	                    
	            	  drawable= new AsyncImageLoader().loadHtmlDrawable(true, source
	                		   , tv, new HtmlImageCallback() {
								
								@Override
								public Drawable preImageLoad() {
									// TODO Auto-generated method stub
									return getResources().getDrawable(R.drawable.icon);
								}
								
								@Override
								public void postImageLoad(Drawable imageDrawable, TextView tv,
										String imageUrl) {
									// TODO Auto-generated method stub
									setTextParten(tv.getText().toString());
								}
							});
	                    
	                    
	                    // Important
	                   if(drawable!=null){
	                	   drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
	                                  .getIntrinsicHeight());
//	                	   drawable.setBounds(0, 0, 30, 30);
	                   } 
	                    return drawable;
	              }
	              
	 };
	 private void setTextParten(String content){
		 Spanned text = Html.fromHtml(content
				 ,imgGetter
				 ,null);
		 tv.setText(text);
	 }
}

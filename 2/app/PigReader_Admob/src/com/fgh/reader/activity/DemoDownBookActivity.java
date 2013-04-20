package com.fgh.reader.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import pigframe.httpFrame.PigHttpBaseActivity;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.httpFrame.PigHttpResponse;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.fgh.reader.R;



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
public  class DemoDownBookActivity extends PigHttpBaseActivity{
	
	public static final String iaskBaseUrl="http://ishare.iask.sina.com.cn";
	private EditText et_search;
	private Button  bt_search;
	private WebView  webv_search;
	
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
		et_search=(EditText) findViewById(R.id.et_search);
		bt_search=(Button) findViewById(R.id.bt_search);
		webv_search=(WebView) findViewById(R.id.webv_search);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		bt_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!et_search.getText().toString().equals("")){
					String url=makeSearchUrlByKeyword(et_search.getText().toString());
					webv_search.loadUrl(url);
					hide();
				}else{
					showToast("请输入关键词");
				}
			}
		});
		
		 webv_search.setOnTouchListener(new OnTouchListener()
	        {
	            @Override
	            public boolean onTouch(View v, MotionEvent event)
	            {
	            	webv_search.requestFocus();
	                return false;
	            }
	        });
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		initWebView();
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		setContentView(R.layout.demo_activity_downbook_layout);
		setTitleBarBg(R.drawable.bar1);
		initTitleBar("网上搜书", "","",
				0,0,
				0, 0,
				null,null);
	}


	@Override
	public void onHttpCallBackListener(PigHttpRequest actioned,PigHttpResponse response) {
		// TODO Auto-generated method stub
	}
	
	private void initWebView()  
    {  
        webv_search.clearCache(true);  
        webv_search.getSettings().setJavaScriptEnabled(true);  
        webv_search.getSettings().setSupportZoom(true);  
        webv_search.getSettings().setBuiltInZoomControls(true);  
         
    } 
	
	private String makeSearchUrlByKeyword(String keyword){
		
		String url="";
		try {
			url=iaskBaseUrl+"/search.php?key="+
			URLEncoder.encode(keyword, "GB2312")+
			"&format=txt"+"&sort=price";
//			url=iaskBaseUrl+"/search.php?key="+keyword+"&format=txt";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}
   private void hide(){
	    InputMethodManager im =(InputMethodManager) et_search 
		.getContext() .getSystemService(Context.INPUT_METHOD_SERVICE);

		im.hideSoftInputFromWindow(et_search.getWindowToken(), 
		                    InputMethodManager.HIDE_NOT_ALWAYS);
   }
	
}
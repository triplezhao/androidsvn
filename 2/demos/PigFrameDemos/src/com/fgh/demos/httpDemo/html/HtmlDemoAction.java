package com.fgh.demos.httpDemo.html;

import com.fgh.demos.httpDemo.DemoActionConstants;

import pigframe.httpFrame.PigHttpRequest;
import android.content.Context;
import android.text.TextUtils;

public class HtmlDemoAction extends PigHttpRequest {

	
	
	public HtmlDemoAction(Context mContext, String url, boolean usecache) {
		
		StringBuffer strBuffer = new StringBuffer();
		
//		if(!TextUtils.isEmpty(uid)){
//			strBuffer.append("&uid=");
//			strBuffer.append(uid);
//		}
//		if(!TextUtils.isEmpty(nickname)){
//			strBuffer.append("&screen_name=");
//			strBuffer.append(nickname);
//		}
		
		
		setmContext(mContext);
		setUseCache(usecache);
		setActionId(DemoActionConstants.Action_Id_Html);
		setActionUrl(url);
		setActionParam(strBuffer.toString());
		setPost(false);
	}
}

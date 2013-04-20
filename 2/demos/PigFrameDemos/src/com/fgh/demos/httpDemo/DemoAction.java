package com.fgh.demos.httpDemo;

import pigframe.httpFrame.ActionConstant;
import pigframe.httpFrame.PigHttpRequest;
import android.content.Context;
import android.text.TextUtils;

public class DemoAction extends PigHttpRequest {

	
	
	public DemoAction(Context mContext, String uid, boolean usecache) {
		
		StringBuffer strBuffer = new StringBuffer();
		
		if(!TextUtils.isEmpty(uid)){
			strBuffer.append("&uid=");
			strBuffer.append(uid);
		}
//		if(!TextUtils.isEmpty(nickname)){
//			strBuffer.append("&screen_name=");
//			strBuffer.append(nickname);
//		}
		setmContext(mContext);
		setUseCache(usecache);
		setActionId(DemoActionConstants.Action_Id_gettopic);
		setActionUrl(DemoActionConstants.Action_Url_gettopic);
		setPost(false);
		//因为是get所以url其实就是加上了参数
//		setActionUrl(DemoActionConstants.Action_Url_gettopic+strBuffer.toString());
		setActionParam(strBuffer.toString());
		
	}
}

package com.fgh.voice.api;

import pigframe.httpFrame.ActionConstant;
import pigframe.httpFrame.PigHttpRequest;
import android.content.Context;
import android.text.TextUtils;

public class SendInfoAction extends PigHttpRequest {

	
	
	public SendInfoAction(Context mContext, String username,String password,boolean usecache) {
		
		StringBuffer strBuffer = new StringBuffer();
		
		if(!TextUtils.isEmpty(username)){
			strBuffer.append("username=");
			strBuffer.append(username);
		}
		if(!TextUtils.isEmpty(password)){
			strBuffer.append("&password=");
			strBuffer.append(password);
		}
		setmContext(mContext);
		setUseCache(usecache);
		setActionId(ActionConstants.Action_Id_sendinfo);
		setActionUrl(ActionConstants.Action_Url_sendinfo);
		setActionUrl_Dot("");
		setPost(false);
		//因为是get所以url其实就是加上了参数
//		setActionUrl(DemoActionConstants.Action_Url_gettopic+strBuffer.toString());
		setActionParam(strBuffer.toString());
		
	}
}

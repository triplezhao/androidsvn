package com.fgh.voice.network;

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
		
		//action id
		setActionId(DemoActionConstants.Action_Id_gettopic);
		setActionUrl(DemoActionConstants.Action_Url_gettopic);
		setmContext(mContext);
		setUseCache(usecache);
		setActionParam(strBuffer.toString());
		setPost(false);
	}
}

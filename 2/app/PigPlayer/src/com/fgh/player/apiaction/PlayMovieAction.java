package com.fgh.player.apiaction;

import pigframe.httpFrame.ActionConstant;
import pigframe.httpFrame.PigHttpRequest;
import android.content.Context;
import android.text.TextUtils;

public class PlayMovieAction extends PigHttpRequest {

	
	
	public PlayMovieAction(Context mContext, String m_id, boolean usecache) {
		
		StringBuffer strBuffer = new StringBuffer();
		
//		if(!TextUtils.isEmpty(nickname)){
//			strBuffer.append("&screen_name=");
//			strBuffer.append(nickname);
		setmContext(mContext);
		setUseCache(usecache);
		setActionId(PlayerActionConstants.Action_Id_playmovie);
		setActionUrl(PlayerActionConstants.Action_Url_playmovie+"?point=1&id="
				+m_id
				+"&pid=38819fd26bc8c209&format=4&audiolang=1&guid=250fd60d88b9260ef8f08fcdbef3400b&ver=2.2&operator=android_310260&network=mobile");
		setActionParam(strBuffer.toString());
		setPost(false);
	}
}

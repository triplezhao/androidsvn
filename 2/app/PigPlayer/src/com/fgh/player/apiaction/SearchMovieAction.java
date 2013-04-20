package com.fgh.player.apiaction;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import pigframe.httpFrame.ActionConstant;
import pigframe.httpFrame.PigHttpRequest;
import pigframe.util.PigStringUtils;
import android.content.Context;
import android.text.TextUtils;

public class SearchMovieAction extends PigHttpRequest {

	
	
	public SearchMovieAction(Context mContext, String m_name, boolean usecache) {
		
		
		
		StringBuffer strBuffer = new StringBuffer();
		String urlparm="?format=4&pid=38819fd26bc8c209&guid=250fd60d88b9260ef8f08fcdbef3400b&ver=2.2&operator=Android_310260&network=mobile&fields=vid%7crepu|dura|titl|img&pg=1";
		String encode_url="";
		try {
			if(!TextUtils.isEmpty(m_name)){
					m_name=URLEncoder.encode(m_name, "utf-8");
			}
			encode_url=PlayerActionConstants.Action_Url_serchmovie
					+m_name
					+urlparm;
			encode_url=PigStringUtils.replace7C(encode_url);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setmContext(mContext);
		setUseCache(usecache);
		setActionId(PlayerActionConstants.Action_Id_serchmovie);
		setActionUrl(encode_url);
		setActionParam(strBuffer.toString());
		setPost(false);
	}
}

package pigframe.httpFrame;



import android.content.Context;
import android.text.TextUtils;

public   class DemoPigHttpRequest extends PigHttpRequest{

	public DemoPigHttpRequest(Context mContext, String sina_uid,String nickname, boolean usecache) {
		
		StringBuffer strBuffer = new StringBuffer();
		
		if(!TextUtils.isEmpty(sina_uid)){
			strBuffer.append("&sina_uid=");
			strBuffer.append(sina_uid);
		}
		if(!TextUtils.isEmpty(nickname)){
			strBuffer.append("&screen_name=");
			strBuffer.append(nickname);
		}
		setmContext(mContext);
		setUseCache(usecache);
		setActionId(ActionConstant.id_login);
		setActionUrl(ActionConstant.url_login);
		setActionParam(strBuffer.toString());
	}
	
	

}

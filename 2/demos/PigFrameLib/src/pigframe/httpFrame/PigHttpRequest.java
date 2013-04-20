package pigframe.httpFrame;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public   class PigHttpRequest  {
	
	
	/**
	 * 下面是请求数据
	 */
	private Context mContext;
	//是否使用缓存
	private boolean useCache;
	//是否是post
	private boolean isPost=false;
	//请求参数
	private String actionParam;
	//请求地址
	private String actionUrl;
	//  默认为 ?   url?a=xxx&b=xxxx 就是这里的问号，有的地方不用？来做分割，用/
	private String actionUrl_Dot="?";
	//请求具体的id（如请求个人信息）
	private int actionId;
	//其他请求的数据描述
	private Bundle other_Req_Bundle;

	public PigHttpRequest(){
		
	}
	



	public String getActionUrl_Dot() {
		return actionUrl_Dot;
	}




	public void setActionUrl_Dot(String actionUrlDot) {
		actionUrl_Dot = actionUrlDot;
	}




	public Context getmContext() {
		return mContext;
	}
	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}
	
	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}

	public String getActionParam() {
		return actionParam;
	}

	public void setActionParam(String actionParam) {
		this.actionParam = actionParam;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public int getActionId() {
		return actionId;
	}




	public void setActionId(int actionId) {
		this.actionId = actionId;
	}




	public Bundle getOther_Req_Bundle() {
		return other_Req_Bundle;
	}




	public void setOther_Req_Bundle(Bundle otherReqBundle) {
		other_Req_Bundle = otherReqBundle;
	}




	/**
	 * @param pigHttpCallBackListener
	 * 内部实现了后台线程访问，所以ui线程可以调用，不卡
	 * 貌似放在线程中调用，会不好使。。。
	 */
	public  void doRequest(final PigHttpCallBackListener pigHttpCallBackListener){ final Handler handler = new Handler() {
	            public void handleMessage(Message message) {
	            	/// 可能会造成obj == null 
	            	if (message.obj == null) {
	            		return;
	            	}
	            	PigHttpResponse response=(PigHttpResponse) message.obj;
	            	pigHttpCallBackListener.onHttpCallBackListener(PigHttpRequest.this,response);
	            }
	        };
	        //建立新一个新的线程下载图片
	        new Thread() {
	            @Override
	            public void run() {
	            	PigHttpResponse response=PigHttpConnection.doRequest(PigHttpRequest.this);
	                Message message = handler.obtainMessage(0, response);
	                handler.sendMessage(message);
	            }
	        }.start();
	}
}

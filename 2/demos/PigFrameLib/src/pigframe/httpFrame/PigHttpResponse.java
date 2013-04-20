package pigframe.httpFrame;



public   class PigHttpResponse  {
	
	
//	//访问网络返回成功结果值
	public static final int HttpStatusSuccessCode=200;
	public static final int NOERRORCode=0;
	
	
	/**
	 * 下面是返回后的数据
	 */
	//存储返回的数据
	protected String ret_String;
	//http 返回状态 200为成功
	protected int ret_HttpStatusCode;
	//data 返回状态 ,0为成功 
	protected int ret_ErrorCode;
	
	
	
	public PigHttpResponse(){
		
	}	

	public String getRet_String() {
		return ret_String;
	}

	public void setRet_String(String retString) {
		ret_String = retString;
	}

	public int getRet_HttpStatusCode() {
		return ret_HttpStatusCode;
	}

	public void setRet_HttpStatusCode(int retHttpStatusCode) {
		ret_HttpStatusCode = retHttpStatusCode;
	}

//	public int getRet_ErrorCode() {
//		return ret_ErrorCode;
//	}
//
//	public void setRet_ErrorCode(int retErrorCode) {
//		ret_ErrorCode = retErrorCode;
//	}

	
	
	
	
}

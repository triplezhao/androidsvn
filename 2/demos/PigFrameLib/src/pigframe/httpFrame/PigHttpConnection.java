package pigframe.httpFrame;


import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import pigframe.PigApplication;
import pigframe.cache.CacheFactory;
import pigframe.cache.ICacheManager;
import pigframe.util.PigStringUtils;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

public  class PigHttpConnection  {
	
//	//访问网络返回的错误值。0为地址不存在
//	public static final String httpErrorCode="httpStatusCode";
//	public static final String httpStrRetrun="httpStrRetrun";
	
	public PigHttpConnection(Context ctx){
		
	}
	
	static String tag = "HttpConnector";
//	static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers");
	static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

	static String proxyAddress = "";//"10.0.0.200";
	static String proxyProt = "";//"80";
	
	public static final String CMPROXY = "10.0.0.172";
	public static final String CTPROXY = "10.0.0.200";
	public static final String PROT = "80";
	private static TelephonyManager mtelephonyManager;
	private static TelephonyManager mtelephonyManager2;

	/**
	 * 三星要求:
	 * 这段代码是完成在只有GSM卡，并且没有WLAN的情况下，应用需要提示用户没有数据连接。
	 */ 
	public static boolean testGSMWLAN(Context ctx){
		mtelephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		///////////////////
        mtelephonyManager2 = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
//        mtelephonyManager2 = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE_2);
        int nSimState = mtelephonyManager.getSimState();
        int nSimStateGsm = mtelephonyManager2.getSimState();
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        int wifiState =wifiManager.getWifiState();
         
        if(wifiState == WifiManager.WIFI_STATE_DISABLED){
            if(nSimState != TelephonyManager.SIM_STATE_READY && nSimStateGsm == TelephonyManager.SIM_STATE_READY){
            	return false;
            }
        }
        return true;
	}
	
	public static boolean testWifi(Context ctx){
		ConnectivityManager mConnectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		if(info==null || !mConnectivity.getBackgroundDataSetting()){
			Log.d(tag, "!mConnectivity.getBackgroundDataSetting()"+mConnectivity.getBackgroundDataSetting());
			return false;
		}
		int netType = info.getType();
		int netSubType = info.getSubtype();
		Log.d(tag, "netType:"+netType+" netSubType:"+netSubType);
		if(ConnectivityManager.TYPE_WIFI==netType){
			Log.d(tag, "TYPE_WIFI");
			return info.isConnectedOrConnecting();
		}
		return false;
	}
	
	
	public static boolean testNetworkState(Context ctx){
		ConnectivityManager mConnectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		if(info==null || !mConnectivity.getBackgroundDataSetting()){
			Log.d(tag, "!mConnectivity.getBackgroundDataSetting()"+mConnectivity.getBackgroundDataSetting());
			return false;
		}
		int netType = info.getType();
		int netSubType = info.getSubtype();
		Log.d(tag, "netType:"+netType+" netSubType:"+netSubType);
		if(ConnectivityManager.TYPE_WIFI==netType){
			Log.d(tag, "TYPE_WIFI");
			return info.isConnectedOrConnecting();
		}
		if(ConnectivityManager.TYPE_MOBILE==netType && TelephonyManager.NETWORK_TYPE_CDMA==netSubType){
			Log.d(tag, "NETWORK_TYPE_CDMA");
			return info.isConnectedOrConnecting();
		}
		if(ConnectivityManager.TYPE_MOBILE==netType && TelephonyManager.NETWORK_TYPE_GPRS==netSubType){
			Log.d(tag, "NETWORK_TYPE_GPRS");
			return info.isConnectedOrConnecting();
		}
		if(ConnectivityManager.TYPE_MOBILE==netType && TelephonyManager.NETWORK_TYPE_EVDO_0==netSubType){
			Log.d(tag, "NETWORK_TYPE_EVDO_0");
			return info.isConnectedOrConnecting();
		}
		if(ConnectivityManager.TYPE_MOBILE==netType && TelephonyManager.NETWORK_TYPE_EVDO_A==netSubType){
			Log.d(tag, "NETWORK_TYPE_EVDO_A");
			return info.isConnectedOrConnecting();
		}
		return false;
	}
	private static DefaultHttpClient buileClient(Context ctx){
		
		HttpHost proxy = null;
		APNType apnType = getCurrentUsedAPNType(ctx);
		Log.d(tag, APNType.CTWAP+" "+apnType.name().toString()+" "+APNType.CTWAP.equals(apnType)); 
//		String paramProxy = null;
//		String paramProt = PROT;
//		if(!StringUtils.isEmpty(proxyAddress) && !StringUtils.isEmpty(proxyProt)){
//			paramProxy = proxyAddress;
//			paramProt = proxyProt;
//		}
		if(APNType.CTWAP.equals(apnType)){//电信
//			if(paramProxy == null){
//				paramProxy = CTPROXY;
//			}
			proxy = new HttpHost(CTPROXY, Integer.parseInt(PROT));
		}else if (APNType.CMWAP.equals(apnType)){//移动
//			if(paramProxy == null){
//				paramProxy = CMPROXY;
//			}
			proxy = new HttpHost(CMPROXY, Integer.parseInt(PROT));
//			proxy = new HttpHost(CTPROXY.replace("http://", ""), 80);
		}
//		else if (APNType._3GWAP.equals(apnType)){//联通
//			if(paramProxy == null){
//				paramProxy = CMPROXY;
//			}
//			proxy = new HttpHost(CMPROXY, Integer.parseInt(PROT));
//			proxy = new HttpHost(CTPROXY.replace("http://", ""), 80);
//		}
		HttpParams httpParameters = new BasicHttpParams();

		int timeoutConnection = 1000*20;

		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 1000*50;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		//没有wifi就设置代理
		if(!testWifi(ctx)){
			//并且用wap方式上网
			if (proxy != null)
			{
				httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			//net 方式不用设置代理
		}
		return httpclient;
	}
	
	/**
	 * 接入点类型
	 */
	public enum APNType {
		CMWAP, CMNET, Unknow, CTWAP, CTNET,_3GNET,_3GWAP;
	}

	//获取接入点类型
	public static APNType getCurrentUsedAPNType(Context ctx) {
		Log.d(tag, "getCurrentUsedAPNType");
		try {
			ContentResolver cr = ctx.getContentResolver();
			Cursor cursor = cr.query(PREFERRED_APN_URI, new String[]{"_id","name","apn","proxy","port"}, null, null, null);
			
			cursor.moveToFirst();
			if (cursor.isAfterLast()) {
				return APNType.Unknow;
			}
			String id = cursor.getString(0);
			String name = cursor.getString(1);
			String apn = cursor.getString(2);
			String proxy = cursor.getString(3);
			String _prot = cursor.getString(4);
			if(!PigStringUtils.isEmpty(proxy)){
				proxyAddress = proxy;
			}
			if(!PigStringUtils.isEmpty(_prot)){
				proxyProt = _prot;
			}
			Log.d(tag, id+" proxy:"+proxy+" prot:"+_prot+" apn:"+apn);
			cursor.close();
			if (//"1".equals(id) || //三星电信1为ctwap,2为ctnet
					(
						("CTWAP".equals(apn.toUpperCase())||"CTWAP".equals(name.toUpperCase()))&&
						!PigStringUtils.isEmpty(proxy)&&
						!PigStringUtils.isEmpty(_prot)
					)
				)
				return APNType.CTWAP;
			else if (//"2".equals(id) ||
						(
							("CTNET".equals(apn.toUpperCase()) || "CTNET".equals(name.toUpperCase()))&&
							PigStringUtils.isEmpty(proxy)&&
							PigStringUtils.isEmpty(_prot)
						)
					)
				return APNType.CTNET;
			else if (("CMWAP".equals(apn.toUpperCase()) || "CMWAP".equals(name.toUpperCase())) &&
						!PigStringUtils.isEmpty(proxy)&&
						!PigStringUtils.isEmpty(_prot))
				return APNType.CMWAP;
			else if (("CMNET".equals(apn.toUpperCase()) || "CMNET".equals(name.toUpperCase()))&&
						PigStringUtils.isEmpty(proxy)&&
						PigStringUtils.isEmpty(_prot))
				return APNType.CMNET;
			else if (("3GWAP".equals(apn.toUpperCase()) || "3GWAP".equals(name.toUpperCase())) &&
					!PigStringUtils.isEmpty(proxy)&&
					!PigStringUtils.isEmpty(_prot))
				return APNType._3GWAP;
			else if (("3GNET".equals(apn.toUpperCase()) || "3GNET".equals(name.toUpperCase()))&&
					PigStringUtils.isEmpty(proxy)&&
					PigStringUtils.isEmpty(_prot))
				return APNType._3GNET;
			else
				return APNType.Unknow;
		} catch (Exception ep) {
			Log.e(tag, "getCurrentUsedAPNTypeException");
			return APNType.Unknow;
		}
	}
	
	
	public static boolean testNetWork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
		.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
		// 当前网络不可用
//			UGCApplication.getInstance().makeToast("网络异常");
			return false;
		}
		return true;
	}
	
	
	public static ICacheManager<String> manager = CacheFactory.creator(CacheFactory.CACHE_TYPE_COMMON);
	
	/**
	 * @param mContext
	 * @param url
	 * @param ActionParam
	 * @param usecache
	 * @param ispost
	 * @param url_dot    url?a=xxx&b=xxxx 就是这里的问号，有的地方不用？来做分割，用/
	 * @return
	 */
	private  static PigHttpResponse doRequest(Context mContext,
							 String url,
							 String ActionParam,
							 boolean usecache ,boolean ispost,String url_dot){
					
		
				
		
		PigHttpResponse response=new PigHttpResponse();
				if(url==null){
					response.setRet_HttpStatusCode(404);
					return response;
				}
			
				if(usecache){
					String retCache = manager.getCache(url + "&" + ActionParam);
					if(!TextUtils.isEmpty(retCache)){
		//				PrintLog.printLog("getCache: " + retCache);
//						retBundle.putString(httpStrRetrun, retCache);
//						//200代表读取的缓存
//						retBundle.putInt(httpErrorCode, 200);
						response.setRet_String(retCache);
						//200代表读取的缓存
						response.setRet_HttpStatusCode(PigHttpResponse.HttpStatusSuccessCode);
						
						return response;
					}
				}
				
				
				
			    DefaultHttpClient httpclient = null;
			    String	strResult;
				httpclient = buileClient(mContext);
				
				
				
				try {
					HttpResponse rsp;
					//post方式
					if(ispost){
						HttpPost httpPost = new HttpPost(url);
						httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
						httpPost.addHeader("User-Agent", "Jakarta Commons-HttpClient/3.1");
						httpPost.setEntity(new StringEntity(ActionParam,
								HTTP.UTF_8));
						rsp = httpclient.execute(httpPost);
					}else{//get方式
						if(ActionParam!=null&&!ActionParam.equals("")){
							url=url+url_dot+ActionParam;
						}
						HttpGet req = new HttpGet(url);
						rsp = httpclient.execute(req);
					}
					
					int StatusCode=rsp.getStatusLine().getStatusCode();
					if (StatusCode == 200) {
						byte[] b = EntityUtils.toByteArray(rsp.getEntity());
		//				strResult = EntityUtils.toString(rsp.getEntity(), "GBK");
						strResult=new String(b, "gbk").trim();
					    //处理keyword中这个字段的&字符   <rpurl>http://f.10086.cn/sms/index.php?m=Topic&a=topic_list&topic_id=29</rpurl>
						strResult=strResult.replace("&", "&amp;");
						//设置返回的结果str
//						retBundle.putString(httpStrRetrun, strResult);
						response.setRet_String(strResult);
						if(!TextUtils.isEmpty(strResult)){
							manager.putCache(url + "&" + ActionParam, strResult);
						}

					}
					response.setRet_HttpStatusCode(StatusCode);
					
					
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return response;
		
	}
	
	
	public static PigHttpResponse doRequest(PigHttpRequest request){
		return doRequest(request.getmContext(), request.getActionUrl(),
				request.getActionParam(), request.isUseCache(), 
				request.isPost(),request.getActionUrl_Dot());
	}
	
	
	
	
	public  static Bundle doRequest_Image(Context mContext,
			String imageurl,
			boolean usecache){
		
			return null;
	}
	
public static InputStream doGet(String uriAPI) {
		
		if(uriAPI==null){
			return null;
		}
		InputStream is = null;
		DefaultHttpClient httpclient = null;
		try {
			if(testNetWork(PigApplication.getInstance())){
				httpclient = buileClient(PigApplication.getInstance());
				HttpGet req = new HttpGet(uriAPI);
				HttpResponse rsp = httpclient.execute(req);
				if (rsp.getStatusLine().getStatusCode() == 200) {
//					byte[] b = EntityUtils.toByteArray(rsp.getEntity());
					is= rsp.getEntity().getContent();
//					//处理keyword中这个字段的&字符   <rpurl>http://f.10086.cn/sms/index.php?m=Topic&a=topic_list&topic_id=29</rpurl>
				}
			}
			return is;
			
		}
		catch (Exception e) {
			Log.e(tag, e.toString());
			return is;
		} finally {
			if(httpclient!=null){
				httpclient.getConnectionManager().shutdown();
				Log.d(tag, "httpclient.shutdown()");
			}
		}
	}
}

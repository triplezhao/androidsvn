package com.fgh.up.logic;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;

import android.text.TextUtils;


public class HttpTool {
	
	public static String ProxyAddress="";
	public static int ProxyPort=80;
	
	
	public static boolean sendPost(String url,String postData){
		
		boolean isSuccess=false;
		HttpURLConnection con =null;
		URL dataUrl;
		try {
				postData=URLEncoder.encode(postData,"utf-8");
				postData="content="+postData;
				dataUrl = new URL(url);
				if(ProxyAddress.equals("")||ProxyAddress==null){
					 con = (HttpURLConnection) dataUrl.openConnection();
				}else{
//					广东代理  测试好使 125.93.180.234	8081
					Proxy proxy=new Proxy(java.net.Proxy.Type.HTTP,
					new InetSocketAddress(ProxyAddress,
							ProxyPort));
					//甘肃代理   试验过能用
//					Proxy proxy=new Proxy(java.net.Proxy.Type.HTTP,
//							new InetSocketAddress("112.85.42.69",
//									80));
					System.out.println(proxy.toString());
					con = (HttpURLConnection) dataUrl.openConnection(proxy);
				}
			    con.setRequestMethod("POST");
			    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			    con.setRequestProperty("Connection", "Keep-Alive");
			    con.setUseCaches( false);
			    con.setDoOutput(true);
		//	    con.addRequestProperty("content", postData);
			    OutputStream os=con.getOutputStream();
			    DataOutputStream dos=new DataOutputStream(os);
			    dos.write(postData.getBytes());
			    
			    
			    if (con.getResponseCode() == HttpURLConnection.HTTP_OK)
			    {
			    	System.out.println( "connect success!");
			    	isSuccess=true;
			    } 
			    
			    dos.flush();
			    dos.close();
		    
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
	        {
	            
	            if (con != null)
	                con.disconnect() ;
	        }
		return isSuccess;
	}
	
//	public static HttpURLConnection getURL(String url,Context context){
//		HttpURLConnection con =null;
//		URL postUrl=null;
//		try {
//			postUrl = new URL(url);
//		} catch (MalformedURLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		boolean isProxy=false;
//		//网络检测
//		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if(cm!=null){
//			NetworkInfo  ni = cm.getActiveNetworkInfo();
//			if(ni!=null){
//				if(! ni.getTypeName().equals("WIFI")){
//					isProxy=true;
//				}
//			}
//		}
//		try {
//			if(isProxy){
////				Proxy proxy=new Proxy(java.net.Proxy.Type.HTTP,
////						new InetSocketAddress(android.net.Proxy.getDefaultHost(),
////								android.net.Proxy.getDefaultPort()));
//				Proxy proxy=new Proxy(java.net.Proxy.Type.HTTP,
//						new InetSocketAddress("10.0.0.172",
//								80));
//				con = (HttpURLConnection) postUrl.openConnection(proxy);
//				
////				 Properties systemProperties = System.getProperties();
////				    systemProperties.setProperty("http.proxyHost","10.0.0.172");
////				    systemProperties.setProperty("http.proxyPort","80");
////				    con = (HttpURLConnection) postUrl.openConnection();
//			}else{
//				
//				con = (HttpURLConnection) postUrl.openConnection();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return con;
//	}
	
}

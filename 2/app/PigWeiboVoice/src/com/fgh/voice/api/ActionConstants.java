package com.fgh.voice.api;

import pigframe.httpFrame.PigHttpRequest;

public class ActionConstants extends PigHttpRequest {
	
	
	
	public static final String URL = "http://appcloudy.sinaapp.com/api/";
	public static   int BASEID = 2000;
	
	
	//测试demo的id和url
	public static final int Action_Id_sendinfo = ++BASEID;
	public static final String Action_Url_sendinfo = URL+"cnvoice_tb/insert/";
	
	
	
}

package com.fgh.demos.httpDemo;

import pigframe.httpFrame.PigHttpRequest;

public class DemoActionConstants extends PigHttpRequest {
	
	
	
	public static final String URL = "http://www.91dota.com/http_demo.txt";
	public static final String HTMLURL = "http://www.91dota.com/?p=220";
	public static   int BASEID = 2000;
	
	
	//测试demo的id和url
	public static final int Action_Id_gettopic = ++BASEID;
	public static final int Action_Id_Html = ++BASEID;
	public static final String Action_Url_gettopic = URL;
	public static final String Action_Url_Html = HTMLURL;
	
	
	
}

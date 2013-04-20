package com.fgh.player.apiaction;

import pigframe.httpFrame.PigHttpRequest;

public class PlayerActionConstants extends PigHttpRequest {
	
	
	
	public static final String URL = "http://api.3g.youku.com";
	public static   int BASEID = 2000;
	//http://api.3g.youku.com/layout/phone2_1/play?point=1&id=XNDAyNTY5MzY0&pid=38819fd26bc8c209&format=4&audiolang=1&guid=250fd60d88b9260ef8f08fcdbef3400b&ver=2.2&operator=android_310260&network=mobile
	
	//测试demo的id和url
	public static final int Action_Id_serchmovie = ++BASEID;
	public static final String Action_Url_serchmovie = URL+"/openapi-wireless/videos/search/";
	public static final int Action_Id_playmovie = ++BASEID;
	public static final String Action_Url_playmovie = URL+"/layout/phone2_1/play";
	
	
	
}

package com.fgh.voice;

import pigframe.PigApplication;
import pigframe.util.PigUncaughtExceptionHandler;
import pigframe.util.PigUncaughtExceptionHandlerCallback;
import weibo4android.util.OAuthConstant;
import android.widget.Toast;

import com.fgh.voice.db.UserDao;
import com.fgh.voice.db.bean.UserBean;

public class PWApplication extends PigApplication{
	
	/**
	 * 登录页面中的User是本地缓存的用户信息
	 */
	private static UserBean user = null;
	
	private static UserBean def_user = null;
	public static String weibo_subject_name = "";
	
	public static UserBean getUser() {
		return user;
	}
	public static void setUser(UserBean user) {
		PWApplication.user = user;
	}
	
	
	public static UserBean getDefUser() {
		return def_user;
	}
	public static void setDefUser(UserBean defuser) {
		PWApplication.def_user = defuser;
	}
	
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		System.setProperty("weibo4j.oauth.consumerKey",OAuthConstant.CONSUMER_KEY );
    	System.setProperty("weibo4j.oauth.consumerSecret", OAuthConstant.CONSUMER_SECRET);
		
		
		init();
		
	}
	public void init(){
		
		initSubject();
		initDefUser();
		initExceptionCatch();
		
		
	}
	
	public void initSubject(){
		
		weibo_subject_name=getResources().getString(R.string.STR_WEIBO_SUBJECT_NAME);
	}
	public void initDefUser(){
		UserDao dbHelper = new UserDao(this);
		UserBean userBean=dbHelper.selectIsPublicUser(false);
		if(userBean!=null){
			PWApplication.setUser(userBean);
		}
		dbHelper.close();
		
		long id=Long.valueOf(getResources().getString(R.string.STR_DEF_USER_ID));
		String name=getResources().getString(R.string.STR_DEF_USER_NAME);
		String token=getResources().getString(R.string.STR_DEF_USER_Token);
		String tokensceret=getResources().getString(R.string.STR_DEF_USER_TokenSecret);
		
		UserBean defuserBean=new UserBean();
		defuserBean.setUserId(id);
		defuserBean.setUser(name);
		defuserBean.setToken(token);
		defuserBean.setTokenSecret(tokensceret);
		defuserBean.setPublic(true);
		
		PWApplication.setDefUser(defuserBean);
		
	}
	public void initExceptionCatch(){
		
		setPigUncaughtExceptionHandler(new PigUncaughtExceptionHandlerCallback() {
					
					@Override
					public boolean handleException(Throwable ex) {
						// TODO Auto-generated method stub
		
						//可以使用pig默认的处理方法如下：
		//					PigUncaughtExceptionHandler.getInstance().handleException(ex);
					
						//也可以自己定义
							Toast.makeText(PWApplication.this, "貌似出了问题:" , Toast.LENGTH_LONG).show();
							PigUncaughtExceptionHandler pigex=PigUncaughtExceptionHandler.getInstance();
							//保存错误报告文件到SD卡上，以便分析异常
							pigex.saveCrashInfoToFile(ex);
						return true;
					}
				});
	}
}

package pigframe;



import pigframe.cache.CacheFactory;
import pigframe.cache.DataFile;
import pigframe.cache.ICacheManager;
import pigframe.download.DownLoadOneFile.NotifyBroadcastReceiver;
import pigframe.util.PigAndroidUtil;
import pigframe.util.PigUncaughtExceptionHandler;
import pigframe.util.PigUncaughtExceptionHandlerCallback;
import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.Toast;



public abstract class PigApplication extends Application {
	private static final String tag = "PigApplication";
	private static PigApplication mApp;
	private Handler mHandler = null;
	private static Activity currentActivity = null;
	public static boolean first = true;
	public static ICacheManager<String> COMMON_CacheManager;
	public static ICacheManager<String> DrawableCacheManager ;
	protected PigUncaughtExceptionHandlerCallback uncaughtExceptionHandlerCallback;
	protected PigUncaughtExceptionHandler crashHandler ;
	public  boolean isCatchException=false;
	
	private NotifyBroadcastReceiver notifyBroadcastReceiver=new NotifyBroadcastReceiver();
	
	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
		mHandler = new Handler();
		initPig();
		
	}
	
	public void initPig(){
		
		initException();
		initPigCache();
		registerBoradcastReceiver();
	}
	
	
	private void initException(){
		if(!isCatchException){
			return ;
		}
		//默认Callback走pig的异常处理方法。使用者可以复写这个方法
		setPigUncaughtExceptionHandler(new PigUncaughtExceptionHandlerCallback() {
			
			@Override
			public boolean handleException(Throwable ex) {
				// TODO Auto-generated method stub
				//这个可以自己来实现了。处理异常，不走系统的error弹出框
				PigUncaughtExceptionHandler.getInstance().handleException(ex);
				return true;
			}
		});
	}
	
	public void setPigUncaughtExceptionHandler(PigUncaughtExceptionHandlerCallback uncaughtExceptionHandlerCallback){
		crashHandler= PigUncaughtExceptionHandler.getInstance();  
        //注册crashHandler  
        crashHandler.init(getApplicationContext(),uncaughtExceptionHandlerCallback);  
        //发送以前没发送的报告(可选)  
        crashHandler.sendPreviousReportsToServer();  
	}
	
	
	public void initPigCache(){
		DataFile.initDataPath(this);
		COMMON_CacheManager = CacheFactory.creator(CacheFactory.CACHE_TYPE_COMMON);
		DrawableCacheManager = CacheFactory.creator(CacheFactory.CACHE_TYPE_IMAGE);
		
	}
	
	public static PigApplication getInstance() {
		return mApp;
	}
	
	private String versionName = null;
	
	public Handler getHandler() {
		return mHandler;
	}

	public String getVersion() {
		if (versionName == null) {
			versionName = "1.0.1";
			try {
				versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			} catch (Exception e) {
			}
		}
		return versionName;
	}
	
	/**
	 * 
	 * @param str
	 */
	public void makeToast(final String str) {
		mHandler.post(new Runnable() {
			public void run() {
				PigAndroidUtil.showToast(PigApplication.this, str, Toast.LENGTH_LONG);
			}

		});
	}
	
	public  Activity getCurrentActivity(){
		return currentActivity;
	}
	public  void setCurrentActivity(Activity act){
		currentActivity=act;
	}
	
	
	
	
	@Override
	public void onTerminate() {

		super.onTerminate();
//		unregisterReceiver(notifyBroadcastReceiver);
	}
	
	
	
	
     
    public void registerBoradcastReceiver(){ 
        IntentFilter myIntentFilter = new IntentFilter(); 
        myIntentFilter.addAction(NotifyBroadcastReceiver.ACTION_NOTIFY_PAUSE); 
        //注册广播       
        registerReceiver(notifyBroadcastReceiver, myIntentFilter); 
    } 		
	
}

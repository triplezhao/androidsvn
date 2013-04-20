package com.fgh.pets;

import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;
import pigframe.PigApplication;
import pigframe.util.PigUncaughtExceptionHandler;
import pigframe.util.PigUncaughtExceptionHandlerCallback;

public class PigPetApplication extends PigApplication{
	
	
	public static int screenWidth=320; //default value;
	public static int screenHeight=480;//default value;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
		
	}
	public void init(){
		setPigUncaughtExceptionHandler(new PigUncaughtExceptionHandlerCallback() {
			
			@Override
			public boolean handleException(Throwable ex) {
				// TODO Auto-generated method stub

				//可以使用pig默认的处理方法如下：
//					PigUncaughtExceptionHandler.getInstance().handleException(ex);
			
				//也可以自己定乄1�7
					Toast.makeText(PigPetApplication.this, "貌似出了问题:" , Toast.LENGTH_LONG).show();
					PigUncaughtExceptionHandler pigex=PigUncaughtExceptionHandler.getInstance();
					//保存错误报告文件到SD卡上，以便分析异帄1�7
					pigex.saveCrashInfoToFile(ex);
				return true;
			}
		});
		
		initWH();
	}
	
	private void initWH(){
		 DisplayMetrics dm = new DisplayMetrics(); 
		 dm =getResources().getDisplayMetrics(); 
		 screenWidth = dm.widthPixels; 
		 screenHeight = dm.heightPixels; 
	}
	
}

package com.fgh.smshelp;

import pigframe.PigApplication;
import pigframe.util.PigUncaughtExceptionHandler;
import pigframe.util.PigUncaughtExceptionHandlerCallback;
import android.widget.Toast;

public class SHApplication extends PigApplication{
	
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
					Toast.makeText(SHApplication.this, "貌似出了问题:" , Toast.LENGTH_LONG).show();
					PigUncaughtExceptionHandler pigex=PigUncaughtExceptionHandler.getInstance();
					//保存错误报告文件到SD卡上，以便分析异帄1�7
					pigex.saveCrashInfoToFile(ex);
				return true;
			}
		});
		
		
	}
	
}

package com.iceeye.crop;


import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.iceeye.crop.util.BitMapTools;

public class MyApplication extends Application {
	
	private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
	public static int screenWidth=320;
	public static int screenHeight=480;

	public WindowManager.LayoutParams getMywmParams(){
		return wmParams;
	}


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i("MyApplication", "============> MyApplication");
		
		DisplayMetrics dm = new DisplayMetrics(); 
		dm =getResources().getDisplayMetrics(); 
		 screenWidth = dm.widthPixels; 
		 screenHeight = dm.heightPixels; 
		 
		 BitMapTools.cropScreenToRaw();
	}

}

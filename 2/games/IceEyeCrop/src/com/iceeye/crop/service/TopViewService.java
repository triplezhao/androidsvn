package com.iceeye.crop.service;


import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.iceeye.crop.MyApplication;
import com.iceeye.crop.R;
import com.iceeye.crop.ShareActivity;
import com.iceeye.crop.util.BitMapTools;


public class TopViewService extends Service  implements OnTouchListener{
	public static final String tmp_path="/sdcard/iceeyes/tmp/";
	public static final String save_path="/sdcard/iceeyes/save/";
	public static final String tmp_rawname="temp.raw";
	public static final String tmp_pngname="tmppng.png";
	public static final String tmp_crop_png_name="tmpcroppng.png";
	
	 GestureDetector mGesture = null;  
	WindowManager wm=null;
	WindowManager.LayoutParams wmParams=null;
//	EditText myFV=null;
	View myFV;
	private static final String TAG = "TestService";
	@Override
	public boolean onUnbind(Intent i) {
		Log.i(TAG, "============> TestService.onUnbind");
		return false;   
	}

	@Override
	public void onRebind(Intent i) {
		Log.i(TAG, "============> TestService.onRebind");
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "============> TestService.onCreate");
		createView();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "============> TestService.onStart");
		if(myFV!=null){
			myFV.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "============> TestService.onDestroy");
		wm.removeView(myFV);
	}
	
	 private void createView(){
		 		LayoutInflater inflater = (LayoutInflater)    
		 		this.getSystemService(LAYOUT_INFLATER_SERVICE);   
		 		myFV  = inflater.inflate(R.layout.layout_main,null);
	//	    	myFV.setImageResource(R.drawable.icon);
		    	wm=(WindowManager)getApplicationContext().getSystemService("window");
		    	wmParams = ((MyApplication)getApplication()).getMywmParams();
		        wmParams.type=2002;
		        wmParams.format=1;
		        wmParams.flags|=8;
	        
	        
		        wmParams.gravity=Gravity.LEFT|Gravity.TOP;  
		        wmParams.x=320;
		        wmParams.y=50;
		       
		        wmParams.width=45;
		        wmParams.height=45;
	       
	            wm.addView(myFV, wmParams);
	            
	            Log.i("TEST", "onCreate");  
	            myFV.setOnTouchListener(this);  
	            mGesture = new GestureDetector(this, new GestureListener());  
	            
	       
	    }
	 
	 
	 @Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}
	 

	    /**
	     * @param path      传递的图片所在路径   /sdcard/iceeyes/
	     * @param tmppngname  传递图片名字   xxx.png
	     */
	    public void callShareActivity(String path,String tmppngname){
	    	Intent intent = new Intent(this, ShareActivity.class);
	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		    intent.putExtra("image-path", path+tmppngname);
		    startActivity(intent);
	        
	    }

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			   return mGesture.onTouchEvent(event);
		}
		
		class GestureListener extends SimpleOnGestureListener  
	    {  
	  
	        @Override  
	        public boolean onDoubleTap(MotionEvent e)  
	        {  
	            // TODO Auto-generated method stub  
	            Log.i("TEST", "onDoubleTap");  
//	            Toast.makeText(TopViewService.this, "双击冰眼可进行设置", Toast.LENGTH_SHORT).show();
	            return super.onDoubleTap(e);  
	        }  
	  
	        @Override  
	        public boolean onDown(MotionEvent e)  
	        {  
	            // TODO Auto-generated method stub  
	            Log.i("TEST", "onDown");  
	            return super.onDown(e);  
	        }  
	  
	        @Override  
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
	                float velocityY)  
	        {  
	            // TODO Auto-generated method stub  
	            Log.i("TEST", "onFling:velocityX = " + velocityX + " velocityY" + velocityY);  
	            return super.onFling(e1, e2, velocityX, velocityY);  
	        }  
	  
	        @Override  
	        public void onLongPress(MotionEvent e)  
	        {  
	            // TODO Auto-generated method stub  
	            Log.i("TEST", "onLongPress");
	            if(myFV!=null){
	    			myFV.setVisibility(View.GONE);
	    		}
    			Toast.makeText(TopViewService.this, "关闭成功", Toast.LENGTH_SHORT).show();
	            super.onLongPress(e);  
	        }  
	  
	        @Override  
	        public boolean onScroll(MotionEvent e1, MotionEvent e2,  
	                float distanceX, float distanceY)  
	        {  
	            // TODO Auto-generated method stub  
	            Log.i("TEST", "onScroll:distanceX = " + distanceX + " distanceY = " + distanceY);  
	            //更新浮动窗口位置参数  
                 wmParams.x=(int)(e2.getRawX()-myFV.getWidth()/2);  
                 wmParams.y=(int)(e2.getRawY()-myFV.getHeight()/2); 
                 wm.updateViewLayout(myFV, wmParams); 
	            
	            return true;
	        }  
	  
	        @Override  
	        public boolean onSingleTapUp(MotionEvent e)  
	        {  
	            // TODO Auto-generated method stub  
	            Log.i("TEST", "onSingleTapUp");  
	            
//	            Toast.makeText(TopViewService.this, "长按冰眼可关闭程序", Toast.LENGTH_SHORT).show();
	            
	            //截屏保存
				int cropok=BitMapTools.cropScreenToRaw();
				//从raw文件转成bitmap
				if(cropok>0){
					Bitmap bitmap=BitMapTools.getBitMapFromRawFile(
							tmp_rawname,MyApplication.screenWidth,MyApplication.screenHeight);
					if(bitmap!=null){
					//存储bitmap到sd
					  int saveok=BitMapTools.saveBitmapAsPNG(TopViewService.tmp_path,tmp_pngname,bitmap);
					  if(saveok>0)
						  //打开指定存储位置的图片
						 callShareActivity(TopViewService.tmp_path,tmp_pngname);
					 	return true;
					}
				}
				Toast.makeText(TopViewService.this, "截屏失败", Toast.LENGTH_SHORT).show();
	            return true;  
	        }  
	          
	    }  
}

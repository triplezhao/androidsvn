package com.fgh.pets.service;


import pigframe.util.PigAndroidUtil;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fgh.pets.R;
import com.fgh.pets.anim.GifMovie;
import com.fgh.pets.anim.GifMovieListener;
import com.fgh.pets.anim.MovieConstant;



public class TopViewService extends Service  implements OnTouchListener{
	public static final String tmp_path="/sdcard/iceeyes/tmp/";
	public static final String save_path="/sdcard/iceeyes/save/";
	public static final String tmp_rawname="temp.raw";
	public static final String tmp_pngname="tmppng.png";
	public static final String tmp_crop_png_name="tmpcroppng.png";
	
	private GestureDetector mGesture = null;  
	private WindowManager wm=null;
	private WindowManager.LayoutParams wmParams=null;
//	EditText myFV=null;
	private View myFV;
	private  ImageView iv_anim;
	private  GifMovie gifMovie;
	
	
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
		 		iv_anim=(ImageView) myFV.findViewById(R.id.iv_anim);
		 		gifMovie=new GifMovie(this,iv_anim );
		 		 
		 		 
		    	wm=(WindowManager)getApplicationContext().getSystemService("window");
		    	wmParams = pigframe.util.PigAndroidUtil.getMywmParams();
		        wmParams.type=2002;
		        wmParams.format=1;
		        wmParams.flags|=8;
	        
	        
		        wmParams.gravity=Gravity.LEFT|Gravity.TOP;  
		        wmParams.x=320;
		        wmParams.y=50;
		       
		        wmParams.width=145;
		        wmParams.height=145;
	       
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
	 

//	    /**
//	     * @param path      ���ݵ�ͼƬ����·��   /sdcard/iceeyes/
//	     * @param tmppngname  ����ͼƬ����   xxx.png
//	     */
//	    public void callShareActivity(String path,String tmppngname){
//	    	Intent intent = new Intent(this, ShareActivity.class);
//	    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//		    intent.putExtra("image-path", path+tmppngname);
//		    startActivity(intent);
//	        
//	    }

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
//	            Toast.makeText(TopViewService.this, "˫�����ۿɽ�������", Toast.LENGTH_SHORT).show();
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
	            PigAndroidUtil.showToast(TopViewService.this, "长按隐藏", 3000);
	            super.onLongPress(e);  
	        }  
	  
	        @Override  
	        public boolean onScroll(MotionEvent e1, MotionEvent e2,  
	                float distanceX, float distanceY)  
	        {  
	            // TODO Auto-generated method stub  
	            Log.i("TEST", "onScroll:distanceX = " + distanceX + " distanceY = " + distanceY);  
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
	            PigAndroidUtil.showToast(TopViewService.this, "点击信息：这是一个popwindow,受控于一个service", 3000);
	            gifMovie.playMovie(MovieConstant.AnimType.xiaoji_right,10,new GifMovieListener() {
					
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						 Log.i("TEST", "onSingleTapUp");  
					}
				});
	            return true;  
	        }  
	    }  
}

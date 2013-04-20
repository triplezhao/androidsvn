package com.fgh.pets.anim;


import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.fgh.pets.R;
import com.fgh.pets.anim.MovieConstant.AnimType;

public class GifMovie {
	private static int mThreadid=0;
	public static boolean isPlaying=false;
	
	
	private String mGifName;
	private int mPicCount;
	private Handler mhandler;
	private Context mctx;
	private View mView;
	private AnimType m_animType;
	
	public GifMovie(Context ctx,View view){
		mctx=ctx;
		mView=view;
		mhandler=new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message arg0) {
				// TODO Auto-generated method stub
				int resId=arg0.arg1;
				mView.setBackgroundResource(resId);
				
				return false;
			}
		});
	}
	
	
	public void stopMovie(){
		isPlaying=false;
	};
	
	
	public void playMovie(AnimType animType,final int times,final GifMovieListener mlistener){
		
		
		isPlaying=true;
		mPicCount=MovieConstant.getCountByAnimType(animType);
		mGifName=animType.toString();
		m_animType=animType;
		
		new Thread(new Runnable() {
			int timesCount=0;
			int threadid=0;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mThreadid+=1;
				threadid=mThreadid;
				Log.i("playMovie", threadid+"");
			
				while (timesCount<times) {
					timesCount++;
					for (int i = 0; i <mPicCount; i++) {
						String tempname=mGifName;
						if(i<10){
							tempname=tempname+"0";
						}
						int resId=mctx.getResources().getIdentifier(
								tempname+"00"+i, "drawable", mctx.getPackageName());
						
						Log.i("resname:", tempname+"00"+i);
						Log.i("resId", resId+"");
						
						Message msg=new Message();
						msg.what=1;
						msg.arg1=resId;
						mhandler.sendMessage(msg);
						
						
						try {
							Thread.sleep(MovieConstant.gif_step_time);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//要求停止此次动画。或者开始了其他动画
						if(!isPlaying||threadid<mThreadid){
							
							break;
						}
					}//end for
				}
				
				
				
				//自然结束了次动画，或者手动要求停止的。则还原背景
				if(!isPlaying||threadid>=mThreadid){
					Message msg_over=new Message();
					msg_over.arg1=R.drawable.xiaoji_right0000;
					mhandler.sendMessage(msg_over);
					isPlaying=false;
					mlistener.onFinish();
				}
			}
		}).start();
		
		
	}
	
	
}

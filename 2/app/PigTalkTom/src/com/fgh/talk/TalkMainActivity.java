package com.fgh.talk;

import pigframe.PigBaseActivity;
import pigframe.util.PigAndroidUtil;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.fgh.talk.animation.GifMovie;
import com.fgh.talk.animation.MovieConstant;
import com.fgh.talk.sound.Cocos2dxSound;
import com.fgh.talk.sound.MicListener;
import com.fgh.talk.sound.SoundConstant;

public class TalkMainActivity extends PigBaseActivity{

	private  ImageView iv_anim;
//	private   PCMAudioTrack audioPlayer;
	private Cocos2dxSound wavPlayer;
	private MicListener mic_listener;
	
	private GifMovie gifMovie;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC); //让音量键固定为媒体音量控制
//		audioPlayer=new PCMAudioTrack();
//		audioPlayer.init(this);
		wavPlayer=new Cocos2dxSound(this);
//		mic_listener=new MicListenerThread();
		wavPlayer.preLoadAllAsset("16000");
		
		
	}

	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		iv_anim=(ImageView) findViewById(R.id.iv_anim);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		iv_anim.setOnTouchListener(new OnTouchListener() {
			
		public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
		  switch (event.getAction() ) {
			  case MotionEvent.ACTION_DOWN:
				  
				  
			     MovieConstant.AnimType mtype = MovieConstant.AnimType.panda_jump;
    	    	 String pathwav=SoundConstant.p_foot3;
    	    	 wavPlayer.stopAllEffects();
				  	
				  	int stop_x = (int) event.getX();
	    	    	int stop_y = (int) event.getY();
	    	    	
	    	    	//left 
	    	    	if(stop_x<PigAndroidUtil.getW()/2){
	    	    		//bottom   
	    	    		if(stop_y>PigAndroidUtil.getH()/2){
	    	    			  mtype=MovieConstant.AnimType.panda_cry;
	    	    			  pathwav=SoundConstant.p_foot3;
	    	    		//top   
	    	    		}else if(stop_y<=PigAndroidUtil.getH()/2){
	    	    			  mtype=MovieConstant.AnimType.panda_hello;
	    	    			  pathwav=SoundConstant.p_drink_milk;
	    	    			  wavPlayer.playEffect(SoundConstant.pour_milk, false);
	    	    			  
	    				  }
	    	    	 //right
	    	    	}else if(stop_x>PigAndroidUtil.getW()/2){
	    	    		//bottom   
	    	    		if(stop_y>PigAndroidUtil.getH()/2){
//	    					  v.setBackgroundResource(R.anim.anim_cat_foot_right); 
	    	    			mtype=MovieConstant.AnimType.panda_zhini;
	    	    			pathwav=SoundConstant.p_foot4;
	    	    			//top 
	    	    		}else if(stop_y<=PigAndroidUtil.getH()/2){
	    	    			  mtype=MovieConstant.AnimType.panda_xiagui;
	    	    			  pathwav=SoundConstant.fall;
	    				  }
	    	    	}
	    	    
	    	    	
					 wavPlayer.playEffect(pathwav, false);
					
					 gifMovie.playMovie(mtype);
						
					 break;
	        }
	        return true;	
			}
		});
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		iv_anim.setBackgroundResource(R.drawable.panda_xiagui0001);
		gifMovie=new GifMovie(TalkMainActivity.this,iv_anim );
	}
	
	
	
	
	 /**
	  * 播放这个view自己的动画
	 * @param iv
	 */
	public void startAnim(View iv){  
	        // 获取AnimationDrawable对象  
	        AnimationDrawable animationDrawable = (AnimationDrawable)iv.getBackground();  
	        animationDrawable.stop();
	        animationDrawable.start();  
	    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mic_listener=new MicListener();
		mic_listener.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mic_listener.pause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mic_listener.pause();
	}  
	
	 
}

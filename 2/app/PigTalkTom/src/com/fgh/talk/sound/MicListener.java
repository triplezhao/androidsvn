package com.fgh.talk.sound;
 
import com.fgh.talk.animation.GifMovie;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.MediaRecorder.AudioSource;
import android.util.Log;
 
/**
 * 监听MIC音量类。
 * 如果大于多少就开始调用录音类开始
 * 如果小于就停止录音类，并播放
 * @author ztw
 *
 */
public class MicListener extends Thread {
   
    
    
    private boolean isRun = false;
    private RecordPlayer recordPlayer;
    
 
    public MicListener() {
        super();
        recordPlayer=new RecordPlayer();
    }
 
    public void run() {
        super.run();
        recordPlayer.startLiternMic();
        isRun = true;
        int mark=0;
		boolean pre_mark=false;
        while (isRun) {  
              int dB = recordPlayer.getVolume();
            
              if(!pre_mark){
	        	   mark=0;
	           }
	            if(dB>SoundConstant.MinDecibel&&!GifMovie.isPlaying){
	            	pre_mark=true;
	            	mark++;
	 	            Log.i("start mark",  mark+"");
	 	            if(mark>=SoundConstant.START_COUNT){
	 	            	recordPlayer.rePlay();
	 	            }
	            }else{
	            	pre_mark=false;
	            }
            
                Log.d("dB", dB+"");
            	
          }//end while
        recordPlayer.release();
    }
       
 
    public void pause() {
                // 在调用本线程的 Activity 的 onPause 里调用，以便 Activity 暂停时释放麦克风
        isRun = false;
    }
 
    public void start() {
                // 在调用本线程的 Activity 的 onResume 里调用，以便 Activity 恢复后继续获取麦克风输入音量
        if (!isRun) {
            super.start();
        }
    }
    
}

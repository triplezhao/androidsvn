package com.fgh.voice.util;

import android.os.Handler;
import android.os.Message;

 abstract public class PigAsyncTask {
	
	public abstract void doPre();
	public abstract void doInBack();
	public abstract void doPost();
	
	public void excute(){
    	//1111111111111111111
		 doPre();
		//3333333333333333333
		 final Handler handler = new Handler() {
            public void handleMessage(Message message) {
            	doPost();
            }
         };
         
       //2222222222222222222
        new Thread() {
            @Override
            public void run() {
            	
            	doInBack();
            	
                Message message =new Message();
                handler.sendMessage(message);
            }
        }.start();
}
	
}

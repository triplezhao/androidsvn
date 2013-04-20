package com.fgh.pets;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.fgh.pets.service.TopViewService;

public class PigPetsActivity extends Activity {
    /** Called when the activity is first created. */
//        setContentView(R.layout.main);
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    				WindowManager.LayoutParams.FLAG_FULLSCREEN);
            try{
            	Intent i = new Intent(this, TopViewService.class);
            	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startService(i); 
            }
            catch (Exception e) {
            }
            finally{
              finish();
            }
        }
}
package com.iceeye.crop;

import net.youmi.android.AdManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.iceeye.crop.service.TopViewService;

public class RootActivity extends Activity{
	
//  ע�� ���ڳ�����ڵ�ʹ��static������ʼ��AdManager.init 
	static {
		//				Ӧ��Id				Ӧ������			      ���������(s)   ����ģʽ      
		AdManager.init("f5fb7e56cc05bc81", "e43ae97d19d0b473", 30, 	false);
	}
	
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

package com.fgh.room.activity;

import pigframe.PigBaseActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fgh.room.R;
import com.fgh.room.util.IntentCaller;

public class PigRoomActivity extends PigBaseActivity {
	
	
	private Button bt_dail;
	private Button bt_sms;
	private Button bt_photo;
	private Button bt_vedio;
	
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
		
		
	}

	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		bt_dail=(Button) findViewById(R.id.bt_dail);
		bt_sms=(Button) findViewById(R.id.bt_sms);
		bt_photo=(Button) findViewById(R.id.bt_photo);
		bt_vedio=(Button) findViewById(R.id.bt_vedio);
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
		bt_dail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentCaller.DialIntent(getThisActivity());
			}
		});
		bt_sms.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentCaller.SmsIntent(getThisActivity());
			}
		});
		bt_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				IntentCaller.CameraIntent(getThisActivity());
			}
		});
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
	}
}
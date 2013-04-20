package com.fgh.voice.act;

import com.fgh.voice.R;

import pigframe.PigBaseActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



public class Pw_About_Activity extends PW_BaseActivity {

	private static final String TAG = "Pw_About_Activity";
	TextView tv_about;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_about_layout);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	@Override
	public void initFindViews() {
		tv_about = (TextView)findViewById(R.id.tv_about);
	}

	@Override
	public void initListeners() {
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		String about=getResources().getString(R.string.PIG_FRAME_ABOUT_INFO);
		tv_about.setText( about);
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub

	}

}

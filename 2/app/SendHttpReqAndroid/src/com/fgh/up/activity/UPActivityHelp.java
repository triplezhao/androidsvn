package com.fgh.up.activity;

import com.fgh.up.R;

import android.os.Bundle;
import pigframe.PigBaseActivity;

public class UPActivityHelp extends PigBaseActivity{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_help);
		initPre();
		initFindViews();
		initListeners();
		initPost();
	}
	
	
	@Override
	public void initFindViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPre() {
		// TODO Auto-generated method stub
		initTitleBar("说明", "","",
				0,0,
				0, 0,
				null,null);
	}

}

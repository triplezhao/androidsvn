package com.fgh.voice.act;

import pigframe.PigBaseActivity;

import com.umeng.analytics.MobclickAgent;

public abstract class  PW_BaseActivity extends PigBaseActivity{
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		doAddActivity();
		super.onResume();
		 MobclickAgent.onResume(this);
	}
	public void onPause() {
	    super.onPause();
	    MobclickAgent.onPause(this);
	}
}

package com.fgh.smshelp.activity;

import pigframe.PigMainTabAct;
import android.os.Bundle;

import com.fgh.smshelp.R;

public class SHMainActivity extends PigMainTabAct {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		AddTab(R.drawable.icon, "主页", SHOfenActivity.class);
		AddTab(R.drawable.icon, "收件箱", SHOfenActivity.class);
		AddTab(R.drawable.icon, "帮助", SHOfenActivity.class);
		setTabWidgetBg(R.drawable.topbar_bg);
    }

}
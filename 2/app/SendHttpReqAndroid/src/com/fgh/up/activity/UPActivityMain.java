package com.fgh.up.activity;



import pigframe.PigMainTabAct;
import android.os.Bundle;

import com.fgh.up.R;




/**
 * 修改tab样式的例子
 * @author ztw
 *
 */
public class UPActivityMain extends PigMainTabAct{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AddTab(R.drawable.icon, "主页", UPActivitySend.class);
		AddTab(R.drawable.icon, "帮助", UPActivityHelp.class);
		setTabWidgetBg(R.drawable.topbar_bg);
	}
	
}

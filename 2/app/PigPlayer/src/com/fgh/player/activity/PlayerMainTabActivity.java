package com.fgh.player.activity;

import pigframe.PigMainTabAct;
import android.os.Bundle;

import com.fgh.pigframe.R;


/**
 * 修改tab样式的例子
 * @author ztw
 *
 */
public class PlayerMainTabActivity extends PigMainTabAct{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AddTab(R.drawable.icon, "主页", PigPlayerActivity.class);
		AddTab(R.drawable.icon,"本地", MP4FileSelectActivity.class);
		AddTab(R.drawable.icon,"搜索", SearchMovieHttpActivity.class);
		setTabWidgetBg(R.drawable.topbar_bg);
	}
	
}
